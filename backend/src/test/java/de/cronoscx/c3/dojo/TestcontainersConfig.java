
package de.cronoscx.c3.dojo;

import eu.rekawek.toxiproxy.Proxy;
import eu.rekawek.toxiproxy.ToxiproxyClient;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.Network;
import org.testcontainers.postgresql.PostgreSQLContainer;
import org.testcontainers.toxiproxy.ToxiproxyContainer;
import org.testcontainers.utility.DockerImageName;

import javax.sql.DataSource;
import java.io.IOException;

@TestConfiguration(proxyBeanMethods = false)
public class TestcontainersConfig {
    private static final int PORT_POSTGRESQL = 5432;
    private static final int PORT_TOXIPROXY = 8666;

    private final Network network = Network.newNetwork();

    @Bean
    PostgreSQLContainer postgresContainer() {
        //noinspection resource
        return new PostgreSQLContainer(DockerImageName.parse("postgres:18"))
                .withDatabaseName("mydb")
                .withNetwork(network)
                .withNetworkAliases("db");
    }

    @Bean
    ToxiproxyContainer toxiproxyContainer() {
        //noinspection resource
        return new ToxiproxyContainer("ghcr.io/shopify/toxiproxy:2.5.0")
                .withNetwork(network);
    }

    @Bean
    ToxiproxyClient toxiproxyClient(ToxiproxyContainer toxiproxyContainer) {
        return new ToxiproxyClient(toxiproxyContainer.getHost(), toxiproxyContainer.getControlPort());
    }

    @Bean
    Proxy dbProxy(ToxiproxyClient toxiproxyClient) throws IOException {
        final var result = toxiproxyClient.createProxy("db-proxy", "0.0.0.0:%d".formatted(PORT_TOXIPROXY), "db:%d".formatted(PORT_POSTGRESQL));

//        result.toxics().bandwidth("DOWN_BANDWIDTH", ToxicDirection.DOWNSTREAM, 1 << 5);
//        result.toxics().latency("DOWN_LATENCY", ToxicDirection.DOWNSTREAM, 20); //.setJitter(100);
//
//        result.toxics().bandwidth("UP_BANDWIDTH", ToxicDirection.UPSTREAM, 1 << 5);
//        result.toxics().latency("UP_LATENCY", ToxicDirection.UPSTREAM, 20); //.setJitter(100);

        return result;
    }

    @Bean
    DataSource dataSource(
            ToxiproxyContainer toxiproxyContainer,
            PostgreSQLContainer postgresContainer,
            Proxy dbProxy /* dependency necessary to make sure the proxy is up and running */
    ) {
        final var jdbcUrl = "jdbc:postgresql://%s:%d/%s".formatted(
                toxiproxyContainer.getHost(),
                toxiproxyContainer.getMappedPort(PORT_TOXIPROXY),
                postgresContainer.getDatabaseName()
        );
        return DataSourceBuilder.create()
                .url(jdbcUrl)
                .username(postgresContainer.getUsername())
                .password(postgresContainer.getPassword())
                .build();
    }

}

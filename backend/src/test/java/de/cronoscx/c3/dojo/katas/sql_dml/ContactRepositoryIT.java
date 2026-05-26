package de.cronoscx.c3.dojo.katas.sql_dml;

import de.cronoscx.c3.dojo.TestcontainersConfig;
import eu.rekawek.toxiproxy.Proxy;
import eu.rekawek.toxiproxy.model.Toxic;
import eu.rekawek.toxiproxy.model.ToxicDirection;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase.Replace;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Katas :: Persistence :: Contacts")
@DisplayNameGeneration(ReplaceUnderscores.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ContextConfiguration(classes = {TestcontainersConfig.class})
@TestPropertySource(properties = {
        "spring.liquibase.contexts=dev",
        "spring.jpa.properties.hibernate.generate_statistics=true",
        "logging.level.org.hibernate.session.metrics=DEBUG",
})
class ContactRepositoryIT {

    @Autowired
    private ContactRepository underTest;
    @Autowired
    private Proxy dbProxy;

    @Test
    void preconditions() {
        // given

        // when
        final var actual = underTest.findAll(Pageable.ofSize(1));

        // then
        assertThat(actual.getTotalElements())
                .as("Less contacts in test database than expected")
                .isGreaterThan(5);
    }

    @Nested
    class Search {
        private static final int LATENCY_BASE_MILLIS = 20;
        private static final int LATENCY_JITTER_MILLIS = 100;
        private static final int BANDWIDTH_BYTES = 1 << 10;

        private static final int TIMEOUT_MILLIS = 750;

        @BeforeEach
        void beforeEach() throws IOException {
            dbProxy.toxics().bandwidth("DOWN_BANDWIDTH", ToxicDirection.DOWNSTREAM, BANDWIDTH_BYTES);
            dbProxy.toxics().latency("DOWN_LATENCY", ToxicDirection.DOWNSTREAM, LATENCY_BASE_MILLIS).setJitter(LATENCY_JITTER_MILLIS);

            dbProxy.toxics().bandwidth("UP_BANDWIDTH", ToxicDirection.UPSTREAM, BANDWIDTH_BYTES);
            dbProxy.toxics().latency("UP_LATENCY", ToxicDirection.UPSTREAM, LATENCY_BASE_MILLIS).setJitter(LATENCY_JITTER_MILLIS);
        }

        @AfterEach
        void afterEach() throws IOException {
            for (Toxic toxic : dbProxy.toxics().getAll()) {
                toxic.remove();
            }
        }

        @Test
        @Timeout(value = TIMEOUT_MILLIS, unit = TimeUnit.MILLISECONDS)
        void without_restrictions() {
            // given
            final var query = new ContactQuery(null, null, null);
            final var spec = underTest.buildSpecification(query);
            final var expected = underTest.findAll(Pageable.unpaged());

            // when
            final var actual = underTest.findAll(spec, Pageable.unpaged());

            // then
            assertThat(actual)
                    .containsExactlyInAnyOrderElementsOf(expected);
        }

        @ParameterizedTest
        @ValueSource(strings = {
                "test",
                "test@example",
                "test@example.org",
                "doesnotexist",
        })
        @Timeout(value = TIMEOUT_MILLIS, unit = TimeUnit.MILLISECONDS)
        void by_email(String email) {
            // given
            final var query = new ContactQuery(email, null, null);
            final var spec = underTest.buildSpecification(query);
            final var expected = underTest.findAll(Pageable.unpaged())
                    .filter(contact -> contact.getEmail().toLowerCase().startsWith(email.toLowerCase()));

            // when
            final var actual = underTest.findAll(spec, Pageable.unpaged());

            // then
            assertThat(actual)
                    .containsExactlyInAnyOrderElementsOf(expected);
        }

        @ParameterizedTest
        @ValueSource(strings = {
                "PT1S",
                "PT10S",
                "PT30S",
                "PT1M",
                "PT1H",
        })
        @Timeout(value = TIMEOUT_MILLIS, unit = TimeUnit.MILLISECONDS)
        void by_createdAfter(Duration durationFromNow) {
            // given
            final var query = new ContactQuery(null, Instant.now().minus(durationFromNow), null);
            final var spec = underTest.buildSpecification(query);
            final var expected = underTest.findAll(Pageable.unpaged())
                    .filter(contact -> Instant.now().minus(durationFromNow).isBefore(contact.getCreatedAt()));

            // when
            final var actual = underTest.findAll(spec, Pageable.unpaged());

            // then
            assertThat(actual)
                    .containsExactlyInAnyOrderElementsOf(expected);
        }

    }

}

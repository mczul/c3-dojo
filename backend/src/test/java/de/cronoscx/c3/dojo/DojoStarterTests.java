package de.cronoscx.c3.dojo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.modulith.core.VerificationOptions;
import org.springframework.modulith.docs.Documenter;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@DisplayName("App :: Context & Modularity")
@SpringBootTest
@Import(TestcontainersConfig.class)
class DojoStarterTests {

    @Test
    void loading_application_context() {
    }

    @Test
    void modularity_violations() {
        // given
        final var violations = ApplicationModules.of(DojoStarter.class)

                // when
                .detectViolations(VerificationOptions.defaults()
                        .withAdditionalVerifications(/* TODO */));

        // then
        assertThat(violations.hasViolations())
                .describedAs(violations.getMessage())
                .isFalse();
    }

    @Test
    void write_modularity_documentation() {
        final var modules = ApplicationModules.of(DojoStarter.class);
        new Documenter(modules)
                .writeModulesAsPlantUml()
                .writeIndividualModulesAsPlantUml();
    }

}

package de.cronoscx.c3.dojo.katas.spring_feature_flags;

import de.cronoscx.c3.dojo.TestcontainersConfig;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.system.JavaVersion;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Katas :: Spring :: Feature Flags")
@DisplayNameGeneration(ReplaceUnderscores.class)
@SpringBootTest
@Import(TestcontainersConfig.class)
class SpringFeatureFlagsUseCasesIT {

    @Autowired
    private SpringFeatureFlagsUseCases underTest;

    @Nested
    class Sanitizing {

        @Test
        void depending_on_operation_system() {
            // given
            final var targetSanitizerClass = MacOsSanitizer.class;
            final var actualOS = System.getProperty(SpringFeatureFlagsConfig.PROPERTY_OS_NAME_KEY);

            // when
            final var activeSanitizers = underTest.sanitizers;

            // then
            if (actualOS.equalsIgnoreCase(SpringFeatureFlagsConfig.PROPERTY_OS_NAME_VALUE_MACOS)) {
                assertThat(activeSanitizers).hasAtLeastOneElementOfType(targetSanitizerClass);
            } else {
                assertThat(activeSanitizers).doesNotHaveAnyElementsOfTypes(targetSanitizerClass);
            }
        }

        @Test
        void depending_on_JVM_version() {
            // given
            final var targetSanitizerClass = LegacyJavaSanitizer.class;
            final var actualJVM = JavaVersion.getJavaVersion();

            // when
            final var activeSanitizers = underTest.sanitizers;

            // then
            if (actualJVM.isOlderThan(JavaVersion.TWENTY_ONE)) {
                assertThat(activeSanitizers).hasAtLeastOneElementOfType(targetSanitizerClass);
            } else {
                assertThat(activeSanitizers).doesNotHaveAnyElementsOfTypes(targetSanitizerClass);
            }
        }

        @Nested
        @TestPropertySource(properties = {
                "my.custom.features.regex-sanitizer-enabled=false"
        })
        class with_deactivated_feature_flag {

            @ParameterizedTest
            @ValueSource(strings = {
                    "Nur eine Zeile"
            })
            void and_sample_inputs(String input) {
                // given

                // when
                final var actual = underTest.sanitize(input);

                // then
                assertThat(actual).doesNotContain(RegexSanitizer.INDICATOR);
                assertThat(underTest.sanitizers).doesNotHaveAnyElementsOfTypes(RegexSanitizer.class);
            }

        }

        @Nested
        @TestPropertySource(properties = {
                "my.custom.features.regex-sanitizer-enabled=true"
        })
        class with_active_feature_flag {

            @ParameterizedTest
            @ValueSource(strings = {
                    "Nur eine Zeile",
                    "Meine IBAN lautet 'DE02120300000000202051'",
                    """
                            Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore
                            magna aliquyam erat, sed diam voluptua.
                            
                            IBAN-Liste:
                            DE02120300000000202051
                            AT023200000000641605
                            
                            Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore
                            magna aliquyam erat, sed diam voluptua. DE02120300000000202051.
                            """
            })
            void and_sample_inputs(String input) {
                // given

                // when
                final var actual = underTest.sanitize(input);

                // then
                final var softly = new SoftAssertions();
                softly.assertThat(actual).contains(RegexSanitizer.INDICATOR);
                softly.assertThat(underTest.sanitizers).hasAtLeastOneElementOfType(RegexSanitizer.class);
                softly.assertThat(actual).doesNotMatch(RegexSanitizer.IBAN_PATTERN);

                softly.assertAll();
            }

        }

    }

}

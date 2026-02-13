package de.cronoscx.c3.dojo.katas.java_exhaustive_switch;

import de.cronoscx.c3.dojo.katas.java_exhaustive_switch.ConstraintViolationException.ConstraintType;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.http.HttpStatus;

import java.util.stream.Stream;

@DisplayName("Katas :: Java :: Exhaustive Switch")
@DisplayNameGeneration(ReplaceUnderscores.class)
class JavaExhaustiveSwitchUseCasesIT {
    private final JavaExhaustiveSwitchUseCases underTest = new JavaExhaustiveSwitchUseCases();

    @Nested
    class handling {
        /**
         * Beschreibt eine Erwartungshaltung.
         *
         * @param exception die zu behandelnde Ausnahme
         * @param expectedHttpStatus der erwartete HTTP Status des Rückgabewerts
         */
        record Expectation(CustomException exception, HttpStatus expectedHttpStatus) {
        }

        /**
         * Liefert alle Erwartungen an die zu testende Funktion.
         */
        static Stream<Expectation> expectations() {
            return Stream.of(
                    new Expectation(new AuthorizationException(), HttpStatus.FORBIDDEN),
                    new Expectation(new ConstraintViolationException(ConstraintType.UNIQUE, "email_un"), HttpStatus.CONFLICT),
                    new Expectation(new ConstraintViolationException(ConstraintType.FOREIGN_KEY, "user_fk"), HttpStatus.BAD_REQUEST),
                    new Expectation(new MandatoryAttributeException("email"), HttpStatus.BAD_REQUEST),
                    new Expectation(new UnexpectedException(), HttpStatus.INTERNAL_SERVER_ERROR)
            );
        }

        @ParameterizedTest
        @MethodSource("expectations")
        void expected_exceptions(Expectation expectation) {
            // given

            // when
            final var actual = underTest.handle(expectation.exception());

            // then
            final var softly = new SoftAssertions();
            softly.assertThat(actual).isNotNull();
            softly.assertThat(actual.getStatus()).isEqualTo(expectation.expectedHttpStatus().value());
            softly.assertThat(actual.getDetail()).isNotEmpty();
            softly.assertAll();
        }

    }

}

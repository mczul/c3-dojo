package de.cronoscx.c3.dojo.katas.java_exhaustive_switch;

import de.cronoscx.c3.dojo.katas.java_exhaustive_switch.ConstraintViolationException.ConstraintType;
import org.jspecify.annotations.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

class JavaExhaustiveSwitchUseCases {

    @NonNull ProblemDetail handle(@NonNull CustomException customException) {
        return switch (customException) {
            case AuthorizationException _ -> ProblemDetail.forStatusAndDetail(
                    HttpStatus.FORBIDDEN,
                    "Operation nicht zulässig."
            );
            case MandatoryAttributeException mandatoryAttributeException -> ProblemDetail.forStatusAndDetail(
                    HttpStatus.BAD_REQUEST,
                    "Wert für Attribut \"%s\" fehlt.".formatted(mandatoryAttributeException.getAttributeName())
            );
            case ConstraintViolationException constraintViolationException when constraintViolationException.getConstraintType() == ConstraintType.UNIQUE ->
                    ProblemDetail.forStatusAndDetail(
                            HttpStatus.CONFLICT,
                            "Wert ist nicht eindeutig: %s".formatted(constraintViolationException.getConstraintName())
                    );
            case ConstraintViolationException constraintViolationException -> ProblemDetail.forStatusAndDetail(
                    HttpStatus.BAD_REQUEST,
                    "Integritätsprüfung fehlgeschlagen: %s".formatted(constraintViolationException.getConstraintName())
            );

            // -> Fallback
            default -> throw new IllegalArgumentException("\uD83D\uDCA5 Garstiger Laufzeitfehler!");
        };
    }

}

package de.cronoscx.c3.dojo.katas.java_exhaustive_switch;

import lombok.Getter;

@Getter
final class ConstraintViolationException extends DataIntegrityException {
    public enum ConstraintType {
        UNIQUE,
        FOREIGN_KEY,
    }

    private final String constraintName;
    private final ConstraintType constraintType;

    public ConstraintViolationException(ConstraintType constraintType, String constraintName) {
        this.constraintType = constraintType;
        this.constraintName = constraintName;
    }
}

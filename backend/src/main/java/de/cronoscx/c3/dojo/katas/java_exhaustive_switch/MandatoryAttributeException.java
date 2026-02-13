package de.cronoscx.c3.dojo.katas.java_exhaustive_switch;

import lombok.Getter;

@Getter
class MandatoryAttributeException extends DataIntegrityException {
    private final String attributeName;

    public MandatoryAttributeException(String attributeName) {
        this.attributeName = attributeName;
    }
}

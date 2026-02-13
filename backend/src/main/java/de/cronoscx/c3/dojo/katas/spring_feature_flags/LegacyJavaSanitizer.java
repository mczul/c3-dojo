package de.cronoscx.c3.dojo.katas.spring_feature_flags;

import org.springframework.boot.system.JavaVersion;

class LegacyJavaSanitizer implements Sanitizer {
    static final JavaVersion SUPPORTED_JAVA_VERSION = JavaVersion.TWENTY_ONE;

    @Override
    public String getIndicator() {
        return "[Legacy Java]";
    }

    @Override
    public String process(String input) {
        if (JavaVersion.getJavaVersion().equals(SUPPORTED_JAVA_VERSION)) {
            return getIndicator() + input;
        }

        throw new UnsupportedOperationException("Läuft nur auf der Version %s".formatted(SUPPORTED_JAVA_VERSION));
    }

}

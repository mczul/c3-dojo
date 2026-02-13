package de.cronoscx.c3.dojo.katas.spring_feature_flags;

class MacOsSanitizer implements Sanitizer {

    @Override
    public String getIndicator() {
        return "[MacOS]";
    }

    @Override
    public String process(String input) {
        final var actualOS = System.getProperty(Config.PROPERTY_OS_NAME_KEY);
        if (actualOS.equalsIgnoreCase(Config.PROPERTY_OS_NAME_VALUE_MACOS)) {
            return getIndicator() + input;
        }
        throw new UnsupportedOperationException("Läuft nicht auf %s, Lasse \uD83D\uDE44".formatted(actualOS));
    }

}

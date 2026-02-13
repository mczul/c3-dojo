package de.cronoscx.c3.dojo.katas.spring_feature_flags;

class AwsEcsSanitizer implements Sanitizer {

    @Override
    public String getIndicator() {
        return "[AWS ECS]";
    }

    @Override
    public String process(String input) {
        throw new UnsupportedOperationException("Läuft sehr wahrscheinlich nicht auf einer ECS-Instanz in AWS.");
    }

}

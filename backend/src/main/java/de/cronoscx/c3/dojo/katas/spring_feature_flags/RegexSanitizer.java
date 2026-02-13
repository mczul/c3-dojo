package de.cronoscx.c3.dojo.katas.spring_feature_flags;


import lombok.RequiredArgsConstructor;

import java.util.regex.Pattern;

@RequiredArgsConstructor
class RegexSanitizer implements Sanitizer {
    static final String INDICATOR = "[RegEx]";
    protected static final Pattern IBAN_PATTERN = Pattern.compile("[a-z]{2}[0-9]{18,20}", Pattern.MULTILINE | Pattern.CASE_INSENSITIVE);

    protected final ConfigProperties configProperties;

    @Override
    public String getIndicator() {
        return INDICATOR;
    }

    @Override
    public String process(String input) {
        if (configProperties.regexSanitizerEnabled()) {
            return getIndicator() + IBAN_PATTERN
                    .matcher(input)
                    .replaceAll("__IBAN_REMOVED__");
        }

        throw new UnsupportedOperationException("Entsprechendes Feature wurde nicht aktiviert!");
    }

}

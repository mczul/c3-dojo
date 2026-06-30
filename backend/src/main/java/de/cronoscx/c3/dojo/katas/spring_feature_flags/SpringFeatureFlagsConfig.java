package de.cronoscx.c3.dojo.katas.spring_feature_flags;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBooleanProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({ConfigProperties.class})
class SpringFeatureFlagsConfig {
    public static final String PROPERTY_OS_NAME_KEY = "os.name";
    public static final String PROPERTY_OS_NAME_VALUE_MACOS = "Mac OS X";

    @Bean
    Sanitizer regexSanitizer(ConfigProperties configProperties) {
        return new RegexSanitizer(configProperties);
    }

    @Bean
    Sanitizer legacyJavaSanitizer() {
        return new LegacyJavaSanitizer();
    }

    @Bean
    Sanitizer awsEcsSanitizer() {
        return new AwsEcsSanitizer();
    }

    @Bean
    Sanitizer macOsSanitizer() {
        return new MacOsSanitizer();
    }

}

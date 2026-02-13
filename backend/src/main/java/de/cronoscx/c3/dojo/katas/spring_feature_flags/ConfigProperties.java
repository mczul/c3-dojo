package de.cronoscx.c3.dojo.katas.spring_feature_flags;

import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "my.custom.features")
record ConfigProperties(@NotNull(message = "feature flag is mandatory") Boolean regexSanitizerEnabled) {
}

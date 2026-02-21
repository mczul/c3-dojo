package de.cronoscx.c3.dojo;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties("c3.dojo.management")
record DojoManagementConfigProperties(@NotBlank @Length(min = 3, max = 20) String username,
                                      @NotBlank @Length(min = 6) String password) {
}

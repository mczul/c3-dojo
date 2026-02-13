package de.cronoscx.c3.dojo.katas.spring_feature_flags;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(SpringFeatureFlagsUseCases.API_PATH)
@RequiredArgsConstructor
class SpringFeatureFlagsUseCases {
    static final String API_PATH = "/spring-feature-flags";

    /**
     * Umfasst alle aktiven {@link Sanitizer} Instanzen.
     */
    protected final List<Sanitizer> sanitizers;

    /**
     * Nimmt die textuellen Quelldaten entgegen und bereinigt diese u.a. von schützenswerten Infos.
     */
    @PutMapping(path = "sanitize")
    String sanitize(@RequestBody String input) {
        var output = input;
        for (Sanitizer sanitizer : sanitizers) {
            output = sanitizer.process(output);
        }
        return output;
    }

}

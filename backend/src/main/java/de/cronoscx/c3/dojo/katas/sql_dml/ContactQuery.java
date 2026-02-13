package de.cronoscx.c3.dojo.katas.sql_dml;

import org.jspecify.annotations.Nullable;

import java.time.Instant;
import java.util.Optional;

record ContactQuery(@Nullable String email,
                    @Nullable Instant createdAfter,
                    @Nullable Instant createdBefore) {
    Optional<String> getEmail() {
        return Optional.ofNullable(email);
    }

    Optional<Instant> getCreatedAfter() {
        return Optional.ofNullable(createdAfter);
    }

    Optional<Instant> getCreatedBefore() {
        return Optional.ofNullable(createdBefore);
    }
}

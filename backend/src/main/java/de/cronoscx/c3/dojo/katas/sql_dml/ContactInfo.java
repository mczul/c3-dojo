package de.cronoscx.c3.dojo.katas.sql_dml;

import java.time.Instant;
import java.util.UUID;

record ContactInfo(UUID id, String email, String name, Instant createdAt) {
}


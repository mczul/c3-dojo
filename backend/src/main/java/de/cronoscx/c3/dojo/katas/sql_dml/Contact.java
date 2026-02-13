package de.cronoscx.c3.dojo.katas.sql_dml;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.hibernate.annotations.Immutable;

import java.time.Instant;
import java.util.UUID;

@Immutable
@Entity
@Table(schema = "sql_dml", name = "contacts")
@Getter
class Contact {
    @Id
    private UUID id;

    @NotNull
    private String name;

    @NotNull
    private String email;

    @NotNull
    private Instant createdAt;

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o instanceof Contact other) {
            return id != null &&
                    id.equals(other.getId());
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}

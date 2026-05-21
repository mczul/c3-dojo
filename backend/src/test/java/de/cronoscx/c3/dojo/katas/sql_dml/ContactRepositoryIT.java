package de.cronoscx.c3.dojo.katas.sql_dml;

import de.cronoscx.c3.dojo.TestcontainersConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Katas :: Persistence :: Contacts")
@DisplayNameGeneration(ReplaceUnderscores.class)
@DataJpaTest
@ContextConfiguration(classes = {TestcontainersConfig.class})
@TestPropertySource(properties = {
        "spring.liquibase.contexts=dev",
        "spring.jpa.properties.hibernate.generate_statistics=true",
        "logging.level.org.hibernate.session.metrics=DEBUG",
})
class ContactRepositoryIT {
    @Autowired
    private ContactRepository underTest;

    @Test
    void preconditions() {
        // given

        // when
        final var actual = underTest.findAll(Pageable.ofSize(1));

        // then
        assertThat(actual.getTotalElements())
                .as("Less contacts in test database than expected")
                .isGreaterThan(5);
    }

    @Nested
    class Search {

        @Test
        void without_restrictions() {
            // given
            final var query = new ContactQuery(null, null, null);
            final var spec = underTest.buildSpecification(query);
            final var expected = underTest.findAll(Pageable.unpaged());

            // when
            final var actual = underTest.findAll(spec, Pageable.unpaged());

            // then
            assertThat(actual)
                    .containsExactlyInAnyOrderElementsOf(expected);
        }

        @ParameterizedTest
        @ValueSource(strings = {
                "test",
                "test@example",
                "test@example.org",
        })
        void by_email(String email) {
            // given
            final var query = new ContactQuery(email, null, null);
            final var spec = underTest.buildSpecification(query);
            final var expected = underTest.findAll(Pageable.unpaged())
                    .filter(contact -> contact.getEmail().toLowerCase().startsWith(email.toLowerCase()));

            // when
            final var actual = underTest.findAll(spec, Pageable.unpaged());

            // then
            assertThat(actual)
                    .containsExactlyInAnyOrderElementsOf(expected);
        }

    }

}

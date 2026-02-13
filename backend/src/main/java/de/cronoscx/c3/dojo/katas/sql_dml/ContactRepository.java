package de.cronoscx.c3.dojo.katas.sql_dml;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.RepositoryDefinition;

import java.util.ArrayList;
import java.util.UUID;

@RepositoryDefinition(domainClass = Contact.class, idClass = UUID.class)
interface ContactRepository {
    default Specification<Contact> buildSpecification(ContactQuery query) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            final var predicates = new ArrayList<Predicate>();

            query.getEmail()
                    .filter(value -> !value.isBlank())
                    .map(value -> String.format("%%%s%%", value))
                    .ifPresent(email -> predicates.add(
                    criteriaBuilder.like(root.get(Contact_.email), email)
            ));
            query.getCreatedAfter().ifPresent(after -> predicates.add(
                    criteriaBuilder.greaterThanOrEqualTo(root.get(Contact_.createdAt), after)
            ));
            query.getCreatedBefore().ifPresent(before -> predicates.add(
                    criteriaBuilder.lessThanOrEqualTo(root.get(Contact_.createdAt), before)
            ));

            return predicates.stream()
                    .reduce(criteriaBuilder::and)
                    .orElseGet(criteriaBuilder::conjunction);
        };
    }

    Page<Contact> findAll(Specification<Contact> specification, Pageable pageable);
}

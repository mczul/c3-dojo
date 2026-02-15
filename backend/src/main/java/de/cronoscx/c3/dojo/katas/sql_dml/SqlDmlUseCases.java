package de.cronoscx.c3.dojo.katas.sql_dml;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(SqlDmlUseCases.API_PATH)
@RequiredArgsConstructor
class SqlDmlUseCases {
    static final String API_PATH = "/sql-dml";

    private final ContactRepository contactRepository;
    private final ContactMapper contactMapper;

    @PostMapping(path = "search")
    Page<ContactInfo> search(@NotNull @Valid @RequestBody ContactQuery query,
                             Pageable pageable) {
        final var spec = contactRepository.buildSpecification(query);
        return contactRepository.findAll(spec, pageable)
                .map(contactMapper::toInfo);
    }

}

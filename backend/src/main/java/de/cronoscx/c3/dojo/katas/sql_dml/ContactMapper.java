package de.cronoscx.c3.dojo.katas.sql_dml;

import org.mapstruct.Mapper;

@Mapper
interface ContactMapper {
    ContactInfo toInfo(Contact contact);
}

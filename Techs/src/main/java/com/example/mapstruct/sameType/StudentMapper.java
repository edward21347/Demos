package com.example.mapstruct.sameType;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface StudentMapper {
    @Mapping(target = "SId",source = "id")
    StudentDto toDTO(Student student);
}

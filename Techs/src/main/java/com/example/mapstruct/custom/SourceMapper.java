package com.example.mapstruct.custom;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SourceMapper {


    @Mapping(source = "sourceName", target = "targetName")
    Target toTarget(Source source);

    default InnerTarget innerTarget2InnerSource(InnerSource innerSource) {
        if (innerSource == null) {
            return null;
        }
        InnerTarget innerTarget = new InnerTarget();
        innerTarget.setIsDeleted(innerSource.getDeleted() == 1);
        innerTarget.setName(innerSource.getName());
        return innerTarget;
    }
}

package com.example.mapstruct.custom;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(target = "total",expression = "java(product.num * product.price)")
    ProductDto toDto(Product product);

    @InheritInverseConfiguration(name = "toDto")
    Product toPo(ProductDto productDto);
}

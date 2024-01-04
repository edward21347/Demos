package com.example.mapstruct.custom;

import lombok.Data;

@Data
public class Source {

    private Integer id;

    private String sourceName;

    private InnerSource innerSource;
}

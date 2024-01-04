package com.example.mapstruct.custom;

import lombok.Data;

@Data
public class Target {

    private Integer id;

    private String targetName;

    private InnerTarget innerTarget;
}

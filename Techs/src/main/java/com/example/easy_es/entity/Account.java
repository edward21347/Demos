package com.example.easy_es.entity;

import cn.easyes.annotation.IndexName;
import lombok.Data;

@Data
@IndexName("bank")
public class Account {
    //必须有id
    private String id;

    private Long accountNumber;
    private String address;
    private Long age;
    private Long balance;
    private String city;
    private String email;
    private String employer;
    private String firstname;
    private String gender;
    private String lastname;
    private String state;
}

package com.example.easy_es.service;

import com.example.easy_es.esmapper.AccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountEsService {
    @Autowired
    private AccountMapper mapper;


}

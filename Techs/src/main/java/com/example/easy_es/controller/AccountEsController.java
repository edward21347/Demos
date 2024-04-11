package com.example.easy_es.controller;

import cn.easyes.core.conditions.select.LambdaEsQueryWrapper;
import com.example.easy_es.entity.Account;
import com.example.easy_es.esmapper.AccountMapper;
import com.example.easy_es.service.AccountEsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/account/es")
public class AccountEsController {
    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private AccountEsService service;
    @GetMapping("/list")
    public List<Account> list(){
        LambdaEsQueryWrapper<Account> wrapper = new LambdaEsQueryWrapper<>();
        return accountMapper.selectList(wrapper);
    }


}

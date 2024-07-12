package com.example.libreoffice.controller;

import com.example.libreoffice.WordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.net.URLEncoder;

@RestController
@RequestMapping("/word")
public class WordController {
    @Autowired
    private WordUtils wordUtils;

    @GetMapping("/getWord")
    public String getWord(@RequestParam String path, HttpServletResponse resp)throws Exception{
        resp.setHeader("Content-Disposition","attachment; filename=" + URLEncoder.encode("备考表.pdf","UTF-8"));
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/octet-stream");
        ServletOutputStream outputStream = resp.getOutputStream();

        FileInputStream fileInputStream = new FileInputStream(path);

        wordUtils.wordToPdf(fileInputStream, outputStream);

        return "调用成功!";
    }
}

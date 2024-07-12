package com.example.libreoffice;

import org.jodconverter.core.DocumentConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.OutputStream;

@Service
public class WordUtils {
    @Autowired
    private DocumentConverter converter;

    public void wordToPdf(InputStream inputStream, OutputStream outputStream)throws Exception{
        converter.convert(inputStream).to(outputStream)
                .as(converter.getFormatRegistry().getFormatByExtension("pdf")).execute();

    }
}

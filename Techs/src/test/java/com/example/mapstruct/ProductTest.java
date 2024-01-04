package com.example.mapstruct;

import com.example.mapstruct.custom.Product;
import com.example.mapstruct.custom.ProductDto;
import com.example.mapstruct.custom.ProductMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class ProductTest {
    @Resource
    private ProductMapper productMapper;

    @Test
    public void test(){
        Product product = new Product();
        product.setNum(10);
        product.setPrice(2.0);

        ProductDto productDto = productMapper.toDto(product);
        System.out.println(productDto);

        Product po = productMapper.toPo(productDto);
        System.out.println(po);
    }
}

package com.example.mapstruct;

import com.example.mapstruct.custom.InnerSource;
import com.example.mapstruct.custom.Source;
import com.example.mapstruct.custom.SourceMapper;
import com.example.mapstruct.custom.Target;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
@SpringBootTest
public class SourceTest {
    @Resource
    private SourceMapper sourceMapper;
    @Test
    public void sourceTest() {
        Source source = new Source();
        InnerSource innerSource = new InnerSource();
        innerSource.setName("小夫");
        innerSource.setDeleted(1);
        source.setId(123);
        source.setSourceName("王小夫");
        source.setInnerSource(innerSource);

        Target target = sourceMapper.toTarget(source);
        System.out.println(target);
    }
}

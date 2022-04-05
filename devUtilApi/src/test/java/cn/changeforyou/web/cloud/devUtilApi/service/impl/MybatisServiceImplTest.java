package cn.changeforyou.web.cloud.devUtilApi.service.impl;

import cn.changeforyou.utils.io.read.ReadUtils;
import cn.changeforyou.web.cloud.devUtilApi.parser.ast.sourceCodeContext.utils.SingleContextScanner;
import cn.changeforyou.web.cloud.devUtilApi.parser.ast.sourceCodeContext.utils.separator.dictionary.MybatisSeparatorCharDictionary;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;

class MybatisServiceImplTest {

    @Test   
    public void test3() {
        BigDecimal successRate = BigDecimal.valueOf(2).multiply(BigDecimal.valueOf(100)).divide(BigDecimal.valueOf(3),2, RoundingMode.HALF_UP);
        System.out.println(successRate + "%");
    }

    @Test
    public void test() {
        InputStream resourceAsStream = MybatisServiceImplTest.class.getClassLoader().getResourceAsStream("2.txt");
        String s = ReadUtils.readReaderToString(new InputStreamReader(resourceAsStream));

    }

    @Test
    public void test2() {
        InputStream resourceAsStream = MybatisServiceImplTest.class.getClassLoader().getResourceAsStream("11.txt");
        String s = ReadUtils.readReaderToString(new InputStreamReader(resourceAsStream));
        s = s.replaceAll("\r\n|\r|\n", " ").replaceAll(" +", " ");
        SingleContextScanner singleContextScanner = new SingleContextScanner(s, MybatisSeparatorCharDictionary.instance);
        System.out.println(singleContextScanner.next());
    }
}
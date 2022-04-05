package cn.changeforyou.web.cloud.devUtilApi.parser.ast.sourceCodeContext.utils;

import cn.changeforyou.utils.io.read.ReadUtils;
import cn.changeforyou.web.cloud.devUtilApi.parser.ast.sourceCodeContext.utils.separator.dictionary.MybatisSeparatorCharDictionary;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.util.List;

class AggregationContextScannerTest {

    @Test
    void test(){
        InputStream resourceAsStream = AggregationContextScanner.class.getClassLoader().getResourceAsStream("sql集合.txt");
        List<String> list = ReadUtils.readLinesToList(resourceAsStream);
        for (String s : list) {
            s = s.replaceAll("\r\n|\r|\n", " ").replaceAll(" +", " ");
            SingleContextScanner singleContextScanner = new SingleContextScanner(s, MybatisSeparatorCharDictionary.instance);
            singleContextScanner.printWordsWithSeparate();
        }
    }
}
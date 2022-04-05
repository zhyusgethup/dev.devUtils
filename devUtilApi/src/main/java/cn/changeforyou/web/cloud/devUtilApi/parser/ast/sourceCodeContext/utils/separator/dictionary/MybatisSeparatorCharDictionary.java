package cn.changeforyou.web.cloud.devUtilApi.parser.ast.sourceCodeContext.utils.separator.dictionary;

import java.util.Arrays;
import java.util.List;

/**
 * mybatis 字典
 */
public class MybatisSeparatorCharDictionary extends SeparatorDictionaryAdaptor {
    private MybatisSeparatorCharDictionary() {
    }

    public static final MybatisSeparatorCharDictionary instance = new MybatisSeparatorCharDictionary();
    @Override
    public List<SeparatorCharDictionary> getFatherSeparatorCharDictionary() {
        return Arrays.asList(NglSeparatorCharDictionary.instance, SqlSeparatorCharDictionary.instance);
    }
}

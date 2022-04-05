package cn.changeforyou.web.cloud.devUtilApi.parser.ast.sourceCodeContext.utils;

import cn.changeforyou.web.cloud.devUtilApi.parser.ast.sourceCodeContext.utils.separator.dictionary.MybatisSeparatorCharDictionary;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class SingleContextScannerTest {

    @Test
    void testScanOnce() {
        SingleContextScanner sc = new SingleContextScanner(MybatisSeparatorCharDictionary.instance);
        sc.printSetting();
        sc.scan("INSERT INTO mdg_ff_materials(id, tt,bb) VALUES (1,\"123   456\",   #{name})");
        assertEquals(sc, "INSERT", "INTO", "mdg_ff_materials", "(id, tt,bb)", "VALUES", "(1,\"123   456\",   #{name})");

        sc.scan("id  tt bb");
        assertEquals(sc, "id", "tt", "bb");

        sc.scan("\"123 456\"  #{ name}");
        assertEquals(sc, "\"123 456\"", "#{ name}");

        sc.scan("insert into mdg_ff_materials(id, name) values(1, '激情燃烧的岁月');");
        assertEquals(sc, "insert", "into", "mdg_ff_materials", "(id, name)", "values", "(1, '激情燃烧的岁月')", ";");
    }

    private void print(SingleContextScanner sc2) {
        int index = 0;
        while (sc2.hasNext()) {
            System.out.print("[" + index + "] " + sc2.next() + " ");
            index++;
        }
        System.out.print("\n");
    }

    private void assertEquals(SingleContextScanner sc2, String... words) {
        List<String> result = new ArrayList<>();
        while (sc2.hasNext()) {
            result.add(sc2.next().getWord());
        }
        if (result.size() != words.length) {
            System.err.println("分隔的数量不同, 期待段数数量:" + words.length + "   实际的数量:" + result.size() + " " + result);
        } else {
            System.out.println("分隔的段数合格");
        }

        boolean isOk = true;
        for (int i = 0; i < words.length; i++) {
            if (!words[i].equals(result.get(i))) {
                System.err.println("第" + (i + 1) + "段不同, 期待:(" + words[i] + ")  实际:(" + result.get(i) + ")");
                isOk = false;
            }
        }
        if (isOk) {
            System.out.println("字符合格: " + result);
        } else {
            assert false;
        }
    }
}
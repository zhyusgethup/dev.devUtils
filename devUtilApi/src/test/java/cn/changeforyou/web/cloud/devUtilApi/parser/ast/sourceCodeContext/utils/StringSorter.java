package cn.changeforyou.web.cloud.devUtilApi.parser.ast.sourceCodeContext.utils;

import cn.changeforyou.utils.io.read.ReadUtils;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StringSorter {
    @Test
    public void test() {
        Logger log = LoggerFactory.getLogger("1");
        List<String> list1 = ReadUtils.readLinesToList(StringSorter.class.getClassLoader().getResourceAsStream("stringSort1.txt"));
        List<String> list2 = ReadUtils.readLinesToList(StringSorter.class.getClassLoader().getResourceAsStream("stringSort2.txt"));

        int size1 = list1.size();
        int size2 = list2.size();

        long count1 = list1.stream().distinct().count();
        long count2 = list2.stream().distinct().count();

        List<String> r1 = list1.stream().distinct().sorted().collect(Collectors.toList());
        List<String> r2 = list2.stream().distinct().sorted().collect(Collectors.toList());

        int size3 = r1.size();
        int size4 = r2.size();

        ArrayList<String> c1 = new ArrayList<>(r1);
        ArrayList<String> c2 = new ArrayList<>(r2);

        c1.removeAll(r2);
        c2.removeAll(r1);

        log.info("第一组共计{}条, 有效{}条, 独有的{}", size1, size3, c1);
        log.info("第二组共计{}条, 有效{}条, 独有的{}", size2, size4, c2);
    }
}

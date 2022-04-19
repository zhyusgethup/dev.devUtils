package cn.changeforyou.web.cloud.devUtilApi.parser;

import cn.changeforyou.utils.io.read.ReadUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhyu
 * @version 1.0
 * @date 2022/4/19 13:25
 */

public class ParseHtml {

    @Test
    void test() {
        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream("a.text");
        String s = ReadUtils.readReaderToString(new InputStreamReader(resourceAsStream));
        System.out.println(s);

        Document doc = Jsoup.parse(s);
        Element table = doc.select("table").get(0);
        Elements ths = table.select("thead").select("tr");
        Elements rows = table.select("tbody").select("tr");
        List<List<String>> tables = new ArrayList<>();
        List<String> th = new ArrayList<>();
        tables.add(th);
        for (Element row : ths) {
            Elements td = row.select("td");
            for (Element element : td) {
                th.add(element.text());
            }
        }

        if (rows.size() == 1) {
            System.out.println("没有结果");
        } else {
            for (int i = 1; i < rows.size(); i++) {
                Element row = rows.get(i);
                List<String> tdd = new ArrayList<>();
                tables.add(tdd);
                Elements tds = row.select("td");
                for (Element td : tds) {
                    tdd.add(td.text());
                }
            }
        }

        List<String> strings = tables.get(0);
        System.out.println("表头:-----");
        System.out.println(strings);
        System.out.println("表体-----------------------------------------------------------------");
        for (int i = 1; i < tables.size(); i++) {
            System.out.println(tables.get(i));
        }
    }
}

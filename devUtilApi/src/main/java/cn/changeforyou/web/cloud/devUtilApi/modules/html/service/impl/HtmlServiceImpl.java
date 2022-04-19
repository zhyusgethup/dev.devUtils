package cn.changeforyou.web.cloud.devUtilApi.modules.html.service.impl;

import cn.changeforyou.utils.string.StringUtils;
import cn.changeforyou.web.cloud.devUtilApi.modules.html.service.HtmlService;
import cn.changeforyou.web.cloud.devUtilApi.modules.html.service.entity.HtmlTable;
import cn.changeforyou.web.cloud.devUtilApi.modules.html.service.vo.HtmlTableVO;
import cn.changeforyou.web.cloud.devUtilApi.modules.html.service.vo.table.TableColumn;
import cn.changeforyou.web.cloud.devUtilApi.parser.ast.ParseCodeException;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhyu
 * @version 1.0
 * @date 2022/4/19 16:37
 */
@Service
@Slf4j
public class HtmlServiceImpl implements HtmlService {

    @Override
    public List<HtmlTable> parseMultipartFile2HtmlTable(MultipartFile[] files) {
        List<HtmlTable> htmlTables = new ArrayList<>();
        for (MultipartFile file : files) {
            htmlTables.add(parseMultipartFile2HtmlTable(file));
        }
        return htmlTables;
    }

    @Override
    public HtmlTable parseMultipartFile2HtmlTable(MultipartFile file) {
        InputStream inputStream = null;
        HtmlTable htmlTable = new HtmlTable();
        htmlTable.setFileName(file.getOriginalFilename());
        try {
            inputStream = file.getInputStream();
            byte[] bytes = new byte[inputStream.available()];
            inputStream.read(bytes);

            String html = dealEncoding(bytes);
            parseHtml2HtmlTable(html, htmlTable);
            log.info("解析出HtmlTable: {}", htmlTable);
        } catch (IOException e) {
            e.printStackTrace();
            htmlTable.setErrorInfo("io错误:" + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            htmlTable.setErrorInfo("异常:" + e.getMessage());
        }
        return htmlTable;
    }

    @Override
    public List<HtmlTableVO> parseMultipartFile2HtmlTableVO(MultipartFile[] files) {
        List<HtmlTable> htmlTables = parseMultipartFile2HtmlTable(files);
        List<HtmlTableVO> vos = new ArrayList<>();
        for (HtmlTable htmlTable : htmlTables) {
            HtmlTableVO vo = new HtmlTableVO();
            vos.add(vo);
            vo.setFileName(htmlTable.getFileName());
            if (htmlTable.getErrorInfo() != null) {
                vo.setErrorInfo(htmlTable.getErrorInfo());
                continue;
            }
            vo.setTableTitle(htmlTable.getTitle());

            List<String> thead = htmlTable.getThead();
            List<TableColumn> tableColumns = new ArrayList<>();
            vo.setTableColumns(tableColumns);
            for (int i = 0; i < thead.size(); i++) {
                TableColumn tableColumn = new TableColumn();
                tableColumn.setTitle(thead.get(i));
                tableColumn.setDataIndex("column" + i);
                tableColumns.add(tableColumn);
            }


            List<List<String>> tbody = htmlTable.getTbody();
            List<Map<String, String>> data = new ArrayList<>();
            vo.setData(data);
            for (List<String> tr : tbody) {
                Map<String, String> map = new HashMap<>(thead.size());
                data.add(map);
                for (int i = 0; i < tr.size(); i++) {
                    map.put("column" + i, tr.get(i));
                }
            }
        }
        return vos;
    }

    /**
     * 处理字符集
     *
     * @param bytes
     * @return
     */
    private String dealEncoding(byte[] bytes) {
        String html = new String(bytes);
        Document doc = Jsoup.parse(html);
        Elements meta = doc.select("meta");
        if (null != meta) {
            String charset = meta.attr("charset");
            if (StringUtils.isBlank(charset)) {
                String content = meta.attr("content");
                if (StringUtils.isNotBlank(content)) {
                    int index = content.indexOf("charset");
                    if (index != -1) {
                        charset = content.substring(index + 8);
                    }
                }
            }
            if (StringUtils.isNotBlank(charset)) {
                try {
                    html = new String(bytes, charset);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    throw new ParseCodeException("字符集错误");
                }
            }
        }
        return html;
    }

    /**
     * 转换html字符串为HtmlTable模型
     *
     * @param html
     * @return
     */
    public void parseHtml2HtmlTable(String html, HtmlTable htmlTable) {
        Document doc = Jsoup.parse(html);
        htmlTable.setTitle(doc.select("title").text());
        Element table = doc.select("table").get(0);
        Elements ths = table.select("thead").select("tr");
        Elements rows = table.select("tbody").select("tr");

        for (Element row : ths) {
            Elements td = row.select("td");
            for (Element element : td) {
                htmlTable.getThead().add(element.text());
            }
        }

        for (int i = 0; i < rows.size(); i++) {
            Element row = rows.get(i);
            List<String> tdd = new ArrayList<>();
            htmlTable.getTbody().add(tdd);
            Elements tds = row.select("td");
            for (Element td : tds) {
                tdd.add(td.text());
            }
        }
    }
}

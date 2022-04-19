package cn.changeforyou.web.cloud.devUtilApi.modules.html.service.entity;

import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhyu
 * @version 1.0
 * @date 2022/4/19 16:40
 */
@Data
@ToString
public class HtmlTable {
    private String fileName;
    private String title;
    private List<String> thead;
    private List<List<String>> tbody;
    private String errorInfo;

    public HtmlTable() {
        thead = new ArrayList<>();
        tbody = new ArrayList<>();
    }
}

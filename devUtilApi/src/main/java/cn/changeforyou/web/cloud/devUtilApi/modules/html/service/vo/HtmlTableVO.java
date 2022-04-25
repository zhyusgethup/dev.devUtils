package cn.changeforyou.web.cloud.devUtilApi.modules.html.service.vo;

import cn.changeforyou.web.cloud.devUtilApi.modules.html.service.vo.table.TableColumn;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author zhyu
 * @version 1.0
 * @date 2022/4/19 19:40
 */
@Data
public class HtmlTableVO {

    private String tableTitle;
    private String fileName;
    private List<TableColumn> tableColumns;
    private List<Map<String, String>> data;
    private String errorInfo;
    private String comment;
}

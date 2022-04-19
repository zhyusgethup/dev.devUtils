package cn.changeforyou.web.cloud.devUtilApi.modules.html.service;

import cn.changeforyou.web.cloud.devUtilApi.modules.html.service.entity.HtmlTable;
import cn.changeforyou.web.cloud.devUtilApi.modules.html.service.vo.HtmlTableVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface HtmlService {
    List<HtmlTable> parseMultipartFile2HtmlTable(MultipartFile[] files);

    HtmlTable parseMultipartFile2HtmlTable(MultipartFile file);

    List<HtmlTableVO> parseMultipartFile2HtmlTableVO(MultipartFile[] files);
}

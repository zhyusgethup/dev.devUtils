package cn.changeforyou.web.cloud.devUtilApi.controller;

import cn.changeforyou.web.cloud.devUtilApi.common.model.ResultWithEncoded;
import cn.changeforyou.web.cloud.devUtilApi.modules.html.service.HtmlService;
import cn.changeforyou.web.cloud.devUtilApi.modules.html.service.vo.HtmlTableVO;
import cn.changeforyou.web.cloud.webBase.common.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author zhyu
 * @version 1.0
 * @date 2022/4/19 14:04
 */
@RestController
@RequestMapping("html")
@Slf4j
public class HtmlController {
    private HtmlService htmlService;

    public HtmlController(HtmlService htmlService) {
        this.htmlService = htmlService;
    }

    @PostMapping("uploadHtml")
    public Result<List<HtmlTableVO>> uploadHtml(MultipartFile[] files) {
        return ResultWithEncoded.success(htmlService.parseMultipartFile2HtmlTableVO(files));
    }
}

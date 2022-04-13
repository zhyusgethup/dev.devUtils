package cn.changeforyou.web.cloud.devUtilApi.controller;

import cn.changeforyou.web.cloud.devUtilApi.common.model.ResultWithEncoded;
import cn.changeforyou.web.cloud.devUtilApi.modules.sql.SqlService;
import cn.changeforyou.web.cloud.devUtilApi.modules.sql.model.DdlRequestModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

@Controller
@RequestMapping("sql")
public class SqlController {

    @Autowired
    private SqlService sqlService;

    @PostMapping("ddl2Entity")
    @ResponseBody
    public ResultWithEncoded jsonFormat(@Valid @RequestBody DdlRequestModel model) {
        return ResultWithEncoded.success(sqlService.ddl2Entity(model));
    }
}

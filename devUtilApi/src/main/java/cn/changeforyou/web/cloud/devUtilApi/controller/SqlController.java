package cn.changeforyou.web.cloud.devUtilApi.controller;

import cn.changeforyou.base.exception.DataExceptionEnum;
import cn.changeforyou.web.cloud.devUtilApi.common.model.ResultWithEncoded;
import cn.changeforyou.web.cloud.devUtilApi.common.model.StringReqModel;
import cn.changeforyou.web.cloud.devUtilApi.modules.sql.SqlService;
import cn.changeforyou.web.cloud.webBase.common.model.Result;
import cn.hutool.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("sql")
public class SqlController {

    @Autowired
    private SqlService sqlService;

    @PostMapping("ddl2Entity")
    @ResponseBody
    public ResultWithEncoded jsonFormat(@Valid @RequestBody StringReqModel model) {
        return ResultWithEncoded.success(sqlService.ddl2Entity(model.getValue()));
    }



}

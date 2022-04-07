package cn.changeforyou.web.cloud.devUtilApi.controller;

import cn.changeforyou.base.exception.DataExceptionEnum;
import cn.changeforyou.web.cloud.devUtilApi.common.model.ResultWithEncoded;
import cn.changeforyou.web.cloud.devUtilApi.common.model.StringReqModel;
import cn.changeforyou.web.cloud.webBase.common.model.Result;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONException;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.SortedMap;
import java.util.TreeMap;

@RestController
@RequestMapping("json")
public class JsonController {

    @PostMapping("format")
    @ResponseBody
    public Result jsonFormat(@Valid @RequestBody StringReqModel model) {
        String src = model.getValue();
        src = src.trim();
        String result = null;
        if (src.startsWith("{")) {
            JSONObject jsonObject = JSONUtil.parseObj(src);
            SortedMap<Object, Object> sortedMap = new TreeMap(jsonObject);
            result = JSONUtil.formatJsonStr(JSONUtil.toJsonStr(sortedMap));
        } else {
            JSONArray jsonArray = JSONUtil.parseArray(src);
            result = jsonArray.toStringPretty();
        }
        return ResultWithEncoded.success(result);
    }

    @ExceptionHandler(JSONException.class)
    public Result exceptionHandler(JSONException e) {
        return Result.fail(DataExceptionEnum.PARAM_ERROR);
    }
}

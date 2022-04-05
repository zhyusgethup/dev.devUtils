package cn.changeforyou.web.cloud.devUtilApi.controller;

import cn.changeforyou.web.cloud.webBase.common.model.Result;
import cn.changeforyou.web.cloud.webBase.platform.PlatformEnum;
import cn.changeforyou.web.cloud.webBase.platform.PlatformStringUtils;
import cn.changeforyou.web.cloud.webBase.platform.PlatformUtils;
import cn.changeforyou.web.cloud.devUtilApi.Constant;
import cn.changeforyou.web.cloud.devUtilApi.modules.json.StringReqModel;
import cn.changeforyou.web.cloud.devUtilApi.modules.json.StringRespModel;
import cn.hutool.core.codec.Base64;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.nio.charset.StandardCharsets;
import java.util.SortedMap;
import java.util.TreeMap;

@Controller
@RequestMapping("json")
public class JsonController {

    @PostMapping("format")
    @ResponseBody
    public Result<StringRespModel> jsonFormat(@Valid StringReqModel model) {
        byte[] decode = Base64.decode(model.getValue());
        model.setValue(new String(decode, StandardCharsets.UTF_8));
        String src = model.getValue();
        src = src.trim();
        String result;
        if(src.startsWith("{")) {
            JSONObject jsonObject = JSONUtil.parseObj(src);
            SortedMap<Object, Object> sortedMap = new TreeMap(jsonObject);
            result = JSONUtil.formatJsonStr(JSONUtil.toJsonStr(sortedMap));
        }else {
            JSONArray jsonArray = JSONUtil.parseArray(src);
            result = jsonArray.toStringPretty();
        }
        HttpServletRequest request =((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        PlatformEnum platform = PlatformUtils.getPlatform(request);
        result = PlatformStringUtils.getStringAdaptPlatform(platform, result);
        StringRespModel respModel = new StringRespModel();
        if(Constant.base64.equalsIgnoreCase(model.getEncode())){
            result = Base64.encode(result);
            respModel.setEncode(Constant.base64);
        }
        respModel.setValue(result);
        return Result.success(respModel);
    }
}

package cn.changeforyou.web.cloud.devUtilApi.controller;

import cn.changeforyou.web.cloud.webBase.common.model.Result;
import cn.changeforyou.web.cloud.devUtilApi.Constant;
import cn.changeforyou.web.cloud.devUtilApi.modules.json.StringReqModel;
import cn.changeforyou.web.cloud.devUtilApi.modules.json.StringRespModel;
import cn.hutool.core.codec.Base64;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.nio.charset.StandardCharsets;

@Controller
@RequestMapping("mybatis")
public class MybatisController {

    @PostMapping("insert2update")
    @ResponseBody
    public Result<StringRespModel> insert2update(@Valid StringReqModel model) {
        byte[] decode = Base64.decode(model.getValue());
        model.setValue(new String(decode, StandardCharsets.UTF_8));
        String src = model.getValue();
        src = src.trim();
//        String result = mybatisService.insertContent2updateContent(src);
        String result = "";
        StringRespModel respModel = new StringRespModel();
        if(Constant.base64.equalsIgnoreCase(model.getEncode())){
            result = Base64.encode(result);
            respModel.setEncode(Constant.base64);
        }
        respModel.setValue(result);
        return Result.success(respModel);
    }

}

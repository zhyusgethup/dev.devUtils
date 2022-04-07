package cn.changeforyou.web.cloud.devUtilApi.controller;

import cn.changeforyou.web.cloud.devUtilApi.common.model.ResultWithEncoded;
import cn.changeforyou.web.cloud.devUtilApi.common.model.StringReqModel;
import cn.changeforyou.web.cloud.devUtilApi.common.model.StringRespModel;
import cn.changeforyou.web.cloud.webBase.common.model.Result;
import cn.changeforyou.web.cloud.webBase.platform.PlatformEnum;
import cn.changeforyou.web.cloud.webBase.platform.PlatformStringUtils;
import cn.changeforyou.web.cloud.webBase.platform.PlatformUtils;
import cn.changeforyou.web.cloud.devUtilApi.Constant;
import cn.changeforyou.web.cloud.devUtilApi.db.sql.SqlFormatUtil;
import cn.hutool.core.codec.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequestMapping("sql")
public class SqlController {

    private SqlFormatUtil sqlFormatUtil;

    @PostMapping("format")
    @ResponseBody
    public Result<StringRespModel> sqlFormat(@Valid StringReqModel model) {
        String result = sqlFormatUtil.format(model.getValue());
        System.out.println(result);
        StringRespModel respModel = new StringRespModel();
        HttpServletRequest request =((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        PlatformEnum platform = PlatformUtils.getPlatform(request);
        result = PlatformStringUtils.getStringAdaptPlatform(platform, result);
        if(ResultWithEncoded.DEFAULT_ENCODED.equalsIgnoreCase(model.getArithmetic())){
            result = Base64.encode(result);
            respModel.setEncode(ResultWithEncoded.DEFAULT_ENCODED);
        }
        respModel.setValue(result);
        return Result.success(respModel);
    }



    @Autowired
    public void setSqlFormatUtil(SqlFormatUtil sqlFormatUtil) {
        this.sqlFormatUtil = sqlFormatUtil;
    }
}

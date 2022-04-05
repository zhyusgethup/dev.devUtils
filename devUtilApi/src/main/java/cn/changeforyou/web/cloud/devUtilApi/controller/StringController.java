package cn.changeforyou.web.cloud.devUtilApi.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("string")
public class StringController {

//    @PostMapping("sort")
//    @ResponseBody
//    public Result<StringRespModel> sort(@Valid StringReqModel model) {
//        String result = sqlFormatUtil.format(model.getValue());
//        System.out.println(result);
//        StringRespModel respModel = new StringRespModel();
//        HttpServletRequest request =((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//        PlatformEnum platform = PlatformUtils.getPlatform(request);
//        result = PlatformStringUtils.getStringAdaptPlatform(platform, result);
//        if(Constant.base64.equalsIgnoreCase(model.getEncode())){
//            result = Base64.encode(result);
//            respModel.setEncode(Constant.base64);
//        }
//        respModel.setValue(result);
//        return Result.success(respModel);
//    }

}

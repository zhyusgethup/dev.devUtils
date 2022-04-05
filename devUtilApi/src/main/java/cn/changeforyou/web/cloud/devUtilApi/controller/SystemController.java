package cn.changeforyou.web.cloud.devUtilApi.controller;

import cn.changeforyou.web.cloud.webBase.common.model.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("system")
public class SystemController {

    @GetMapping("getServerTimestamp")
    public Result<Long> getServerTimestamp(){
        return Result.success(System.currentTimeMillis());
    }


}

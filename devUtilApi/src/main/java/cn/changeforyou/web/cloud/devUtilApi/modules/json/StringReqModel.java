package cn.changeforyou.web.cloud.devUtilApi.modules.json;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class StringReqModel {
    @NotBlank
    private String value;

    private String encode;

}

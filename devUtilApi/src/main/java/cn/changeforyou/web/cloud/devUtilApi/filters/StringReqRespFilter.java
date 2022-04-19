package cn.changeforyou.web.cloud.devUtilApi.filters;

import cn.changeforyou.utils.string.StringUtils;
import cn.changeforyou.web.cloud.devUtilApi.common.model.ResultWithEncoded;
import cn.changeforyou.web.cloud.devUtilApi.http.ParameterRequestWrapper;
import cn.changeforyou.web.utils.http.ServletUtils;
import cn.changeforyou.web.utils.http.warpper.BufferedHttpResponseWrapper;
import cn.hutool.core.codec.Base64;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
public class StringReqRespFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = ((HttpServletRequest) request);
        String contentType = req.getHeader(HttpHeaders.CONTENT_TYPE);

        boolean modify = false;
        if (null == contentType) {
        } else if (contentType.startsWith(APPLICATION_JSON_VALUE)) {
            StringBuilder sb = new StringBuilder();
            req = ServletUtils.getRequestBody(req, sb);
            JSONObject reqModel = new JSONObject(sb.toString());
            if (ResultWithEncoded.DEFAULT_ENCODED.equals(reqModel.getStr("arithmetic"))) {
                String value = reqModel.getStr("value");
                byte[] decode = Base64.decode(value);
                String newValue = new String(decode, StandardCharsets.UTF_8);
                reqModel.putOpt("value", newValue);
                req = ServletUtils.wrapperHttpServletRequest(req, reqModel.toString());
                modify = true;
            } else {
                req = ServletUtils.wrapperHttpServletRequest(req, sb.toString());
            }
        } else {
            Map<String, String[]> parameterMap = req.getParameterMap();
            String[] encodes = parameterMap.get("encode");
            if (encodes != null && encodes[0].equals("base64")) {
                ParameterRequestWrapper wrapper = new ParameterRequestWrapper(req);
                modify = true;
                String[] values = parameterMap.get("value");
                if (null != values) {
                    String value = values[0];
                    if (StringUtils.isNotBlank(value)) {
                        byte[] decode = Base64.decode(value);
                        String newValue = new String(decode, StandardCharsets.UTF_8);

                        wrapper.addParameter("value", newValue);
                        wrapper.addParameter("encode", "base64");
                    }
                }
                req = wrapper;
            } else {
            }
        }

        BufferedHttpResponseWrapper responseWrapper = new BufferedHttpResponseWrapper((HttpServletResponse) response);
        chain.doFilter(req, responseWrapper);

        byte[] buffer = responseWrapper.getBuffer();
        if (modify) {
            String res = new String(buffer);
            ResultWithEncoded result = JSONUtil.toBean(res, ResultWithEncoded.class);
            if (result.isEncode() || result.getCode() != 0) {
                response.getOutputStream().write(buffer);
                return;
            }
            String arithmetic = result.getArithmetic();
            if (ResultWithEncoded.DEFAULT_ENCODED.equals(arithmetic)) {
                String data = result.getData();
                String encodedData = Base64.encode(data);
                result.setData(encodedData);
                result.setEncode(true);

                String responseString = JSONUtil.toJsonStr(result);
                response.getOutputStream().write(responseString.getBytes(StandardCharsets.UTF_8));
            }
        } else {
            response.getOutputStream().write(buffer);
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }

    private static String toJson(Object object) {
        return JSONUtil.toJsonStr(object);
    }

}

package cn.changeforyou.web.cloud.devUtilApi.filters;

import cn.changeforyou.web.utils.http.ServletUtils;
import cn.changeforyou.web.utils.http.warpper.BufferedHttpResponseWrapper;
import cn.hutool.json.JSONUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * json请求时，如果body包含header，则剥除 顺便 输出对应url 请求体，响应体，耗时
 *
 * @author zhyu
 */
public class LogFilter extends OncePerRequestFilter {

    Logger log = LoggerFactory.getLogger("reqResp");

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        long requestTime = System.currentTimeMillis();
        String uri = request.getRequestURI();
        String contextPath = request.getContextPath();
        String url = uri.substring(contextPath.length());

        String requestBody = "";
        String requestContentType = request.getHeader(HttpHeaders.CONTENT_TYPE);

        if (requestContentType != null) {
//			xml json
            if ((requestContentType.startsWith(MediaType.APPLICATION_JSON_VALUE) || requestContentType.startsWith(MediaType.APPLICATION_XML_VALUE)) && request.getMethod()
                    .equalsIgnoreCase("POST")) {
                StringBuilder sb = new StringBuilder();
                request = ServletUtils.getRequestBody(request, sb);
                requestBody = sb.toString();
//		    普通表单提交
            } else if (requestContentType.startsWith(MediaType.APPLICATION_FORM_URLENCODED_VALUE)) {
                requestBody = toJson(request.getParameterMap());
//			文件表单提交
            } else if (requestContentType.startsWith(MediaType.MULTIPART_FORM_DATA_VALUE)) {
                requestBody = getFormParam(request);
            } else {
                requestBody = toJson(request.getParameterMap());
            }
        } else if (request.getMethod().equals(HttpMethod.GET.name())) {
            requestBody = toJson(request.getParameterMap());
        }

        BufferedHttpResponseWrapper responseWrapper = new BufferedHttpResponseWrapper(response);
        log.info("URL: {}, requestBody: {}", url, requestBody);
        filterChain.doFilter(request, responseWrapper);

        long costTime = System.currentTimeMillis() - requestTime;
        String responseBody = "";
//		暂定只有json 输出响应体
        String contentType = responseWrapper.getContentType();
        if (contentType != null && contentType.startsWith(MediaType.APPLICATION_JSON_VALUE)) {
            responseBody = new String(responseWrapper.getBuffer(), StandardCharsets.UTF_8);
        }

        StringBuilder sb = new StringBuilder();
        sb.append("URL:").append(url).append(", total time:").append(costTime).append(" ms, ");
        sb.append(", responseBody:").append(responseBody);
        if (responseWrapper.getStatus() >= 200 && responseWrapper.getStatus() < 1000) {
            log.info(sb.toString());
        } else {
            log.error(sb.toString());
        }
        response.getOutputStream().write(responseWrapper.getBuffer());
    }


    private String getFormParam(HttpServletRequest request) {
        MultipartResolver resolver = new StandardServletMultipartResolver();
        MultipartHttpServletRequest mRequest = resolver.resolveMultipart(request);

        Map<String, Object> param = new HashMap<>();
        Map<String, String[]> parameterMap = mRequest.getParameterMap();
        if (!parameterMap.isEmpty()) {
            param.putAll(parameterMap);
        }
        Map<String, MultipartFile> fileMap = mRequest.getFileMap();
        if (!fileMap.isEmpty()) {
            for (Map.Entry<String, MultipartFile> fileEntry : fileMap.entrySet()) {
                MultipartFile file = fileEntry.getValue();
                param.put(fileEntry.getKey(), file.getOriginalFilename() + "(" + file.getSize() + " byte)");
            }
        }
        return toJson(param);
    }

    @Override
    public void afterPropertiesSet() throws ServletException {
        super.afterPropertiesSet();
    }

    private static String toJson(Object object) {
        return JSONUtil.toJsonStr(object);
    }

}

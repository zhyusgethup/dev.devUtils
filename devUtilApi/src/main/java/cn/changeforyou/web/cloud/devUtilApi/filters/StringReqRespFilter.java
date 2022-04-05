package cn.changeforyou.web.cloud.devUtilApi.filters;

import cn.changeforyou.web.utils.http.ServletUtils;
import cn.changeforyou.web.utils.http.warpper.BufferedHttpResponseWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static sun.plugin2.util.PojoUtil.toJson;

@Slf4j
@WebFilter(filterName = "stringReqRespFilter", urlPatterns = {"/string/*", "/json/*"})
public class StringReqRespFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = ((HttpServletRequest) request);
        String requestBody = "";
        String requestContentType = req.getHeader(HttpHeaders.CONTENT_TYPE);
        String url = req.getRequestURL().toString();
        if (requestContentType != null) {
//			xml json
            if ((requestContentType.startsWith(MediaType.APPLICATION_JSON_VALUE) || requestContentType.startsWith(MediaType.APPLICATION_XML_VALUE)) && req.getMethod()
                    .equalsIgnoreCase("POST")) {
                StringBuilder sb = new StringBuilder();
                req = ServletUtils.getRequestBody(req, sb);
                requestBody = sb.toString();
//		    普通表单提交
            } else if (requestContentType.startsWith(MediaType.APPLICATION_FORM_URLENCODED_VALUE)) {
                requestBody = toJson(req.getParameterMap());
//			文件表单提交
            } else if (requestContentType.startsWith(MediaType.MULTIPART_FORM_DATA_VALUE)) {
                requestBody = getFormParam(req);
            } else {
                requestBody = toJson(req.getParameterMap());
            }
        } else if (req.getMethod().equals(HttpMethod.GET.name())) {
            requestBody = toJson(req.getParameterMap());
        }

        BufferedHttpResponseWrapper responseWrapper = new BufferedHttpResponseWrapper((HttpServletResponse) response);
        log.debug("URL: {}, requestBody: {}", url, requestBody);
        chain.doFilter(req, responseWrapper);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
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

}

package cn.changeforyou.web.cloud.devUtilApi.filters;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description * @Author wly * @Date 2018/6/14 14:20
 */
@Configuration
public class FilterConfig {
    @Bean
    public FilterRegistrationBean buildLogFilter() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setOrder(2);
        filterRegistrationBean.setFilter(new LogFilter());
        filterRegistrationBean.setName("logFilter");
        filterRegistrationBean.addUrlPatterns("/*");
        return filterRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean buildStringReqRespFilter() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setOrder(1);
        filterRegistrationBean.setFilter(new StringReqRespFilter());
        filterRegistrationBean.setName("stringReqRespFilter");
        filterRegistrationBean.addUrlPatterns("/string/*", "/json/*");
        return filterRegistrationBean;
    }


}
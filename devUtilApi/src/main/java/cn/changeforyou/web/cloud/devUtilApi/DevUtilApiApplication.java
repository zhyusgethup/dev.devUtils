package cn.changeforyou.web.cloud.devUtilApi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.Cacheable;

@ServletComponentScan
@SpringBootApplication(scanBasePackages={"cn.changeforyou.web"})
@Cacheable
@MapperScan({"cn.changeforyou.web.cloud.devUtilApi.modules.*.dao"})
public class DevUtilApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(DevUtilApiApplication.class, args);
    }
}

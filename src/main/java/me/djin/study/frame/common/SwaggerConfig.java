package me.djin.study.frame.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import me.djin.study.swagger.SwaggerDocket;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class SwaggerConfig {
 
    /**
     * 创建接口文档
     * @return
     */
    @Bean
    public Docket createRestApiDocument() {
    	SwaggerDocket tool = new SwaggerDocket();
    	return tool.createDocket("接口文档", "1.0");
    }
}

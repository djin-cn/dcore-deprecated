/**
 * 
 */
package me.djin.study.swagger;

import io.swagger.annotations.Api;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @author djin
 * swagger配置工具类类，简化swagger使用
 */
public class SwaggerDocket {
	private ApiInfo apiInfo(String title, String description, String termsOfServiceUrl, String version) {
        return new ApiInfoBuilder()
                .title(title)
                .description(description)
                .termsOfServiceUrl(termsOfServiceUrl)
                .version(version)
                .build();
	}
	public Docket createDocket(String title, String version) {
		return createDocket(title, null, null, version);
	}
	public Docket createDocket(String title, String description, String version) {
		return createDocket(title, description, null, version);
	}
	public Docket createDocket(String title, String description, String termsOfServiceUrl, String version) {
		return new Docket(DocumentationType.SWAGGER_2)
				.useDefaultResponseMessages(false)
                .apiInfo(apiInfo(title, description, termsOfServiceUrl, version))
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .paths(PathSelectors.any())
                .build();
	}
}

/**
 * 
 */
package me.djin.dcore.frame.common;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author djin 跨域访问设置
 */
@Configurable
public class CORSConfiguration {
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				// 添加映射路径
				registry.addMapping("/**")
						// 放行哪些原始域
						.allowedOrigins("*")
						// 是否发送Cookie信息
						.allowCredentials(true)
						// 放行哪些原始域(请求方式)
						.allowedMethods("GET", "POST", "PUT", "DELETE")
						// 放行哪些原始域(头部信息)
						.allowedHeaders("*")
						// 暴露哪些头部信息（因为跨域访问默认不能获取全部头部信息）
						.exposedHeaders("TOKEN");
			}
		};
	}
}

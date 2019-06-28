/**
 * 
 */
package me.djin.dcore.frame.common;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author djin 跨域访问设置
 */
@Component
public class CorsConfiguration {
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
						// 放行哪些请求方式
						.allowedMethods("*")
						// 放行哪些头部信息
						.allowedHeaders("*");
			}
		};
	}
}

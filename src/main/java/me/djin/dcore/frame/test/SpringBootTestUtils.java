/**
 * 
 */
package me.djin.dcore.frame.test;

import static org.hamcrest.Matchers.*;

import org.junit.Assert;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.alibaba.fastjson.JSONObject;

import me.djin.dcore.frame.model.Response;


/**
 * @author djin
 * Junit测试工具类
 */
public class SpringBootTestUtils {
	/**
	 * 根据java对象模拟参数
	 * @param clazz
	 * @return
	 */
	public static Object mockParameter(Class<?> clazz) {
		/**
		 * TODO
		 * -获取所有属性
		 * -遍历获取属性类型
		 * -遍历获取属性注解
		 * -根据属性类型和属性注解生成数据
		 * -自定义属性值(有些属性值有特殊范围, 不便于自动生成; 如枚举类型苏醒/传递依赖属性等)
		 * -请求接口测试
		 * -断言接口结果
		 * -遍历结束
		 */
		throw new UnsupportedOperationException();
	}
	/**
	 * 以post方式调用接口
	 * @param template
	 * @param url 接口地址
	 * @param params 接口参数
	 * @param clazz 返回类型
	 * @return
	 */
	public static <T> Response<T> post(TestRestTemplate template, String url, Object params, Class<T> clazz) {
		ResponseEntity<JSONObject> response = template.postForEntity(url, params, JSONObject.class);
		Assert.assertThat(response.getStatusCode(), is(HttpStatus.OK));
		JSONObject json = response.getBody();
		T data = null;
		try {
			data = json.getObject("data", clazz);
		}catch (Exception e) {
			data = null;
		}
		
		Response<T> result = new Response<T>(json.getString("status"), data);
		return result;
	}
}
package me.djin.dcore.util.spring;

import java.util.HashMap;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * spring上下文环境获取工具 类
 * @author djin
 *
 */
@Component
public class SpringContextUtil implements ApplicationContextAware {
	/**
	 * Spring应用上下文环境
	 */
	private static ApplicationContext applicationContext;
	private static final HashMap<String,Object> BEANS_MAP = new HashMap<String,Object>();

	/**
	 * 实现ApplicationContextAware接口的回调方法，设置上下文环境
	 * 
	 * @param applicationContext
	 */
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) {
		SpringContextUtil.applicationContext = applicationContext;
	}

	/**
	 * @return ApplicationContext
	 */
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	/**
	 * 获取对象 这里重写了bean方法，起主要作用
	 * 
	 * @param name
	 * @return Object 一个以所给名字注册的bean的实例
	 * @throws BeansException
	 */
	public static Object getBean(String name) throws BeansException {
		return applicationContext.getBean(name);
	}

	/**
	 * 获取指定类的实例
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(Class<T> clazz) {
		String name = clazz.getSimpleName();
		name = StringUtils.uncapitalize(name);
		return (T)getBean(name);
	}
}
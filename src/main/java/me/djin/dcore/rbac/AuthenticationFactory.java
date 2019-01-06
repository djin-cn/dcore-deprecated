package me.djin.dcore.rbac;

import me.djin.dcore.core.BeanFactory;

/**
 * 认证工厂接口
 * @author djin
 *
 */
public interface AuthenticationFactory extends BeanFactory {
	/**
	 * 创建Authentication实例
	 * @return
	 */
	Authentication createInstance();
}

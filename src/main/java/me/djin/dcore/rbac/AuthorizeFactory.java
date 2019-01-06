package me.djin.dcore.rbac;

import me.djin.dcore.core.BeanFactory;

/**
 * 授权工厂
 * @author djin
 *
 */
public interface AuthorizeFactory extends BeanFactory {
	/**
	 * 创建授权类
	 * @return
	 */
	Authorize createInstance();
}

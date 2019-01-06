package me.djin.dcore.rbac;

import org.apache.shiro.web.servlet.AbstractShiroFilter;

import me.djin.dcore.core.BeanFactory;

/**
 * @author djin
 * AbstractShiroFilter 工厂
 */
public interface AbstractShiroFilterFactory extends BeanFactory {
	/**
	 * 创建AbstractShiroFilter实例
	 * @return
	 */
	AbstractShiroFilter createInstance();
}

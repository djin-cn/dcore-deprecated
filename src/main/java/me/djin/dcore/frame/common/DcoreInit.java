package me.djin.dcore.frame.common;

import me.djin.dcore.core.FactoryContainer;
import me.djin.dcore.frame.rbac.DefaultAuthenticationFactoryImpl;
import me.djin.dcore.frame.rbac.DefaultAuthorizeFactoryImpl;
import me.djin.dcore.rbac.AuthenticationFactory;
import me.djin.dcore.rbac.AuthorizeFactory;

/**
 * 框架初始化
 * @author djin
 *
 */
public class DcoreInit {
	/**
	 * 初始化方法，必须在所有的Bean初始化之前执行
	 * @return
	 */
	public static void init() {
		FactoryContainer.addBeanFactory(AuthorizeFactory.class, new DefaultAuthorizeFactoryImpl());
		FactoryContainer.addBeanFactory(AuthenticationFactory.class, new DefaultAuthenticationFactoryImpl());
	}
}

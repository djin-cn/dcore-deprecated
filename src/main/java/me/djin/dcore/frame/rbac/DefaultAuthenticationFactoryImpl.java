package me.djin.dcore.frame.rbac;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import me.djin.dcore.core.FactoryContainer;
import me.djin.dcore.rbac.Authentication;
import me.djin.dcore.rbac.AuthenticationFactory;
import me.djin.dcore.rbac.AuthorizeDemo;

/**
 * 身份验证工厂，创建身份验证实例
 * @author djin
 *
 */
public class DefaultAuthenticationFactoryImpl implements AuthenticationFactory {
	public DefaultAuthenticationFactoryImpl() {
	}
	
	@Override
	public Authentication createInstance() {
		return new AuthorizeDemo();
	}

}

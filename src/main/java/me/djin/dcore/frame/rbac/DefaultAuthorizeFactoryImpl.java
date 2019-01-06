package me.djin.dcore.frame.rbac;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import me.djin.dcore.core.FactoryContainer;
import me.djin.dcore.rbac.Authorize;
import me.djin.dcore.rbac.AuthorizeDemo;
import me.djin.dcore.rbac.AuthorizeFactory;

/**
 * 访问授权工厂，创建访问授权实例
 * @author djin
 *
 */
public class DefaultAuthorizeFactoryImpl implements AuthorizeFactory {
	public DefaultAuthorizeFactoryImpl() {
	}
	
	@Override
	public Authorize createInstance() {
		return new AuthorizeDemo();
	}

}

package me.djin.dcore.frame.rbac;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.Filter;

import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.mgt.DefaultWebSubjectFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import me.djin.dcore.core.FactoryContainer;
import me.djin.dcore.frame.common.DcoreInit;
import me.djin.dcore.rbac.Authorize;
import me.djin.dcore.rbac.AuthorizeFactory;
import me.djin.dcore.rbac.MyShiroRealm;
import me.djin.dcore.rbac.Permission;
import me.djin.dcore.rbac.ShiroFilterChainBuilder;
import me.djin.dcore.rbac.StatelessControlFilter;
import me.djin.dcore.rbac.StatelessDefaultSubjectFactory;

/**
 * 
 * @author djin
 *
 */
@Configuration
public class RbacConfig {
	@PostConstruct
	public void initApplication() {
		DcoreInit.init();
	}
	
	@Bean
	public ShiroFilterFactoryBean shiroFilterFactoryBean(org.apache.shiro.mgt.SecurityManager securityManager) {
		Authorize authorize = FactoryContainer.getBeanFactory(AuthorizeFactory.class).createInstance();
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		shiroFilterFactoryBean.setSecurityManager(securityManager);

		Map<String, Filter> filtersMap = new LinkedHashMap<String, Filter>();
		filtersMap.put(StatelessControlFilter.FILTER_KEY, new StatelessControlFilter());
		shiroFilterFactoryBean.setFilters(filtersMap);

		Collection<Permission> list = authorize.getPermissions();
		ShiroFilterChainBuilder filterBuilder = new ShiroFilterChainBuilder(list);
		Map<String, String> filterChains = filterBuilder.getFilterChains();
		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChains);

		// 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
		// shiroFilterFactoryBean.setLoginUrl("/test/login");
		// 登录成功后要跳转的链接
		// shiroFilterFactoryBean.setSuccessUrl("/index");
		// 未授权界面;
		// shiroFilterFactoryBean.setUnauthorizedUrl("/403");
		return shiroFilterFactoryBean;
	}

	/**
	 * Shiro安全管理器。 主要用于身份认证管理、缓存管理、COOKIE管理
	 * 
	 * @return
	 */
	@Bean
	public org.apache.shiro.mgt.SecurityManager securityManager() {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		securityManager.setRealm(myShiroRealm());
		securityManager.setSubjectFactory(subjectFactory());
		securityManager.setSessionManager(sessionManager());
		disabledSessionOfSubjectDAO(securityManager);
		return securityManager;
	}

	private MyShiroRealm myShiroRealm() {
		MyShiroRealm myShiroRealm = new MyShiroRealm();
		return myShiroRealm;
	}

	/**
	 * 注入无状态SubjectFactory
	 * 
	 * @return
	 */
	private DefaultWebSubjectFactory subjectFactory() {
		StatelessDefaultSubjectFactory subjectFactory = new StatelessDefaultSubjectFactory();
		return subjectFactory;
	}

	/**
	 * Session管理器。 这里禁用掉Session管理器
	 * 
	 * @return
	 */
	private DefaultSessionManager sessionManager() {
		DefaultSessionManager sessionManager = new DefaultSessionManager();
		sessionManager.setSessionValidationSchedulerEnabled(false);
		return sessionManager;
	}

	/**
	 * 禁用SESSION作为令牌存储策略
	 * 
	 * @param securityManager
	 */
	private void disabledSessionOfSubjectDAO(DefaultWebSecurityManager securityManager) {
		((DefaultSessionStorageEvaluator) ((DefaultSubjectDAO) securityManager.getSubjectDAO())
				.getSessionStorageEvaluator()).setSessionStorageEnabled(false);
	}
}
package me.djin.dcore.rbac;

import java.util.Collection;
import java.util.Map;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.util.CollectionUtils;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.servlet.AbstractShiroFilter;

import me.djin.dcore.core.FactoryContainer;
import me.djin.dcore.util.spring.SpringContextUtil;

/**
 * 刷新权限。
 * 
 * 如果是单应用，可以在修改权限时调用修改当前权限；
 * 
 * 如果是分布式应用，可以通过定时任务的方式定时更新权限(一般权限更新频率不会太高)。
 * 
 * <pre>
 * private RefreshAuthorize refreshAuthorize = new RefreshAuthorize();
 * refreshAuthorize.reloadPermissions();
 * </pre>
 * 
 * @author djin
 *
 */
public class RefreshAuthorize {
	/**
	 * 刷新权限
	 * 
	 * @throws Exception
	 */
	public synchronized void reloadPermissions() throws Exception {
		ShiroFilterFactoryBean shiroFilterFactory = SpringContextUtil.getBean(ShiroFilterFactoryBean.class);
		Authorize authorize = FactoryContainer.getBeanFactory(AuthorizeFactory.class).createInstance();
		AbstractShiroFilter shiroFilter = (AbstractShiroFilter)shiroFilterFactory.getObject();
		PathMatchingFilterChainResolver resolver = (PathMatchingFilterChainResolver) shiroFilter
				.getFilterChainResolver();
		DefaultFilterChainManager manager = (DefaultFilterChainManager) resolver.getFilterChainManager();
		StatelessControlFilter filter = (StatelessControlFilter)manager.getFilter(StatelessControlFilter.FILTER_KEY);
		manager.getFilterChains().clear();
		shiroFilterFactory.getFilterChainDefinitionMap().clear();
		filter.clearAuthorizes();

		Collection<Permission> permissions = authorize.getPermissions();
		
		ShiroFilterChainBuilder filterBuilder = new ShiroFilterChainBuilder(permissions);
		Map<String, String> filterChains = filterBuilder.getFilterChains();

		
		if (!CollectionUtils.isEmpty(filterChains)) {
			shiroFilterFactory.setFilterChainDefinitionMap(filterChains);
			filterChains.forEach((key, value) -> {
				manager.createChain(key, value);
			});
		}
	}
}
package me.djin.dcore.rbac;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.shiro.util.CollectionUtils;

/**
 * Shiro过滤链创建
 * 
 * @author djin
 *
 */
public class ShiroFilterChainBuilder {
	private Collection<Permission> permissions = null;

	public ShiroFilterChainBuilder() {
	}

	public ShiroFilterChainBuilder(Collection<Permission> permissions) {
		this.permissions = permissions;
	}

	/**
	 * 获取默认的过滤链.
	 * 
	 * 默认以下内容不需要权限校验:
	 * 
	 * 1:swagger相关的接口和页面
	 * 
	 * 2:/common目录下的接口
	 * 
	 * @return
	 */
	private Map<String, String> getDefault() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("/swagger-ui.html", "anon");
		map.put("/swagger-resources/**", "anon");
		map.put("/v2/api-docs", "anon");
		map.put("/webjars/springfox-swagger-ui/**", "anon");
		map.put("/common/**", "anon");
		return map;
	}

	/**
	 * 获取所有过滤链，按顺序判断，将不需要权限控制的放到前面. 需要权限控制的放到后面 shiro默认自带了以下过滤器， 可直接使用.
	 * anon:匿名过滤器;org.apache.shiro.web.filter.authc.AnonymousFilter
	 * 
	 * authc:常用，需要做对应的表单登录验证否则不能通过，主要参数：username,password,rememberMe,loginUrl,successUrl;org.apache.shiro.web.filter.authc.FormAuthenticationFilter
	 * 
	 * authcBasic:基本http验证过滤;org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter
	 * 
	 * logout:常用，登录退出过滤器;org.apache.shiro.web.filter.authc.LogoutFilter
	 * 
	 * noSessionCreation:不创建session拦截器;org.apache.shiro.web.filter.session.NoSessionCreationFilter
	 * 
	 * perms:权限过滤器;org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter
	 * 
	 * port:端口拦截器，主要属性：port：可以通过的端口,其他端口将自动跳转到此端口访问(带请求参数);org.apache.shiro.web.filter.authz.PortFilter
	 * 
	 * rest:rest风格拦截器，自动根据请求方法构建权限字符串（GET=read,
	 * POST=create,PUT=update,DELETE=delete,HEAD=read,TRACE=read,OPTIONS=read,
	 * MKCOL=create）构建权限字符串;org.apache.shiro.web.filter.authz.HttpMethodPermissionFilter
	 * 
	 * ssl:常用，SSL拦截器，只有请求协议是https才能通过；否则自动跳转会https端口（443）；其他和port拦截器一样;org.apache.shiro.web.filter.authz.SslFilter
	 * 
	 * @return
	 */
	public Map<String, String> getFilterChains() {
		Map<String, String> map = getDefault();
//		map.put("/**", "anon");
		if (!CollectionUtils.isEmpty(permissions)) {
			permissions.forEach((permission) -> {
//				map.put(permission.getPath(), String.format("stateless, perms[%s]", permission.getCode()));
				map.put(permission.getPath(), String.format(StatelessControlFilter.FILTER_KEY+"[%s]", permission.getCode()));
			});
		}
		map.put("/**", StatelessControlFilter.FILTER_KEY);
		return map;
	}
}
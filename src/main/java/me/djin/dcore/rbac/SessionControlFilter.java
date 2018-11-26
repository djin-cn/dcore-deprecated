package me.djin.dcore.rbac;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import me.djin.dcore.frame.model.Response;

/**
 * 
 * @author djin
 *
 */
public class SessionControlFilter extends FormAuthenticationFilter {

	/**
	 * 表示当访问拒绝时是否已经处理了；如果返回true表示需要继续处理；如果返回false表示该拦截器实例已经处理了，将直接返回即可。
	 * onAccessDenied是否执行取决于isAccessAllowed的值，如果返回true则onAccessDenied不会执行；如果返回false，执行onAccessDenied
	 * 如果onAccessDenied也返回false，则直接返回，不会进入请求的方法（只有isAccessAllowed和onAccessDenied的情况下）
	 */
	@Override
	public boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		Response<String> out = new Response<>(3);
		boolean flg = false;
		if (isLoginRequest(request, response)) {
			flg = executeLogin(request, response);
		}
		response.getWriter().write(out.toString());
		return flg;
	}
    
    /**
     * 获取登录的Subject
     */
	@Override
    protected Subject getSubject(ServletRequest request, ServletResponse response) {
        return SecurityUtils.getSubject();
    }
}
package me.djin.dcore.rbac;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

import me.djin.dcore.frame.model.Response;

/**
 * 
 * @author djin
 *
 */
public class StatelessControlFilter extends AccessControlFilter {
	public static final String FILTER_KEY = "stateless";
	/*@Override
	public boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue)
			throws Exception {
		request.setAttribute(DefaultSubjectContext.SESSION_CREATION_ENABLED, Boolean.FALSE);
		return super.onPreHandle(request, response, mappedValue);
	}*/

	/**
	 * 表示当访问拒绝时是否已经处理了；如果返回true表示需要继续处理；如果返回false表示该拦截器实例已经处理了，将直接返回即可。
	 * onAccessDenied是否执行取决于isAccessAllowed的值，如果返回true则onAccessDenied不会执行；如果返回false，执行onAccessDenied
	 * 如果onAccessDenied也返回false，则直接返回，不会进入请求的方法（只有isAccessAllowed和onAccessDenied的情况下）
	 */
	@Override
	public boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().write(Response.error(3).toString());
		return false;
	}

	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
			throws Exception {
		return isAccessAllowed(request, response) && isPermissive(request, response, mappedValue);
		/*return isAccessAllowed(request, response) ||
                (!isLoginRequest(request, response) && isPermissive(request, response, mappedValue));*/
	}
	
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response) {
		Subject subject = getSubject(request, response);
        return subject.isAuthenticated();
	}
	
	/**
	 * 判断权限验证是否通过
	 * @param request
	 * @param response
	 * @param mappedValue 访问资源所需要的权限
	 * @return
	 */
	protected boolean isPermissive(ServletRequest request, ServletResponse response, Object mappedValue) {
		if(mappedValue == null) {
        	return false;
        }
        Subject subject = getSubject(request, response);
    	String[] perms = (String[]) mappedValue;

        boolean isPermitted = true;
        if (perms != null && perms.length > 0) {
            if (perms.length == 1) {
                if (!subject.isPermitted(perms[0])) {
                    isPermitted = false;
                }
            } else {
                if (!subject.isPermittedAll(perms)) {
                    isPermitted = false;
                }
            }
        }

        return isPermitted;
    }
	
	/**
	 * 登录
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        AuthenticationToken token = createToken(request, response);
        if (token == null) {
            String msg = "createToken method implementation returned null. A valid non-null AuthenticationToken " +
                    "must be created in order to execute a login attempt.";
            throw new IllegalStateException(msg);
        }
        try {
            Subject subject = getSubject(request, response);
            subject.login(token);
            return true;
        } catch (AuthenticationException e) {
            return false;
        }
    }

	/**
	 * 创建认证令牌
	 * @param request
	 * @param response
	 * @return
	 */
	private AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
		String username = getUsername(request);
        String password = getPassword(request);
        password = DigestUtils.sha256Hex(password);
		return new UsernamePasswordToken(username, password, false, request.getRemoteHost());
	}
	
	/**
	 * 获取用户名
	 * @param request
	 * @return
	 */
	protected String getUsername(ServletRequest request) {
		FormAuthenticationFilter formFilter = new FormAuthenticationFilter();
        return WebUtils.getCleanParam(request, formFilter.getUsernameParam());
    }

	/**
	 * 获取密码
	 * @param request
	 * @return
	 */
    protected String getPassword(ServletRequest request) {
    	FormAuthenticationFilter formFilter = new FormAuthenticationFilter();
        return WebUtils.getCleanParam(request, formFilter.getPasswordParam());
    }
    
    public void clearAuthorizes() {
    	this.appliedPaths.clear();
    }
    
    /**
     * 获取登录的Subject
     */
    /*protected Subject getSubject(ServletRequest request, ServletResponse response) {
    	String token = ((HttpServletRequest)request).getHeader("token");
//    	Ciphe
    	Subject subject = super.getSubject(request, response);
//    	subject.isAuthenticated()
//    	WebDelegatingSubject
//    	subject
    	return null;
//        return SecurityUtils.getSubject();
    }*/
}
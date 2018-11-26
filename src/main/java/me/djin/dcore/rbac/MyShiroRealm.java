package me.djin.dcore.rbac;

import java.util.Collection;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 
 * @author djin
 *
 */
public class MyShiroRealm extends AuthorizingRealm {
	@Autowired
	private IAuthorize authorize;
	@Autowired
	private IAuthentication authentication;
	
	public MyShiroRealm() {
		super(new MyCredentialsMatcher());
	}

	/**
	 * 用户权限验证
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		AuthenticationUser user = (AuthenticationUser) principals.getPrimaryPrincipal();
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
//		Collection<String> permissions = authorize.getPermissionsByUserid(user.getUserid());
		Collection<String> permissions = authorize.getPermissionsByUserid(user);
		authorizationInfo.addStringPermissions(permissions);
		return authorizationInfo;
	}

	/**
	 * 主要是用来进行身份认证。即验证账号和密码是否正确。
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		// 获取用户的输入的账号.
		String username = (String) token.getPrincipal();

		AuthenticationUser user = authentication.getUserByUsername(username);
		if (user == null) {
			return null;
		}
		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getPassword()
				,ByteSource.Util.bytes(user.getSalt()), getName());
		return info;
	}
}
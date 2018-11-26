/**
 * 
 */
package me.djin.dcore.rbac;

/**
 * @author djin 认证接口
 */
public interface IAuthentication {
	/**
	 * 根据用户名获取用户认证信息
	 * 
	 * @param username
	 *            用户名
	 * @return
	 */
	AuthenticationUser getUserByUsername(String username);
}

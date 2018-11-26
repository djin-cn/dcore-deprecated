/**
 * 
 */
package me.djin.dcore.rbac;

/**
 * @author djin
 * 认证用户信息
 */
public class AuthenticationUser extends CurrentUser {
	private String password;
	private String salt;
	/**
	 * 密码
	 * @return
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * 密码
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * 加密用的盐值
	 * @return
	 */
	public String getSalt() {
		return salt;
	}
	/**
	 * 加密用的盐值
	 * @param salt
	 */
	public void setSalt(String salt) {
		this.salt = salt;
	}
}
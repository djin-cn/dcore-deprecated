/**
 * 
 */
package me.djin.dcore.rbac;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.apache.shiro.util.SimpleByteSource;

/**
 * @author djin
 * 自定义凭证匹配器。
 * Shiro默认使用SimpleCredentialsMatcher进行凭证匹配，
 * 匹配方式是将数据库的密码与用户输入的密码进行对比，如果相同则匹配成功，否则匹配失败。
 * 但是数据库密码的存储一般是加密之后的，用户的密码是加密之前的，
 * 即数据库存的是密文，用户输入的是明文；默认情况下肯定是不可能相等的；
 * 自定义凭证匹配器先根据从数据库获取的盐值对用户输入的明文密码加密，
 * 然后再和数据库密码进行对比，如果相同则匹配成功，否则匹配失败
 */
public class MyCredentialsMatcher extends SimpleCredentialsMatcher {
	/**
	 * 获取盐值
	 * @param info
	 * @return
	 */
	protected String getSalt(AuthenticationInfo info) {
		SimpleAuthenticationInfo authenticationInfo = (SimpleAuthenticationInfo)info;
		SimpleByteSource byteSource = (SimpleByteSource)authenticationInfo.getCredentialsSalt();
		return new String(byteSource.getBytes());
	}
	
	@Override
	public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
		String salt = getSalt(info);
		String pwd = new String((char[])getCredentials(token));
		String tokenCredential = DigestUtils.sha512Hex(pwd+salt);
        Object accountCredentials = getCredentials(info);
        return equals(tokenCredential, accountCredentials);
    }
}

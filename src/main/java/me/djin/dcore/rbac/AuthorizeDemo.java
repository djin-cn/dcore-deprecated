/**
 * 
 */
package me.djin.dcore.rbac;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @author djin
 *
 */
public class AuthorizeDemo implements Authorize, Authentication {

	/**
	 * @see me.djin.study.rbac.Authentication#getUserByUsername(java.lang.String)
	 */
	@Override
	public AuthenticationUser getUserByUsername(String username) {
		AuthenticationUser user = new AuthenticationUser();
		user.setPassword("875afc94bc041e7096b9ece017c5dff66867e463f8b1af4530289227fc1ab920071a8970a066887c33c095c1513666e9764fd5ebb62df28aa6674d87c80842f0");
		user.setSalt("");
		user.setUserid(1L);
		user.setSystemRole(1);
		user.setUsername("admin");
		return user;
	}

	/**
	 * @see me.djin.study.rbac.Authorize#getPermissionsByUserId(java.lang.Object)
	 */
	@Override
	public Collection<String> getPermissionsByUserid(CurrentUser user) {
		ArrayList<String> list = new ArrayList<>();
		list.add(user.getSystemRole()+"_1");
		return list;
	}

	/**
	 * @see me.djin.study.rbac.Authorize#getPermissions()
	 */
	@Override
	public Collection<Permission> getPermissions() {
		ArrayList<Permission> list = new ArrayList<>();
		
		Permission permission1 = new Permission();
		permission1.setCode("1_1");
		permission1.setPath("/admin/**");
		

		Permission permission2 = new Permission();
		permission2.setCode("1_2");
		permission2.setPath("/test/**");
		
		list.add(permission1);
		list.add(permission2);
		
		return list;
	}

	public static void main(String[] args) {
		String txt = DigestUtils.sha256Hex("123456");
		System.out.println(txt);
	}
}

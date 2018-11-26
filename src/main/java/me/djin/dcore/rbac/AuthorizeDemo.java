/**
 * 
 */
package me.djin.dcore.rbac;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

/**
 * @author djin
 *
 */
//@Service
public class AuthorizeDemo implements IAuthorize {

	/* (non-Javadoc)
	 * @see me.djin.study.rbac.IAuthorize#getUserByUsername(java.lang.String)
	 */
	/*@Override
	public AuthenticationUser getUserByUsername(String username) {
		// TODO Auto-generated method stub
		AuthenticationUser user = new AuthenticationUser();
		user.setPassword("8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92");
		user.setSalt("sdfgwr");
		user.setUserid(1);
		user.setUsername("admin");
		return user;
	}*/

	/* (non-Javadoc)
	 * @see me.djin.study.rbac.IAuthorize#getPermissionsByUserId(java.lang.Object)
	 */
	@Override
	public Collection<String> getPermissionsByUserid(CurrentUser user) {
		// TODO Auto-generated method stub
		ArrayList<String> list = new ArrayList<>();
		list.add(user.getSystemRole()+"_2");
		return list;
	}

	/* (non-Javadoc)
	 * @see me.djin.study.rbac.IAuthorize#getPermissions()
	 */
	@Override
	public Collection<Permission> getPermissions() {
		// TODO Auto-generated method stub
		ArrayList<Permission> list = new ArrayList<>();
		
		Permission permission1 = new Permission();
		permission1.setCode("1_1");
		permission1.setPath("/test/index");
		

		Permission permission2 = new Permission();
		permission2.setCode("1_2");
		permission2.setPath("/test/*");
		
		list.add(permission1);
		list.add(permission2);
		
		return list;
	}

	public static void main(String[] args) {
		String txt = DigestUtils.sha256Hex("123456");
		System.out.println(txt);
	}
}

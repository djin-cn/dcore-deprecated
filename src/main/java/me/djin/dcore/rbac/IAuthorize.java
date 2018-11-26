/**
 * 
 */
package me.djin.dcore.rbac;

import java.util.Collection;

/**
 * @author djin
 * 授权接口
 */
public interface IAuthorize {	
	/**
	 * 根据用户ID获取权限信息
	 * 
	 * 权限组成模式：系统角色ID_用户权限ID
	 * @param uid 用户ID
	 * @return
	 */
	Collection<String> getPermissionsByUserid(CurrentUser user);
	
	/**
	 * 获取所有权限，要求权限列表是有序的，否则可能导致权限不能正确匹配<br/>
	 * 建议按照通配符位置排序，通配符位置靠前的排前面，通配符位置靠后的排后面，没有通配符的排最后<br/>
	 * 权限不是经常会变化的数据，在必要的时候可以设置缓存进行优化,在分布式开发部署的时候可以设置定时更新权限<br/>
	 * 
	 * 权限组成：系统角色ID_用户权限ID。构成原因：比如一个系统有三个角色，分别是管理员、卖家、买家；
	 * 1个用户可以同时有三个身份或者任意几个身份；除了管理员需要控制权限外，其它两个身份一旦拥有即拥有此角色的所有权限；
	 * 即如果用户是卖家，则拥有卖家的所有权限，不会单独进行权限控制。
	 * 类似此种情况除了管理员有权限控制外，其它角色是通过其用户角色进行区分，所以可以在权限内加入角色标识进行角色和权限验证（当然，只要不嫌麻烦也可将买家和卖家也按照管理员那样管理，权限将更加灵活可配）
	 * @return
	 */
	Collection<Permission> getPermissions();
}

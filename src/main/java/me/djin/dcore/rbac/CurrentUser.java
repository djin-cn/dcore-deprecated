/**
 * 
 */
package me.djin.dcore.rbac;

import org.apache.shiro.SecurityUtils;

/**
 * @author djin
 * 当前登录用户
 */
public class CurrentUser {
	private Long userid;
	private String username;
	private String name;
	private Integer gender;
	private Integer systemRole;
	
	/**
	 * 当前用户对象
	 * @return
	 */
	public static final CurrentUser subject() {
		return (CurrentUser)SecurityUtils.getSubject().getPrincipal();
	}

	/**
	 * 获取用户ID
	 * @return
	 */
	public Long getUserid() {
		return userid;
	}

	/**
	 * 获取登录名
	 * @return
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * 获取用户姓名
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * 获取用户性别
	 * @return
	 */
	public Integer getGender() {
		return gender;
	}

	/**
	 * 获取系统角色
	 * @return
	 */
	public Integer getSystemRole() {
		return systemRole;
	}

	/**
	 * 设置用户ID
	 * @param userid
	 */
	public void setUserid(Long userid) {
		this.userid = userid;
	}

	/**
	 * 设置登录名
	 * @param username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * 设置姓名
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 设置用户性别
	 * @param gender
	 */
	public void setGender(Integer gender) {
		this.gender = gender;
	}

	/**
	 * 设置用户系统角色
	 * @param systemRole
	 */
	public void setSystemRole(Integer systemRole) {
		this.systemRole = systemRole;
	}
}
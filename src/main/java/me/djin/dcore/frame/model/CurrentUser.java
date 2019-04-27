package me.djin.dcore.frame.model;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSONObject;

/**
 * @author djin
 * 当前登录用户
 */
public class CurrentUser {
	public static final String TOKEN_HEADER = "Authorization";
	private Long userid;
	private String username;
	private String name;
	private Integer gender;
	/**
	 * 用户系统角色
	 */
	private Integer systemRole;
	
	/**
	 * 当前用户对象,如果没有当前用户则返回空实例用户对象，可根据userid判断是否存在当前用户
	 * @return
	 */
	public static final CurrentUser subject() {
		String exceptionMessage = "CurrentUser使用错误，CurrentUser只能在web请求中使用，不能用于其它时间和场景(如：多线程、定时任务等)";
		ServletRequestAttributes attrs = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
		if(attrs == null) {
			throw new RuntimeException(exceptionMessage);
		}
		HttpServletRequest request = attrs.getRequest();
		if(request == null) {
			throw new RuntimeException(exceptionMessage);
		}
		CurrentUser user = (CurrentUser)request.getAttribute(TOKEN_HEADER);
		if(user != null) {
			return user;
		}
		String token = request.getHeader(TOKEN_HEADER);
		if(StringUtils.isBlank(token)) {
			user = new CurrentUser();
		}else {
			try {
				user = JSONObject.parseObject(token, CurrentUser.class);
			}catch (Exception e) {
				user = new CurrentUser();
			}
		}
		request.setAttribute(TOKEN_HEADER, user);
		return user;
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
	
	@Override
	public String toString() {
		return JSONObject.toJSONString(this);
	}
}
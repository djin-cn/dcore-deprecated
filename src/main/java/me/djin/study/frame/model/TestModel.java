package me.djin.study.frame.model;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="测试对象")
public class TestModel {
	/**
	 * 用户名称
	 */
	@NotBlank
	@ApiModelProperty(required=true, value="用户名称")
	private String username;
	@ApiModelProperty(value="用户性别, 0-女;1-男", allowableValues="0,1")
	private int gender;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}
}

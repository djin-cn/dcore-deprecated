package me.djin.study.frame.model;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;

/**
 * 同一输出格式包
 * @author djin
 *
 */
@ApiModel("Output")
@SuppressWarnings("serial")
public class OutputModel<T> implements Serializable {
	private String status="1";
	private String statusText = "操作成功";
	private T data = null;
	
	/**
	 * 统一输出格式
	 * @param status 状态码，错误码
	 */
	public OutputModel(String status) {
		setStatus(status);
	}
	
	/**
	 * 统一输出格式
	 * @param status 状态码，错误码
	 * @param data 输出数据
	 */
	public OutputModel(String status, T data) {
		setStatus(status);
		setData(data);
	}
	
	/**
	 * 统一输出格式
	 * @param status 状态码，错误码
	 * @param statusText 状态消息
	 */
	public OutputModel(String status, String statusText) {
		setStatus(status);
		setStatusText(statusText);
	}
	
	/**
	 * 统一输出格式
	 * @param status 状态码，错误码
	 */
	public OutputModel(int status) {
		setStatus(String.valueOf(status));
	}
	
	/**
	 * 统一输出格式
	 * @param status 状态码，错误码
	 * @param data 输出数据
	 */
	public OutputModel(int status, T data) {
		setStatus(String.valueOf(status));
		setData(data);
	}
	
	/**
	 * 统一输出格式
	 * @param status 状态码，错误码
	 * @param statusText 状态消息
	 */
	public OutputModel(int status, String statusText) {
		setStatus(String.valueOf(status));
		setStatusText(statusText);
	}
	
	/**
	 * 获取状态码，错误码
	 * @return
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * 设置状态码，错误码
	 * @param status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * 获取状态消息
	 * @return
	 */
	public String getStatusText() {
		return statusText;
	}

	/**
	 * 设置状态消息
	 * @param statusText
	 */
	public void setStatusText(String statusText) {
		this.statusText = statusText;
	}
	
	/**
	 * 获取输出数据
	 * @return
	 */
	public T getData() {
		return data;
	}
	
	/**
	 * 设置输出数据
	 * @param data
	 */
	public void setData(T data) {
		this.data = data;
	}
}
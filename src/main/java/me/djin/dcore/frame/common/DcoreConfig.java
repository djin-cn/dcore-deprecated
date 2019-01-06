package me.djin.dcore.frame.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * 框架初始化配置，为正确配置可能导致某些功能不能正常使用甚至系统无法运行
 * @author djin
 *
 */
@Configuration
public class DcoreConfig {
	private String permissionsApi;
	private String rsaApi;
	@Value("${dcore.mq:thread}")
	private String mq;
	
	/**
	 * 权限列表API，获取所有权限。
	 * @return
	 */
	public String getPermissionsApi() {
		return permissionsApi;
	}
	/**
	 * 权限列表API，获取所有权限。
	 * @param permissionsApi
	 */
	public void setPermissionsApi(String permissionsApi) {
		this.permissionsApi = permissionsApi;
	}
	/**
	 * 公钥API，获取公钥
	 * @return
	 */
	public String getRsaApi() {
		return rsaApi;
	}
	/**
	 * 公钥API，获取公钥
	 * @param rsaApi
	 */
	public void setRsaApi(String rsaApi) {
		this.rsaApi = rsaApi;
	}

	/**
	 * MQ厂商，默认thread
	 * @return
	 */
	public String getMq() {
		return mq;
	}

	/**
	 * MQ厂商
	 * @param mq
	 */
	public void setMq(String mq) {
		this.mq = mq;
	}
}
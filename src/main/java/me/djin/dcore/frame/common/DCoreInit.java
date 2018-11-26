package me.djin.dcore.frame.common;

/**
 * 框架初始化配置，为正确配置可能导致某些功能不能正常使用甚至系统无法运行
 * @author djin
 *
 */
public class DCoreInit {
	private static final DCoreInit INSTANCE = new DCoreInit();
	
	private String permissionsApi;
	private String rsaApi;
	
	private DCoreInit() {}
	
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
	 * 获取框架初始化实例
	 * @return
	 */
	public DCoreInit getInstance() {
		return INSTANCE;
	}
}
package me.djin.dcore.rbac;

/**
 * 权限
 * 
 * @author djin
 *
 */
public class Permission {
	private String code;
	private String path;

	/**
	 * 权限码，可以用权限ID，也可以是其它，但是必须唯一
	 * 
	 * @return
	 */
	public String getCode() {
		return code;
	}

	/**
	 * 权限码，可以用权限ID，也可以是其它，但是必须唯一
	 * 
	 * @param code
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * URL表达式，支持通配符<br/>
	 * ?:匹配一个任意字符<br/>
	 * *:匹配当前目录下任意长度字符，但不支持子目录匹配<br/>
	 * **:匹配任意长度字符，包括子目录
	 * 
	 * @return
	 */
	public String getPath() {
		return path;
	}

	/**
	 * URL表达式，支持通配符<br/>
	 * ?:匹配一个任意字符<br/>
	 * *:匹配当前目录下任意长度字符，但不支持子目录匹配<br/>
	 * **:匹配任意长度字符，包括子目录
	 * 
	 * @param path
	 */
	public void setPath(String path) {
		this.path = path;
	}
}
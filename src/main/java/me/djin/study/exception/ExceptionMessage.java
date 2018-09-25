package me.djin.study.exception;

import java.io.File;
import java.io.FileNotFoundException;

import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;

/**
 * 异常消息获取类。<br />
 * 通过在classes目录下配置error_message.properties文件，此文件为统一的异常消息配置文件。<br />
 * 异常消息通过键值对方式配置，键为消息码，值为消息表达式，表达式可通过%s指定参数格式化输出<br />
 * @author djin
 *
 */
class ExceptionMessage {
	private static PropertiesConfiguration cfg = null;
	private static final String MESSAGEPATH="error_message.properties";
	public ExceptionMessage() {
		cfg = load(MESSAGEPATH);
	}
	
	/**
	 * 加载配置文件
	 * 
	 * @param propertiesFile
	 *            配置文件路径,相对于classes目录，或者绝对路径
	 * @return
	 */
	private PropertiesConfiguration load(String propertiesFile) {
		Configurations configs = new Configurations();
		File file = new File(propertiesFile);
		PropertiesConfiguration config = null;
		if (file.exists()) {
			throw new RuntimeException(new FileNotFoundException(file.getPath()));
		}
		try {
			config = configs.properties(file);
		} catch (ConfigurationException e) {
			throw new RuntimeException(e);
		}
		return config;
	}
	
	/**
	 * 获取异常消息
	 * @param code 错误码
	 * @param params 消息参数
	 * @return
	 */
	public String getMessage(String code, Object... params) {
		String message = cfg.getString(code);
		if(params == null || params.length == 0) {
			return message;
		}
		return String.format(message, params);
	}
	
	public static void main (String[] args) {
		String expr = "hello %s, hao are you!";
		System.out.println(String.format(expr, "djin"));
	}
}

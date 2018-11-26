/**
 * 
 */
package me.djin.dcore.frame.model;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;

/**
 * @author djin
 * 所有POJO的基础类
 */
@SuppressWarnings("serial")
public abstract class BaseObject implements Serializable {
	/**
	 * 将当前实体类转换为其它POJO类
	 * @param clazz 目标类
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	/*public <T extends BaseObject> T convert(Class<T> clazz) throws InstantiationException, IllegalAccessException {
		return me.djin.dcore.util.BeanUtils.copy(this, clazz);
	}*/
	
	/**
	 * 重写之后的toString方法，返回实体类的JSON字符串
	 */
	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}
}

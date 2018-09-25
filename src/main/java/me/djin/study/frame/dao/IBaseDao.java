package me.djin.study.frame.dao;

import tk.mybatis.mapper.common.Mapper;

/**
 * @author djin
 * 基础数据访问类，提供数据库基础访问操作
 * <br />有两种操作方式，1：继承自此接口，但是使用的时候不要直接使用此接口实例化；2：其他操作不要继承此接口
 * @param <T>
 */
public interface IBaseDao<T> extends Mapper<T> {

}

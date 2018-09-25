package me.djin.study.frame.service;

import java.util.List;

/**
 * @author djin
 * 基础服务接口，提供常规增删改查
 * @param <T> 实体类
 */
public interface IBaseService<T> {
	long add(T t);
	long update(T t);
	long delete(T t);
	T get(T t);
	List<T> query(T t);
	List<T> query(T t, int pageNumber, int pageSize);
}

package me.djin.study.frame.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

import me.djin.study.frame.dao.IBaseDao;

/**
 * 基础服务实现
 * @author djin
 *
 * @param <T>
 */
@Service
public class BaseService<T> implements IBaseService<T> {
	private static final Logger LOGGER = LoggerFactory.getLogger(BaseService.class);
	@Autowired 
	protected IBaseDao<T> dao;

	@Override
	public long add(T t) {
		System.out.println("baseservice add");
		LOGGER.debug("add:%s; %s", BaseService.class.getName(), t);
		return 1;
//		return dao.insert(t);
	}

	@Override
	public long update(T t) {
		return dao.updateByPrimaryKey(t);
	}

	@Override
	public long delete(T t) {
		return dao.delete(t);
	}

	@Override
	public T get(T t) {
		return dao.selectOne(t);
	}

	@Override
	public List<T> query(T t) {
		return dao.select(t);
	}

	@Override
	public List<T> query(T t, int pageNumber, int pageSize) {
		PageHelper.startPage(pageNumber, pageSize);
		dao.select(t);
		dao.selectCount(t);
		return null;
	}
}

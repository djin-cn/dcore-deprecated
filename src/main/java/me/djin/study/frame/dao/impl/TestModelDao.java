package me.djin.study.frame.dao.impl;

import me.djin.study.frame.model.TestModel;
import tk.mybatis.mapper.common.Mapper;

/**
 * 基础数据访问类，提供数据库基础访问操作
 * @author djin
 *
 * @param <T>
 */
public interface TestModelDao extends Mapper<TestModel> {

}

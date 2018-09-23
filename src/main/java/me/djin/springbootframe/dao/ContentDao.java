package me.djin.springbootframe.dao;

import org.apache.ibatis.annotations.Mapper;

import me.djin.springbootframe.model.Content;

@Mapper
public interface ContentDao extends tk.mybatis.mapper.common.Mapper<Content> {

}

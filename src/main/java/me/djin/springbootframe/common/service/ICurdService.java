package me.djin.springbootframe.common.service;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
//@Scope("prototype")
public interface ICurdService<T> {
	long add(T t);
}

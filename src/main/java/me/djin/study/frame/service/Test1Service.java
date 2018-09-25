package me.djin.study.frame.service;

import org.springframework.stereotype.Service;

import me.djin.study.frame.model.TestModel;

@Service
public class Test1Service extends BaseService<TestModel> {

	@Override
	public long add(TestModel t) {
		System.out.println("Test1Service add");
		return 1;
//		return dao.insert(t);
	}
}

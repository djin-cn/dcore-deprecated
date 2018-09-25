package me.djin.study.frame.common;

import org.springframework.stereotype.Repository;

@Repository
public class BaseDao {
	public void add() {
		System.out.println("BaseDao add");
	}
}

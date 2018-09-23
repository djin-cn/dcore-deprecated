package me.djin.springbootframe.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import me.djin.springbootframe.common.service.impl.CurdService;
import me.djin.springbootframe.dao.ContentDao;
import me.djin.springbootframe.model.Content;

@Service
public class ContentService extends CurdService<Content> {
	@Autowired
	private ContentDao dao;
	
	/*public long add(Content model) {
		return 0;
	}*/
	public long add(Content model) {
		return dao.insert(model);
	}
}

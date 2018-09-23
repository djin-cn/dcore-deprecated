package me.djin.springbootframe.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

import me.djin.springbootframe.common.service.ICurdService;
import me.djin.springbootframe.model.Content;
import me.djin.springbootframe.model.Content1;

@RestController
@RequestMapping("/test")
public class TestController {
	@Autowired
	private ICurdService<Content> svc;
	@Autowired
//	private ICurdService<Content1> svcContent;
	
	@RequestMapping(method=RequestMethod.GET, value="/hello")
	public String hello() {
		return "hello word b!";
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/add")
	public String add() {
		String content = "";
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("key1", "value1");
		content = JSONObject.toJSONString(map);
		Content model = new Content();
		model.setId(1L);
		model.setAddTime((new Date()).getTime());
		model.setLatestTime(model.getAddTime());
		model.setStatus(1);
		model.setType(1);
		model.setUid(0L);
		model.setUsername("admin");
		model.setContent(content);
		long r1 = svc.add(model);
		//long r2 = svcContent.add(new Content1());
		System.out.println(r1);
		//System.out.println(r2);
		return "add";
	}
}

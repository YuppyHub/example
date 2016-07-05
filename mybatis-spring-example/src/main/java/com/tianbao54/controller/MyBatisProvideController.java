package com.tianbao54.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianbao54.model.User;
import com.tianbao54.service.MyBatisProvideService;

@Controller
@RequestMapping("/v002/mybatis")
public class MyBatisProvideController {

	@Autowired
	private MyBatisProvideService myBatisProvideService;
	
	@RequestMapping(value = "/queryAll",method = RequestMethod.GET)
	public @ResponseBody String forQuery () {
		List<User> users = myBatisProvideService.queryAllUsers();
		System.out.println(users);
		return "query mybatis";
	}
	
	@RequestMapping(value = "/queryByid",method = RequestMethod.GET)
	public @ResponseBody String queryByid (@RequestParam int id) {
		User user = myBatisProvideService.queryById(id);
		System.out.println(user);
		return "query mybatis";
	}
	
	@RequestMapping(value = "/insert",method = RequestMethod.GET)
	public @ResponseBody String insert (@RequestParam String name,@RequestParam String username,@RequestParam String password) {
		return myBatisProvideService.insert(name, username, password);
	}
	
	@RequestMapping(value = "/insertBatch",method = RequestMethod.GET)
	public @ResponseBody String insertBatch (@RequestParam String username,@RequestParam String password) {
		return myBatisProvideService.insertBatch(username, password);
	}
	
	@RequestMapping(value = "/querybyids",method = RequestMethod.GET)
	public @ResponseBody String queryUserByIds (@RequestParam List<Integer> id) {
		List<User> users = myBatisProvideService.queryByIds(id);
		System.out.println(users);
		return "success";
	}
}
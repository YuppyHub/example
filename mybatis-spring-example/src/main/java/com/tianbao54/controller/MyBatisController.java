package com.tianbao54.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianbao54.model.User;
import com.tianbao54.service.MyBatisService;

@Controller
@RequestMapping("/v001/mybatis")
public class MyBatisController {

	@Autowired
	private MyBatisService myBatisService;
	
	@RequestMapping(value = "/query",method = RequestMethod.GET)
	public @ResponseBody String forQuery (@RequestParam String username , @RequestParam String password) {
		
		User user = myBatisService.query(username,password);
		
		return "query mybatis";
	}
	
	@RequestMapping(value = "/insert",method = RequestMethod.GET)
	public @ResponseBody String insert (@RequestParam String username, @RequestParam String password	) {
		
		String result = myBatisService.insert(username, password);
		
		return result;
	}
	
	@RequestMapping(value = "/insertBatch",method = RequestMethod.GET)
	public @ResponseBody String insertBatch (@RequestParam String username, @RequestParam String password	) {
		
		String result = myBatisService.insertBatch(username,password);
		return result;
	}
	
	@RequestMapping(value = "/update",method = RequestMethod.GET)
	public @ResponseBody String update (@RequestParam int id,@RequestParam String username, @RequestParam String password) {
		
		String result = myBatisService.update(id,username,password);
		return result;
	}
}

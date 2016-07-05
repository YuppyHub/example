package com.tianbao54.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianbao54.mapper.MyBatisMapper;
import com.tianbao54.model.User;
import com.tianbao54.service.MyBatisService;

@Service("myBatisService")
public class MyBatisServiceImpl implements MyBatisService{

	@Autowired
	private MyBatisMapper myBatisMapper;
	
	@Override
	public User query(String username, String password) {
		User user = myBatisMapper.query(username, password);
		return user;
	}

	@Override
	public String insert(String username, String password) {
		
		User user = new User ();
		user.setName("zhangsan");
		user.setUsername(username);
		user.setPassword(password);
		user.setAddress("shanghai.");
		
		int result = myBatisMapper.insert(user);
		
		return result == 1 ? "success" : "error";
	}
	
	public String insertBatch (String username,String password) {
		
		List<User> users = new ArrayList<User>();
		for (int i = 0 ; i < 20 ; i ++) {
			User user = new User ();
			user.setName("name" + i);
			user.setUsername(username + i);
			user.setPassword(password + i);
			user.setEmail("eamil" + i);
			user.setAddress("address" + i);
			users.add(user);
		}
		
		int result = myBatisMapper.insertBatch(users);
		return result == 20 ? "success" : "error";
	}

	@Override
	public String update(int id,String username,String password) {
		
		User user = new User ();
		user.setId(id);
		user.setUsername(username);
		user.setPassword(password);
		int result = myBatisMapper.update(user);
		
		return result == 1 ? "success" : "error";
	}
}
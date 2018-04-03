package com.tianbao54.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianbao54.mapper.MyBatisProvideMapper;
import com.tianbao54.model.User;
import com.tianbao54.service.MyBatisProvideService;

@Service("myBatisProvideService")
public class MyBatisProvideServiceImpl implements MyBatisProvideService{

	@Autowired
	private MyBatisProvideMapper myBatisProvideMapper;
	
	@Override
	public List<User> queryAllUsers () {
		
		List<User> users = myBatisProvideMapper.getAllUsers();
		return users;
	}

	@Override
	public User queryById(int id) {
		User user = myBatisProvideMapper.queryById(id);
		return user;
	}

	@Override
	public String insert(String name, String username, String password) {
		
		User user = new User ();
		user.setName(name);
		user.setUsername(username);
		user.setPassword(password);
		user.setEmail("email");
		user.setAddress("address");
		
		int result = myBatisProvideMapper.insert(user);
		
		return result == 1 ? "success" : "error";
	}

	@Override
	public String insertBatch(String username, String password) {
		
		List<User> users = new ArrayList<User>();
		for (int i = 0 ; i < 20 ; i ++) {
			User user = new User ();
			user.setName("name" + i);
			user.setUsername(username + i);
			user.setPassword(password + i);
			user.setEmail("email" + i);
			user.setAddress("address" + i);
			users.add(user);
		}
		int result = myBatisProvideMapper.insertBatch(users);
		
		return result == 20 ? "success" : "error";
	}

	@Override
	public List<User> queryByIds(List<Integer> ids) {
		List<User> users = myBatisProvideMapper.queryByIds(ids);
		return users;
	}
}
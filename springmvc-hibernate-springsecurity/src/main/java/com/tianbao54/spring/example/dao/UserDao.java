package com.tianbao54.spring.example.dao;

import java.util.List;

import com.tianbao54.spring.example.model.User;


public interface UserDao {

	User findById(int id);
	
	User findBySSO(String sso);
	
	void save(User user);
	
	void deleteBySSO(String sso);
	
	List<User> findAllUsers();

}


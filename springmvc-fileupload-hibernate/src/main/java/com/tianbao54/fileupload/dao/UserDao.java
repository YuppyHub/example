package com.tianbao54.fileupload.dao;

import java.util.List;

import com.tianbao54.fileupload.model.User;


public interface UserDao {

	User findById(int id);
	
	User findBySSO(String sso);
	
	void save(User user);
	
	void deleteBySSO(String sso);
	
	List<User> findAllUsers();

}


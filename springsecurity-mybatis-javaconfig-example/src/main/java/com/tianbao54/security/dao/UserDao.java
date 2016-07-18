package com.tianbao54.security.dao;

import com.tianbao54.security.model.User;

public interface UserDao {

	User findById(int id);
	
	User findBySSO(String sso);
	
}


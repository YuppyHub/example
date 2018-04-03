package com.tianbao54.security.service;

import com.tianbao54.security.model.User;

public interface UserService {

	User findById(int id);
	
	User findBySso(String sso);
	
}
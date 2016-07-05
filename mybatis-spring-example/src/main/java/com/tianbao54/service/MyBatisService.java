package com.tianbao54.service;

import com.tianbao54.model.User;

public interface MyBatisService {

	User query (String username,String password);
	String insert (String username,String password);
	String insertBatch(String username,String password);
	String update (int id,String username,String password);
}

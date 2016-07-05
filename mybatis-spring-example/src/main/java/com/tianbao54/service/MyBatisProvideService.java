package com.tianbao54.service;

import java.util.List;

import com.tianbao54.model.User;

public interface MyBatisProvideService {

	List<User> queryAllUsers ();
	User queryById(int id);
	String insert (String name,String username,String password);
	String insertBatch (String username,String password);
	List<User> queryByIds(List<Integer> ids);
}

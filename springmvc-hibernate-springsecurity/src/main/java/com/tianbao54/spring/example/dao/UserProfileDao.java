package com.tianbao54.spring.example.dao;

import java.util.List;

import com.tianbao54.spring.example.model.UserProfile;


public interface UserProfileDao {

	List<UserProfile> findAll();
	
	UserProfile findByType(String type);
	
	UserProfile findById(int id);
}

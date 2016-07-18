package com.tianbao54.spring.example.service;

import java.util.List;

import com.tianbao54.spring.example.model.UserProfile;


public interface UserProfileService {

	UserProfile findById(int id);

	UserProfile findByType(String type);
	
	List<UserProfile> findAll();
	
}

package com.tianbao54.spring.security.service;

import java.util.List;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;

import com.tianbao54.spring.security.model.User;


public interface UserService {

	List<User> findAllUsers();

	@PostAuthorize ("returnObject.type == authentication.name")
	User findById(int id);

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	void updateUser(User user);
	
	@PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_DBA')")
	void deleteUser(int id);
}
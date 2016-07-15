package com.tianbao54.security.dao.mapper;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.tianbao54.security.model.User;

public interface UserMapper {

	@Select("select * from app_user where sso_id=#{username}")
	@Results({ @Result(id = true, column = "id", property = "id"), 
		@Result(column = "sso_id", property = "ssoId"),
		@Result(column = "password", property = "password"), 
		@Result(column = "last_name", property = "firstName"),
		@Result(column = "last_name", property = "lastName"), 
		@Result(column = "email", property = "email") })
	User selectUserByName(String username);
}

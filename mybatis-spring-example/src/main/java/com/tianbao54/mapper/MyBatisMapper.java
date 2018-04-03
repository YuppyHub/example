package com.tianbao54.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.tianbao54.model.User;

public interface MyBatisMapper {

	@Select(value = "SELECT * FROM T_USER t WHERE t.username=#{username} AND t.password=#{password}")
	@Results({ @Result(id = true, column = "id", property = "id"), 
			@Result(column = "name", property = "name"),
			@Result(column = "username", property = "username"), 
			@Result(column = "password", property = "password"),
			@Result(column = "email", property = "email"), 
			@Result(column = "address", property = "address") })
	User query(@Param("username") String username,@Param("password") String password);

	@Insert(value = "INSERT INTO T_USER VALUES (NULL,#{name},#{username},#{password},#{email},#{address})")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	int insert(User user);

	@Insert({ "<script>", "INSERT INTO T_USER ", "VALUES ", "<foreach  collection='users' item='user' separator=','>",
			"(NULL,#{user.name,jdbcType=VARCHAR}, #{user.username,jdbcType=VARCHAR},"
			+ " #{user.password,jdbcType=VARCHAR}, #{user.email,jdbcType=VARCHAR}, #{user.address,jdbcType=VARCHAR})",
			"</foreach>", "</script>" })
	int insertBatch(@Param("users") List<User> users);
	
	@Update(value = "UPDATE T_USER t SET t.username = #{user.username},t.password= #{user.password} WHERE t.id = #{user.id}")
	int update(@Param("user") User user) ;
}

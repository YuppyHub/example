package com.tianbao54.mapper;

import java.util.List;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import com.tianbao54.model.User;
import com.tianbao54.provide.SQLProvide;

public interface MyBatisProvideMapper {

	@SelectProvider(type = SQLProvide.class ,method = "selectAllUser")
	List<User> getAllUsers ();
	@SelectProvider(type = SQLProvide.class ,method = "selectUserById")
	User queryById (@Param("id")int id);
	@InsertProvider(type = SQLProvide.class ,method = "insert")
	int insert (@Param("user") User user);
	@InsertProvider(type = SQLProvide.class ,method = "insertBatch")
	int insertBatch (@Param("users") List<User> users);
	@SelectProvider(type = SQLProvide.class ,method = "selectUserByIds")
	List<User> queryByIds (@Param("ids")List<Integer> ids);
}

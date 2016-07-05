package com.tianbao54.provide;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

import com.tianbao54.model.User;

public class SQLProvide {

	public String selectAllUser () {
		
		return "select * from t_user";
	}
	
	public String selectUserById (Map <String,Object> params) {
		
		int id = Integer.valueOf(params.get("id").toString());
		StringBuffer sql = new StringBuffer ();
		sql.append("select * from t_user t");
		
		if (id == 0) {
			sql.append(" where t.id is null ");
		} else {
			sql.append(" where t.id = #{id}");
		}
		
		return sql.toString();
	}
	
	public String selectUserByIds (Map <String,List<Integer>> params) {
		
		List<Integer> ids = params.get("ids");  
        StringBuilder sql = new StringBuilder();  
        sql.append("SELECT * FROM T_USER t WHERE t.ID in (");  
        MessageFormat messageFormat = new MessageFormat("#'{'ids[{0}]}");  
        for(int i = 0 ;i<ids.size();i++) {  
        	sql.append(messageFormat.format(new Integer[]{i}));  
            if (i < ids.size() - 1) {    
            	sql.append(",");     
            }  
        }  
        sql.append(")");  
        System.out.println(sql.toString());  
		
		return sql.toString();
	}
	
	public String insert (Map<String ,Object> params) {
		
		String sql = ("insert into t_user values (null,#{user.name},#{user.username},#{user.password},#{user.email},#{user.address})");
		
		return sql;
	}
	
	public String insertBatch (Map<String ,List<User>> params) {
		List<User> users = params.get("users");
		StringBuffer sql = new StringBuffer ();
		sql.append("insert into t_user values ");
		MessageFormat format = new MessageFormat ("(null,#'{'users[{0}].name},#'{'users[{0}].username},#'{'users[{0}].password},#'{'users[{0}].email},#'{'users[{0}].address})");
		
		for (int i = 0 ; i < users.size() ; i ++) {
			sql.append(format.format(new Object[]{i}));
			if (i < users.size() - 1) {    
				sql.append(",");     
            } 
		}
		System.out.println(sql.toString());
		return sql.toString();
	}
}
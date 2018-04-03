package com.tianbao54.security.dao;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tianbao54.security.dao.mapper.UserMapper;
import com.tianbao54.security.model.State;
import com.tianbao54.security.model.User;
import com.tianbao54.security.model.UserProfile;
import com.tianbao54.security.model.UserProfileType;

@Repository("userDao")
public class UserDaoImpl implements UserDao {

	@Autowired
	private UserMapper userMapper;
	
	public User findById(int id) {
		
		Set<UserProfile> profiles = new HashSet<UserProfile>();
		UserProfile profile = new UserProfile ();
		profile.setId(1);
		profile.setType(UserProfileType.ADMIN.getUserProfileType());
		profiles.add(profile);
		User user = null;
		if (id == 1) {
			user = new User();
			user.setEmail("admin@admin.com");
			user.setFirstName("Admin");
			user.setId(1);
			user.setLastName("istrator");
			user.setPassword("admin");
			user.setSsoId("admin");
			user.setState(State.ACTIVE.getState());
			user.setUserProfiles(profiles);
		}
		return user;
	}

	public User findBySSO(String sso) {
		
		User userx = userMapper.selectUserByName(sso);
		
		Set<UserProfile> profiles = new HashSet<UserProfile>();
		UserProfile profile = new UserProfile ();
		profile.setId(1);
		profile.setType(UserProfileType.ADMIN.getUserProfileType());
		profiles.add(profile);
		User user = null;
		if (sso.equals("admin")) {
			user = new User();
			user.setEmail("admin@admin.com");
			user.setFirstName("Admin");
			user.setId(1);
			user.setLastName("istrator");
			user.setPassword("admin");
			user.setSsoId("admin");
			user.setState(State.ACTIVE.getState());
			user.setUserProfiles(profiles);
		}
		userx.setState(State.ACTIVE.getState());
		userx.setUserProfiles(profiles);
		return userx;
	}
}
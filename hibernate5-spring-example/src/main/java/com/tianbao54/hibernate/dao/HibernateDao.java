package com.tianbao54.hibernate.dao;

import com.tianbao54.hibernate.model.User;

public interface HibernateDao {

	User queryById(int id);
}

package com.tianbao54.hibernate.dao.impl;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.tianbao54.hibernate.dao.HibernateDao;
import com.tianbao54.hibernate.model.User;

@Repository("hibernateDao")
public class HibernateDaoImpl implements HibernateDao{

	@Resource(name = "localSessionFactoryBean")
	private SessionFactory sessionFactroy;

	public User queryById(int id) {
		
		Session session = sessionFactroy.getCurrentSession();
		return session.get(User.class, id);
	}
}
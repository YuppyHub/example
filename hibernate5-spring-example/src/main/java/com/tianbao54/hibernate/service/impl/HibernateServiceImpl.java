package com.tianbao54.hibernate.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianbao54.hibernate.dao.HibernateDao;
import com.tianbao54.hibernate.model.User;
import com.tianbao54.hibernate.service.HibernateService;

@Service("hibernateService")
public class HibernateServiceImpl implements HibernateService {

	@Autowired
	private HibernateDao hibernateDao;
	
	public String queryById(int id) {
		
		User user = hibernateDao.queryById(id);
		return user.toString();
	}

}

package com.tianbao54.hibernate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianbao54.hibernate.service.HibernateService;

@Controller
@RequestMapping("/v001/hibernate")
public class HibernateController {

	@Autowired
	private HibernateService hibernateService;
	
	@RequestMapping(value = "/queryById",method = RequestMethod.GET)
	public @ResponseBody String queryById (@RequestParam int id) {
		return hibernateService.queryById(id);
	}
}

package com.milkharbor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.milkharbor.dao.FarmerDao;
import com.milkharbor.entity.ResponseLogin;
import com.milkharbor.entity.User;

@Service
public class FarmerService {

	@Autowired
	FarmerDao farmerDao;
	
	public boolean register(User farmer) throws Exception {
		return farmerDao.register(farmer);
	}
	
	public String getUser(int id) {
		return farmerDao.getUser(id);
	}
}

package com.milkharbor.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.milkharbor.dao.AdminDao;
import com.milkharbor.encrypt.EncryptDecrypt;
import com.milkharbor.entity.Admin;
import com.milkharbor.entity.Login;
import com.milkharbor.entity.MilkCollection;
import com.milkharbor.entity.ResponseLogin;
import com.milkharbor.entity.User;

@Service
public class AdminService {

	@Autowired
	AdminDao adminDao;
	
	public boolean register(User admin) throws Exception {
		return adminDao.register(admin);
	}
	
	public Object logIn(Login payload) throws Exception {	
		return adminDao.logIn(payload);	
	}
	
	public ResponseLogin getUser(String username) {
		return adminDao.getUser(username);
	}
	
	public boolean inviteFarmers(List<String> farmerEmails) throws Exception {
		return adminDao.inviteFarmers(farmerEmails);
	}
	
	
	public List<ResponseLogin> getPendingFarmers() {
		return adminDao.getPendingFarmers();
	}
	
	public boolean onApprove(Map<String,String> map){	
		return adminDao.onApprove(map);	
	}
	
	public boolean onDecline(Map<String,String> map){	
		return adminDao.onDecline(map);	
	}
	
	public List<ResponseLogin> getFarmers() {
		return adminDao.getFarmers();
	}
	
	public boolean onActive(Map<String,String> map) {
		return adminDao.onActive(map);
	}
	
	public boolean onInActive(Map<String,String> map) {
		return adminDao.onInActive(map);
	}
	
	public boolean onMilkCollection(MilkCollection mc){	
		return adminDao.onMilkCollection(mc);	
	}
}

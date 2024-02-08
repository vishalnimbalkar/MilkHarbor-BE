package com.milkharbor.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.milkharbor.entity.Login;
import com.milkharbor.entity.MilkCollection;
import com.milkharbor.entity.ResponseLogin;
import com.milkharbor.entity.User;
import com.milkharbor.service.AdminService;

@CrossOrigin
@RestController
@RequestMapping("admin")
public class AdminController {

	@Autowired
	AdminService adminService;
	
	@PostMapping("register")
	public boolean register(@RequestBody User admin) throws Exception {
		return adminService.register(admin);
	}
	
	@PostMapping("login")
	public Object logIn(@RequestBody Login payload) throws Exception {	
		return adminService.logIn(payload);	
	}
	
	@GetMapping("/getUser/{username}")
	public ResponseLogin getUser(@PathVariable String username) {
		return adminService.getUser(username);
	}
	
	@PostMapping("inviteFarmers")
	public boolean inviteFarmers(@RequestBody List<String> farmerEmails) throws Exception {
		return adminService.inviteFarmers(farmerEmails);
	}
	
	@GetMapping("/getPendingFarmers")
	public List<ResponseLogin> getPendingFarmers() {
		return adminService.getPendingFarmers();
	}
	
	@PostMapping("approve")
	public boolean onApprove(@RequestBody Map<String,String> map){	
		return adminService.onApprove(map);	
	}
	
	@PostMapping("decline")
	public boolean onDecline(@RequestBody Map<String,String> map){	
		return adminService.onDecline(map);	
	}
	
	@GetMapping("/getFarmers")
	public List<ResponseLogin> getFarmers() {
		return adminService.getFarmers();
	}
	
	@PutMapping("active")
	public boolean onActive(@RequestBody Map<String,String> map) {
		return adminService.onActive(map);
	}
	
	@PutMapping("inactive")
	public boolean onInActive(@RequestBody Map<String,String> map) {
		return adminService.onInActive(map);
	}
	
	@PostMapping("milkCollection")
	public boolean onMilkCollection(@RequestBody MilkCollection mc){	
		return adminService.onMilkCollection(mc);	
	}
}

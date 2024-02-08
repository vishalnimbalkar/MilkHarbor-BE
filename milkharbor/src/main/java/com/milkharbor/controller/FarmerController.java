package com.milkharbor.controller;

import java.util.List;

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

import com.milkharbor.entity.ResponseLogin;
import com.milkharbor.entity.User;
import com.milkharbor.service.FarmerService;

@CrossOrigin
@RestController
@RequestMapping("farmer")
public class FarmerController {

	@Autowired
	FarmerService farmerService;
	
	@PutMapping("register")
	public boolean register(@RequestBody User farmer) throws Exception {
		return farmerService.register(farmer);
	}
	
	@GetMapping("/getUser/{id}")
	public String getUser(@PathVariable int id) {
		return farmerService.getUser(id);
	}
	
}

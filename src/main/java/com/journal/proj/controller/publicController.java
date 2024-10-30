package com.journal.proj.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.journal.proj.Entity.userEntity;
import com.journal.proj.service.userService;

@RestController
@RequestMapping("/public")
public class publicController {
	@Autowired
	private userService user_service;
	
	@PostMapping("/adduser")
	public ResponseEntity<userEntity> addUser(@RequestBody userEntity user) {
		try{
			user_service.addNewUser(user);	
			return new ResponseEntity<userEntity>(HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<userEntity>(HttpStatus.BAD_REQUEST);
		}
	}
	
}

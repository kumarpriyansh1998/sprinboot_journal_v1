package com.journal.proj.controller;

import com.journal.proj.service.userDetailServiceImpl;
import com.journal.proj.utils.jwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.journal.proj.Entity.userEntity;
import com.journal.proj.service.userService;

@RestController
@RequestMapping("/public")
@Slf4j
public class publicController {
	@Autowired
	private userService user_service;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private userDetailServiceImpl userdetailservice;

	@Autowired
	private jwtUtils jwtutils;
	
	@PostMapping("/sign-up")
	public ResponseEntity<userEntity> signup(@RequestBody userEntity user) {
		try{
			user_service.addNewUser(user);	
			return new ResponseEntity<userEntity>(HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<userEntity>(HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody userEntity user) {

		try{
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword()));
			String token = jwtutils.generateToken(user.getUsername());
			return new ResponseEntity<>(token,HttpStatus.OK);
		}catch (Exception e){
			log.error("Exception occured while login ",e);
			return new ResponseEntity<>("Incorrect username or password",HttpStatus.BAD_REQUEST);
		}

	}
	
}

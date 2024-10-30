package com.journal.proj.controller;

import java.util.List;

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
@RequestMapping("/admin")
public class adminController {
	
	@Autowired
	private userService user;
	
	@GetMapping("/all-users")
	public ResponseEntity<?> getAllUsers() {
		List<userEntity> all = user.getAll();
		if(all!=null && !all.isEmpty()) {
			return new ResponseEntity<List<userEntity>>(all,HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}


//	hello
	@PostMapping("/add-admin")
	public void addAdmin(@RequestBody userEntity adminuser) {
		user.addadmin(adminuser);
	}

}

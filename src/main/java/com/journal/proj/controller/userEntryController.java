package com.journal.proj.controller;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.journal.proj.Entity.journalEntity;
import com.journal.proj.Entity.userEntity;
import com.journal.proj.service.journalEntryService;
import com.journal.proj.service.userService;

@RestController
@RequestMapping("/user")
public class userEntryController {
	
	@Autowired
	private userService user_service;
	
	@Autowired
	private journalEntryService journal;
	
	

	@GetMapping	
	public ResponseEntity<List<userEntity>> getAll(){
		try {
			return new ResponseEntity<>(user_service.getAll(),HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}	
	}
	
	
	@PutMapping
	public ResponseEntity<?> updateUser(@RequestBody userEntity user) {
		Authentication auth= SecurityContextHolder.getContext().getAuthentication();
		String name = auth.getName();
		userEntity temp = user_service.findUserByName(name);
		if(temp!=null) {
			temp.setUsername(user.getUsername());
			temp.setPassword(user.getPassword());
			user_service.addNewUser(temp);
			return new ResponseEntity<>(HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}
	
	@DeleteMapping
	public ResponseEntity<?> deleteUser() {
		Authentication auth= SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		try {	
			userEntity u = user_service.findUserByName(username);
			for(journalEntity e:u.getJournalentries()) {
				if(e ==null) continue;
				journal.deleteById(e.getId());
			}
			user_service.deleteUser(u);
			return new ResponseEntity<>(HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	

	
	
	
	
	
}

package com.journal.proj.controller;

import java.io.IOException;
import java.util.List;

import com.journal.proj.api.response.quoteResponse;
import com.journal.proj.service.emailService;
import com.journal.proj.service.quotesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
	private quotesService quotes;
	
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

	@GetMapping("/opt-email")
	public ResponseEntity<List<userEntity>> getUserForSA(){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<userEntity> user_list = user_service.getUsetForSA();
		return new ResponseEntity<List<userEntity>>(user_list,HttpStatus.OK);
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

	@GetMapping("/greeting")
	public ResponseEntity<?> greeting() throws IOException, InterruptedException {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String name = auth.getName();
		quoteResponse quote_ = quotes.getQuote("happiness");
		String quote_of_day = "";
		if (quote_ != null) {
			quote_of_day = quote_.getQuote();
		}

		return new ResponseEntity<>("Hi " + name + ". " + "Quote of the day is " + quote_of_day, HttpStatus.OK);
	}

	@Autowired
	private emailService email;

	@GetMapping("send-mail")
	public void sendMail(String to,String subject,String body){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<userEntity> user_list = user_service.getUsetForSA();
		for(userEntity ele:user_list){
			String _to = ele.getEmail();
			email.sendEmail(_to,"Greetings from Priyansh's Journal App","Hello. Thanks for subscribing for email notification");
		}
	}
	
	
	
	
	
}

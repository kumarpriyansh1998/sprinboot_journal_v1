package com.journal.proj.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.print.attribute.standard.DateTimeAtCompleted;

import org.apache.catalina.connector.Response;
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
@RequestMapping("/journal")
public class journalEntryController {
	
	
	@Autowired
	private journalEntryService service;
	
	@Autowired
	private userService user;
	
	@GetMapping
	public ResponseEntity<List<journalEntity>> getAllentryByUser(){
		Authentication auth= SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		userEntity cur_user = user.findUserByName(username);
		List<journalEntity> temp = cur_user.getJournalentries();
		if(temp!=null && !temp.isEmpty()) {
			return new ResponseEntity<List<journalEntity>>(temp,HttpStatus.OK);
		}else {
			return new ResponseEntity<List<journalEntity>>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping
	public ResponseEntity<journalEntity> addJournal(@RequestBody journalEntity myEntry) {
		try {
			Authentication auth= SecurityContextHolder.getContext().getAuthentication();
			String username = auth.getName();
			service.addEntry(myEntry,username);
			return new ResponseEntity<journalEntity>(HttpStatus.CREATED);
		}catch (Exception e) {
			return new ResponseEntity<journalEntity>(HttpStatus.BAD_REQUEST);
		}

	}
	
	@GetMapping("id/{id}")
	public ResponseEntity<journalEntity> getJournalById(@PathVariable ObjectId id) {
		Authentication auth= SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		long present = validateIdAndUser(id,username);
		if(present>0) {
			Optional<journalEntity> entry = service.findById(id);
			if(entry.isPresent()) {
				return new ResponseEntity<journalEntity>(entry.get(),HttpStatus.OK);
			}
		}
		
		return new ResponseEntity<journalEntity>(HttpStatus.NOT_FOUND);
		
		
		

	}
	
	@DeleteMapping("/id/{id}")
	public ResponseEntity<?> deleteEntryById(@PathVariable ObjectId id) {
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String username = auth.getName();
			long present = validateIdAndUser(id,username);
			Optional<journalEntity> e = service.findById(id);
			userEntity cur_user = user.findUserByName(username);
			if(present>0 && e.isPresent()) {
				Iterator<journalEntity> itr = cur_user.getJournalentries().iterator();
				while(itr.hasNext()) {
					journalEntity cur_entry = itr.next();
					if(cur_entry.getId().equals(id)) {
						itr.remove();
					}
				}
				service.deleteById(id);
				user.addUser(cur_user);
				return new ResponseEntity<>(HttpStatus.OK);
			}else {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

		}catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?>updateEntry (@PathVariable ObjectId id,@RequestBody journalEntity data) {
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String username = auth.getName();
			long present = validateIdAndUser(id,username);
			journalEntity temp = service.findById(id).orElse(null);
			if(present>0 && temp!=null) {
				String title = data.getTitle();
				String content = data.getContent();
				if(title!=null && !title.equals("")) temp.setTitle(title);
				if(content!=null && !content.equals("")) temp.setContent(content);
				service.addUpdatedEntry(temp);
				return new ResponseEntity<journalEntity>(temp,HttpStatus.OK);
			}else {
				return new ResponseEntity<journalEntity>(temp,HttpStatus.NOT_FOUND);
			}
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	private long validateIdAndUser(ObjectId id,String username) {
		userEntity cur_user = user.findUserByName(username);
		long present = cur_user.getJournalentries().stream().filter(x -> x.getId().equals(id)).count();
		return present;
	}

}






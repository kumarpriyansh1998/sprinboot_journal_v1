package com.journal.proj.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.journal.proj.Entity.journalEntity;
import com.journal.proj.Entity.userEntity;
import com.journal.proj.serviceInterface.mongoServiceInterfaceRepo;
import org.bson.types.ObjectId;


@Component
public class journalEntryService {




	@Autowired
	private mongoServiceInterfaceRepo mongoService;
	
	@Autowired
	private userService user;
	
	
	@Transactional
	public boolean addEntry(journalEntity entry,String username) {


		entry.setDate(LocalDateTime.now());
		userEntity cur_user = user.findUserByName(username); 
		journalEntity saved  = mongoService.save(entry);
		cur_user.getJournalentries().add(saved);
		user.addUser(cur_user);
		return true;
	}
	
	public boolean addUpdatedEntry(journalEntity entry) {
		journalEntity saved  = mongoService.save(entry);
		return true;
	}
	
	public List<journalEntity> getAll() {
		return mongoService.findAll();
	}
	
	public Optional<journalEntity> findById(ObjectId obj){
		return mongoService.findById(obj);
	}
	
	public void deleteById(ObjectId obj) {
		mongoService.deleteById(obj);
	}
	


}


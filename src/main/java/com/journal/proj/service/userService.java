package com.journal.proj.service;

import java.util.Arrays;
import java.util.List;

import com.journal.proj.serviceInterface.userRepoImpl;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.journal.proj.Entity.userEntity;
import com.journal.proj.serviceInterface.mongoServiceInterfaceUserRepo;

@Component
@Slf4j
@Data
@NoArgsConstructor
@AllArgsConstructor
public class userService {
	
	@Autowired
	private mongoServiceInterfaceUserRepo user;

	@Autowired
	private userRepoImpl userrepoimpl;

	private static final Logger logger = LoggerFactory.getLogger(userService.class);
	
	private static final PasswordEncoder passwordencoder = new BCryptPasswordEncoder(); 
	
	public List<userEntity> getAll(){
//		log.info("ahahahaha");
		return user.findAll();
	}
	
	public boolean addUser(userEntity userentity) {
		user.save(userentity);
		return true;
	}
	
	public void addNewUser(userEntity userentity) {
		userentity.setPassword(passwordencoder.encode(userentity.getPassword()));
		userentity.setRoles(Arrays.asList("USER"));
		user.save(userentity);
	}
	
	public void addadmin(userEntity userentity) {
		userentity.setPassword(passwordencoder.encode(userentity.getPassword()));
		userentity.setRoles(Arrays.asList("USER","ADMIN"));
		user.save(userentity);
	}
	
	public boolean deleteUser(userEntity userentity) {
		user.delete(userentity);
		return true;
	}

	
	public userEntity findUserByName(String username) {
		return user.findByUsername(username);
	}


	public List<userEntity> getUsetForSA(){
		return userrepoimpl.getUserForSA();
	}

	
}

package com.journal.proj.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.journal.proj.Entity.userEntity;
import com.journal.proj.serviceInterface.mongoServiceInterfaceUserRepo;

@Component
public class userService {
	
	@Autowired
	private mongoServiceInterfaceUserRepo user;
	
	private static final PasswordEncoder passwordencoder = new BCryptPasswordEncoder(); 
	
	public List<userEntity> getAll(){
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
	
}

package com.journal.proj.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.journal.proj.Entity.userEntity;
import com.journal.proj.serviceInterface.mongoServiceInterfaceUserRepo;


@Component
public class userDetailServiceImpl implements UserDetailsService{

	@Autowired
	private mongoServiceInterfaceUserRepo user;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		userEntity nuser = user.findByUsername(username);
		if(nuser!=null) {
			 UserDetails userdetail= User.builder()
			.username(nuser.getUsername())
			.password(nuser.getPassword())
			.roles(nuser.getRoles().toArray(new String[0]))
			.build();
			return userdetail;
		}else {
			throw new UsernameNotFoundException(username+" username is not found");
		}

				
	}

}

package com.journal.proj.serviceInterface;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;
import com.journal.proj.Entity.userEntity;


public interface mongoServiceInterfaceUserRepo extends MongoRepository<userEntity,ObjectId> {
	
	public userEntity findByUsername(String username);
}

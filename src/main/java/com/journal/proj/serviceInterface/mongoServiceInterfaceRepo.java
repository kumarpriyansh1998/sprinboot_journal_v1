package com.journal.proj.serviceInterface;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

import com.journal.proj.Entity.journalEntity;
import org.bson.types.ObjectId;

public interface mongoServiceInterfaceRepo extends MongoRepository<journalEntity, ObjectId>{
		
}

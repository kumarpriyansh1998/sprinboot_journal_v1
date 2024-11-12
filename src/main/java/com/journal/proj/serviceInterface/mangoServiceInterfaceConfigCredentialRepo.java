package com.journal.proj.serviceInterface;

import com.journal.proj.Entity.configEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface mangoServiceInterfaceConfigCredentialRepo extends MongoRepository<configEntity, ObjectId> {
}

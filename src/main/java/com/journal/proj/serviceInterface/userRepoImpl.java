package com.journal.proj.serviceInterface;

import com.journal.proj.Entity.userEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.util.List;
@Component
public class userRepoImpl {

    @Autowired
    private MongoTemplate mongotemplate;
    public List<userEntity> getUserForSA(){
        Query query = new Query();
        Criteria criteria = new Criteria();
        query.addCriteria(Criteria.where("receiveSentimentEmail").is(true));
        query.addCriteria(Criteria.where("email").regex("^[\\w.-]+@[a-zA-Z]+\\.[a-zA-Z]{2,}$"));
        List<userEntity> userEntities = mongotemplate.find(query, userEntity.class);
        return userEntities;

    }
}

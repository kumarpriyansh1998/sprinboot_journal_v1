package com.journal.proj.Entity;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mongodb.lang.NonNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Document(collection = "users")
@NoArgsConstructor
public class userEntity {
	@Id
	private ObjectId id;
	@Indexed(unique=true)
	@NonNull
	private String username;
	@NonNull
	private String password;
	@DBRef
	private List<journalEntity> journalentries = new ArrayList<>();
	private List<String> roles;
	private String email;
	private boolean receiveSentimentEmail;
}

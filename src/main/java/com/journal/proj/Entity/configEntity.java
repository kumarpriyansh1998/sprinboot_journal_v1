package com.journal.proj.Entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "config_credential")
@Getter
@Setter
@NoArgsConstructor
public class configEntity {


    private String key;
    private String value;
}

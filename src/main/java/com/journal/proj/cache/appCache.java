package com.journal.proj.cache;

import com.journal.proj.Entity.configEntity;
import com.journal.proj.serviceInterface.mangoServiceInterfaceConfigCredentialRepo;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class appCache {

    public enum keys{
        QUOTES_API,
        API_KEY,
        SENTIMENT_API
    }


    @Autowired
    private mangoServiceInterfaceConfigCredentialRepo configCredential;


    public Map<String,String> config_cache;

    @PostConstruct
    public void init(){
        config_cache = new HashMap<String,String>();
        List<configEntity> all =  configCredential.findAll();
        for(configEntity con:all){
            config_cache.put(con.getKey(),con.getValue());
        }
    }

}

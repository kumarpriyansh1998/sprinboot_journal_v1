package com.journal.proj.service;

import com.journal.proj.api.response.quoteResponse;
import com.journal.proj.api.response.sentimentResponse;
import com.journal.proj.cache.appCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class sentimentAnalysisService {

    @Autowired
    private RestTemplate resttemplate;

    @Autowired
    private appCache appcache;

    public sentimentResponse getSentiment(String s){
        String sentiment_url = appcache.config_cache.get(appCache.keys.SENTIMENT_API.toString());
        sentiment_url = sentiment_url.replace("<text>",s);
        String api_key = appcache.config_cache.get(appCache.keys.API_KEY.toString());
        HttpHeaders header = new HttpHeaders();
        header.set("X-Api-Key",api_key);
        HttpEntity<String> entity = new HttpEntity<>(header);
        ResponseEntity<sentimentResponse> res = resttemplate.exchange(sentiment_url, HttpMethod.GET, entity, sentimentResponse.class);
        return res.getBody();
    }
}

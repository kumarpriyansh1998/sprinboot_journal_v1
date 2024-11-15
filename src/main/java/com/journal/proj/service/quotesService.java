package com.journal.proj.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;


import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.journal.proj.api.response.quoteResponse;
import com.journal.proj.cache.appCache;
import com.journal.proj.serviceInterface.mangoServiceInterfaceConfigCredentialRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.client.RestTemplate;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.http.HttpRequest;


@Component
public class quotesService {

    @Autowired
    private RestTemplate restTemplate;


    @Autowired
    private appCache appcache;

    @Autowired
    private RedisService redisService;




    public quoteResponse getQuote(String category) throws IOException, InterruptedException {

//        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create(api_url)).header("X-Api-Key", api_key).build();
//        HttpClient client = HttpClient.newBuilder().build();
//        HttpResponse<String> res = client.send(request, HttpResponse.BodyHandlers.ofString());
//
//        ObjectMapper objectmapper = new ObjectMapper();
//        List<quoteResponse> quotes = objectmapper.readValue(res.body(), objectmapper.getTypeFactory().constructCollectionType(List.class, quoteResponse.class));
//        return quotes.size()>0?quotes.get(0):null;


        quoteResponse o  = redisService.get(category,quoteResponse.class);
        if(o==null){
            HttpHeaders headers = new HttpHeaders();
            String api_key= appcache.config_cache.get(appCache.keys.API_KEY.toString());
            headers.set("X-Api-Key", api_key);
            HttpEntity<String> entity = new HttpEntity<>(headers);
            String api_uri = appcache.config_cache.get(appCache.keys.QUOTES_API.toString());
            ResponseEntity<quoteResponse[]> response = restTemplate.exchange(api_uri, HttpMethod.GET, entity, quoteResponse[].class);
            if(response!=null) redisService.set(category,response.getBody()[0],10l);
            return response.getBody() != null ? response.getBody()[0] : null;
        }else{
            return o;
        }






//    If you want to send API post call to an api endpoint with body

//        String request_body = "{\n"+
//                " \"username\":\"utkarsh\",\n"+
//               " \"password\":\"123\"\n" +
//"} ";
//        HttpHeaders headers1 = new HttpHeaders();
//        headers.set("X-Api-Key", api_key);
//        User user = (User) User.builder().username("utkarsh").password("123").build();
//        HttpEntity<User> entity1 = new HttpEntity<>(user,headers1);
//        ResponseEntity<quoteResponse[]> response1 = restTemplate.exchange(api_url, HttpMethod.POST, entity1, quoteResponse[].class);



    }


}

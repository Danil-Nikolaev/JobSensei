package com.nikolaev.JobSensey.API;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nikolaev.JobSensey.converter.ConverterHH;

public class HHAPI extends JobAPI{

    ObjectMapper mapper;

    public HHAPI() {
        super(new ConverterHH());
        this.mapper = new ObjectMapper();
    }

    @Override
    protected String getAllJson(String profession) {
        int pages = getPages(profession);
        String result = "Error";
      
        Map<String, Object> mergeMap = new HashMap<>();

        for (int page = 0; page < pages; page++) {
            String json = getJson(profession, String.valueOf(page));
            mergeMap = concatJson(mergeMap, json);
            
        }
       
        try {
            result = this.mapper.writeValueAsString(mergeMap);
        } catch (Exception e) {}

        return result;
       
    }
    

    private Map<String, Object> concatJson(Map<String, Object> mergeMap, String json) {
        try {
                Map<String, Object> jsonMap = this.mapper.readValue(json, Map.class);
                mergeMap.putAll(jsonMap);
            } catch (Exception e) {}
        return mergeMap;
    }


    private String getJson(String profession, String page) {

        HttpClient httpClient = HttpClient.newHttpClient();
        String apiUrl = "https://api.hh.ru/vacancies?text=" + profession + "&page=" + page;

        try {
             HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(apiUrl))
                .GET()
                .build();
        
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        
            if (response.statusCode() == 200) return  response.body();

        } catch (URISyntaxException | IOException | InterruptedException exc) { } 
        return null;
    }

     private String getJson(String profession) {

        HttpClient httpClient = HttpClient.newHttpClient();
        String apiUrl = "https://api.hh.ru/vacancies?text=" + profession;

        try {
             HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(apiUrl))
                .GET()
                .build();
        
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        
            if (response.statusCode() == 200) return  response.body();

        } catch (URISyntaxException | IOException | InterruptedException exc) { } 
        return null;
    }

    private int getPages(String profession) {
        String json = getJson(profession);
        try {
            Map<String, Object> jsonMap = this.mapper.readValue(json, Map.class);
            return (int) jsonMap.get("pages");
        } catch (Exception e) {}
        return -1;
    }
    
}

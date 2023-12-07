package com.nikolaev.JobSensei.API;

import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.nikolaev.JobSensei.converter.ConverterHH;

@Component
public class HHAPI extends JobAPI {

    ObjectMapper mapper;
    ArrayNode jsonArray;
    RestTemplate restTemplate;
    ProxyRequestAPI proxyRequestAPI;
    boolean activateTimer = false;
    LocalTime localTime;
    private int countRequest = 0;
    @Autowired
    public HHAPI(ConverterHH converterHH, RestTemplate restTemplate, ProxyRequestAPI proxyRequestAPI) {
        super(converterHH);
        this.proxyRequestAPI = proxyRequestAPI;
        this.restTemplate = restTemplate;
        this.mapper = new ObjectMapper();
        this.jsonArray = mapper.createArrayNode();
    }

    @Override
    protected ArrayNode getAllJson(String profession) {
        int pages = getPages(profession);
        for (int page = 0; page < pages; page++) {
            if (this.countRequest > 99) break;

            JsonNode json = getJson(profession, String.valueOf(page));
            getVacancies(json);
        }
        return jsonArray;

    }

    private int getPages(String profession) {
        String jsonString = getJson(profession);
        JsonNode rootNode = stringToJsonNode(jsonString);
        if (rootNode.isObject()) {
            return rootNode.get("pages").asInt();
        }
        return -1;
    }

    private String getJson(String profession) {
        String url = "https://api.hh.ru/vacancies?search_field=name&per_page=100&text=" + profession;
        ResponseEntity<String> response = this.restTemplate.getForEntity(url, String.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        }
        return null;
    }

    private JsonNode stringToJsonNode(String json) {
        try {
            JsonNode root = this.mapper.readTree(json);
             return root;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
       return null;
    }

    private JsonNode getJson(String profession, String page) {
        String apiUrl = "https://api.hh.ru/vacancies?per_page=100&search_field=name&text=" + profession + "&page=" + page;

        ResponseEntity<String> response = this.restTemplate.getForEntity(apiUrl, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            String body = response.getBody();
            JsonNode rootNode = stringToJsonNode(body);
            rootNode = rootNode.get("items");
            return rootNode;
        }
        return null;
    }

    private void getVacancies(JsonNode jsonNode) {
        for (JsonNode item : jsonNode) {
            String url = item.get("url").asText();
            // sleep();
            addJson(getVacancy(url));
        }
    }

    private void addJson(String json) {
        if (json == null) return;
        if (json.equals("error")) return;

        JsonNode jsonNode = stringToJsonNode(json);
        this.jsonArray.add(jsonNode);
    }

    
    private String getVacancy(String url) {
        if (activateTimer && (LocalTime.now().getMinute() - localTime.getMinute() > 0 || LocalTime.now().getSecond() - this.localTime.getSecond() > 10)) {
            System.out.println("work");
            activateTimer = false;
            restTemplate = proxyRequestAPI.restTemplate();
        }

        try{
            System.out.println(countRequest++);
            ResponseEntity<String> response = this.restTemplate.getForEntity(url, String.class);
           
            if (response.getStatusCode() == HttpStatus.OK) {
                return response.getBody();
            }

            if (response.getStatusCode() == HttpStatus.FORBIDDEN) {
                return "error";
            }
        }catch (Exception e) {
            System.out.println("GetVacancy");
            System.out.println(e.getClass());
            this.restTemplate = proxyRequestAPI.change();
            this.localTime = LocalTime.now();
            this.activateTimer = true;
        }
        return null;
    }

}

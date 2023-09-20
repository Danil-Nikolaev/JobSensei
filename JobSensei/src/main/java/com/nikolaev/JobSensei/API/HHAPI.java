package com.nikolaev.JobSensei.API;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nikolaev.JobSensei.converter.ConverterHH;

public class HHAPI extends JobAPI {

    ObjectMapper mapper;
    List<Map<String, Object>> listMap;

    public HHAPI() {
        super(new ConverterHH());
        this.mapper = new ObjectMapper();
        this.listMap = new ArrayList<>();
    }

    @Override
    protected String getAllJson(String profession) {
        int pages = getPages(profession);
        System.out.println(pages);
        String result = "Error";

        for (int page = 0; page < pages; page++) {
            String json = getJson(profession, String.valueOf(page));
            getVacancies(json);
        }

        try {
            result = this.mapper.writeValueAsString(this.listMap);
        } catch (Exception e) {
            System.out.println("Module - HHAPI, method - getAllJson");
            System.out.println("convert from json into String");
            System.out.println(e.getClass());
        }

        return result;

    }

    private void addJson(String json) {
        try {
            Map<String, Object> jsonMap = this.mapper.readValue(json, new TypeReference<Map<String, Object>>() {
            });
            this.listMap.add(jsonMap);
        } catch (Exception e) {
            System.out.println("Module - HHAPI, method - addJSon");
            System.out.println(e.getClass());
        }
    }

    private void getVacancies(String json) {
        try {
            List<Map<String, Object>> items = this.mapper.readValue(json, List.class);
            for (Map<String, Object> item : items) {
                String url = String.valueOf (item.get("url"));
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                System.out.println(url);
                addJson(getVacancy(url));
            }
        } catch (Exception e) {
            System.out.println("Module - HHAPI method - getVacancies");
            System.out.println(e.getClass());
        }
    }

    private String getJson(String profession, String page) {
        String apiUrl = "https://api.hh.ru/vacancies?per_page=100&search_field=name&text=" + profession + "&page=" + page;

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(apiUrl, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            String body = response.getBody();
            try {
                    Map<String, Object> BodyMap = this.mapper.readValue(body, new TypeReference<Map<String, Object>>() {
                    });
                    String itemsString = this.mapper.writeValueAsString(BodyMap.get("items"));
                    return itemsString;
                } catch (Exception e) {
                    System.out.println("Module - HHAPI, method - getJson");
                    System.out.println("Not find items or can`t convert");
                    System.out.println(e.getClass());
                }
        }
        return null;
    }

    private String getVacancy(String url) {
        try{
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
           
            if (response.getStatusCode() == HttpStatus.OK) {
                return response.getBody();
            }
        }catch (Exception e) {
            System.out.println("GetVacancy");
            System.out.println(e.getClass());
        }
        return null;
      

    }

    private String getJson(String profession) {
        String url = "https://api.hh.ru/vacancies?search_field=name&per_page=100&text=" + profession;
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        }
        return null;
    }

    private int getPages(String profession) {
        String json = getJson(profession);
        try {
            Map<String, Object> jsonMap = this.mapper.readValue(json, new TypeReference<Map<String, Object>>() {
            });
            return (int) jsonMap.get("pages");
        } catch (Exception e) {
            System.out.println("Module - HHAPI, method - getPages");
            System.out.println(e.getClass());
        }
        return -1;
    }

}

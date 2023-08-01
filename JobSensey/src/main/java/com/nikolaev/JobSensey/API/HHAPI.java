package com.nikolaev.JobSensey.API;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nikolaev.JobSensey.converter.ConverterHH;

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
                String url = String.valueOf(item.get("url"));
                addJson(getVacancy(url));
            }
        } catch (Exception e) {
            System.out.println("Module - HHAPI method - getVacancies");
            System.out.println(e.getClass());
        }
    }

    private String getJson(String profession, String page) {

        HttpClient httpClient = HttpClient.newHttpClient();
        String apiUrl = "https://api.hh.ru/vacancies?per_page=100&search_field=name&text=" + profession + "&page=" + page;

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(apiUrl))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                String body = response.body();
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

        } catch (URISyntaxException | IOException | InterruptedException exc) {
            System.out.println("Module - HHAPI, method - getJson");
            System.out.println(exc.getClass());
        }
        return null;
    }

    private String getVacancy(String url) {
        HttpClient httpClient = HttpClient.newHttpClient();
        String apiUrl = url;

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(apiUrl))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return response.body();
            }

        } catch (URISyntaxException | IOException | InterruptedException exc) {
            System.out.println("Module - HHAPI, method - getVacancy");
            System.out.println(exc.getClass());
        }
        return null;

    }

    private String getJson(String profession) {

        HttpClient httpClient = HttpClient.newHttpClient();
        String apiUrl = "https://api.hh.ru/vacancies?search_field=name&per_page=100&text=" + profession;

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(apiUrl))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200)
                return response.body();

        } catch (URISyntaxException | IOException | InterruptedException exc) {
            System.out.println("Module - HHAPI, method - getJson with one parametr");
            System.out.println(exc.getClass());
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

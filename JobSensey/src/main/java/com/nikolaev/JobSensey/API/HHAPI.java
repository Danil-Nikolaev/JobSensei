package com.nikolaev.JobSensey.API;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.nikolaev.JobSensey.converter.ConverterHH;

public class HHAPI extends JobAPI{

    public HHAPI() {
        super(new ConverterHH());
    }

    @Override
    protected String getAllJson(String profession) {
        // Здесь будет запросы к api с получением json

        HttpClient httpClient = HttpClient.newHttpClient();
        String apiUrl = "https://api.hh.ru/vacancies?text=" + profession;
        try {
             HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(apiUrl))
                .GET()
                .build();
        
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        
            if (response.statusCode() == 200) {
                String responseBody = response.body();
                return responseBody;
            }
        } catch (URISyntaxException exc) {

        } catch (IOException exc) {

        } catch (InterruptedException exc) {}
        
        return null;
    }
    
    private String getJson(String profession, int page) {return null;}
    
}

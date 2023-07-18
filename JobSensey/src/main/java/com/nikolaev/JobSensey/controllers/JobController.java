package com.nikolaev.JobSensey.controllers;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nikolaev.JobSensey.models.Job;
import com.nikolaev.JobSensey.services.JobService;

@RestController
public class JobController {

    private JobService jobService;

    public JobController(@Autowired JobService jobService) {
        this.jobService = jobService;
    }

    // Начальная страница
    @GetMapping("/")
    public String getIndex() {
        return "index";
    }

    // Отправка запроса для предоставления анализа профессии
    @GetMapping("/analyze")
    public Job getJob(@RequestParam String profession,@RequestParam String[] creteria) {
        return jobService.makeDesicion(profession, creteria);
    }

    @GetMapping("/test")
    public String getServer() {
         HttpClient httpClient = HttpClient.newHttpClient();
        String apiUrl = "http://127.0.0.1:8000/";
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
        return new String();
    }
}
package com.nikolaev.JobSensei.services;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.nikolaev.JobSensei.API.WorkWithAPI;
import com.nikolaev.JobSensei.models.Job;

@Service
public class Handler {

    // Даннный класс связывает анализатор и получения данных с помощью API

    private WorkWithAPI workWithAPI;

    public Handler(@Autowired WorkWithAPI workWithAPI) {
        this.workWithAPI = workWithAPI;
    }

    // Данный метод сначала запрашивает данные из api,
    // затем результат отправляет анализатору
    public Job analyzeProfession(String profession) {
        ArrayNode result = workWithAPI.getProfession(profession);
        String resultString = jsonNodeToString(result);
        resultString = sendToAnalyzer(resultString);
        System.out.println(resultString);
        return new Job();
    }

    // Отправляет json анализатору
    private String sendToAnalyzer(String json) {
        HttpClient httpClient = HttpClient.newHttpClient();
        String url = "http://localhost:8000";

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(url))
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .header("Content-type", "application/json")
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200)
                return response.body();

        } catch (URISyntaxException | IOException | InterruptedException exc) {
            System.out.println("Module - Handler, method - sendToAnalyzer");
            System.out.println(exc.getClass());
        }
        return null;
    }

    private String jsonNodeToString(JsonNode jsonNode) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(jsonNode);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
       return null;
    }

}

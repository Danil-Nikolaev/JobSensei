package com.nikolaev.JobSensei.API;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Service
public class WorkWithAPI {

    private ArrayList<JobAPI> arrayJobAPI;

    public WorkWithAPI() {

    }

    // Данный метод проходится по всем API запрашивает данные
    // Получая в результате json единого вида и складывая их друг с другом
    public String getProfession(String profession) {
        initializeArrayJob();

        ObjectMapper objectMapper = new ObjectMapper();

        ArrayNode jsonArray = objectMapper.createArrayNode();
        String res = "";

        for (JobAPI job : arrayJobAPI) {
            String json = job.getResultJson(profession);
            res = json;
            // try {
            //     ObjectNode jsonObject1 = objectMapper.readValue(json, ObjectNode.class);
            //     jsonArray.add(jsonObject1);
            // } catch (Exception e) {
            // }
        }

        arrayJobAPI = null;
        try {
            return objectMapper.writeValueAsString(res);
        } catch (Exception e) {
            System.out.println("Module - WorkWithAPI, method - get profession");
            System.out.println(e.getClass());
        }

        return null;
    }

    private void initializeArrayJob() {
        this.arrayJobAPI = new ArrayList<JobAPI>();
        this.arrayJobAPI.add(new HHAPI());
    }

}

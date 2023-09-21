package com.nikolaev.JobSensei.API;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

@Service
public class WorkWithAPI {

    private ArrayList<JobAPI> arrayJobAPI;

    public WorkWithAPI() {

    }

    // Данный метод проходится по всем API запрашивает данные
    // Получая в результате json единого вида и складывая их друг с другом
    public ArrayNode getProfession(String profession) {
        initializeArrayJob();

        ObjectMapper objectMapper = new ObjectMapper();

        ArrayNode resultArrayNode = objectMapper.createArrayNode();

        for (JobAPI job : arrayJobAPI) {
            ArrayNode jsonArrayNode = job.getResultJson(profession);
            resultArrayNode.addAll(jsonArrayNode);
        }

        arrayJobAPI = null;
        return resultArrayNode;
    }

    private void initializeArrayJob() {
        this.arrayJobAPI = new ArrayList<JobAPI>();
        this.arrayJobAPI.add(new HHAPI());
    }

}

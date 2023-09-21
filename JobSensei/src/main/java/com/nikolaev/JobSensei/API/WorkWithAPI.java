package com.nikolaev.JobSensei.API;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

@Service
public class WorkWithAPI {

    private ArrayList<JobAPI> arrayJobAPI;

    public WorkWithAPI(@Autowired HHAPI hhapi) {
        this.arrayJobAPI = new ArrayList<JobAPI>();
        this.arrayJobAPI.add(hhapi);
    }

    // Данный метод проходится по всем API запрашивает данные
    // Получая в результате json единого вида и складывая их друг с другом
    public ArrayNode getProfession(String profession) {

        ObjectMapper objectMapper = new ObjectMapper();

        ArrayNode resultArrayNode = objectMapper.createArrayNode();

        for (JobAPI job : arrayJobAPI) {
            ArrayNode jsonArrayNode = job.getResultJson(profession);
            resultArrayNode.addAll(jsonArrayNode);
        }

        return resultArrayNode;
    }


}

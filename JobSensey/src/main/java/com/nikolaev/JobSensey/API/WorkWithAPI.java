package com.nikolaev.JobSensey.API;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

@Service
public class WorkWithAPI {

    private ArrayList<JobAPI> arrayJobAPI;

    public WorkWithAPI() {
        this.arrayJobAPI = new ArrayList<JobAPI>();
        this.arrayJobAPI.add(new HHAPI());
    }

    // Данный метод проходится по всем API запрашивает данные
    // Получая в результате json единого вида и складывая их друг с другом
    public String getProfession(String profession) {

        String json = "";

        for (JobAPI job : arrayJobAPI) {
            json += job.getResultJson(profession);
        }

        return json;
    }
    
}

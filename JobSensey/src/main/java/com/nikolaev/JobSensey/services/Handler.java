package com.nikolaev.JobSensey.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nikolaev.JobSensey.API.WorkWithAPI;
import com.nikolaev.JobSensey.models.Job;

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
        String result = workWithAPI.getProfession(profession);
        result = sendToAnalyzer(result);
        return new Job();
    }

    // Отправляет json анализатору
    private String sendToAnalyzer(String json) {
        return null;
    }
    
}

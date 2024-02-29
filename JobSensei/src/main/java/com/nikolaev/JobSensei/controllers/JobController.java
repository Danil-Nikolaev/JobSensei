package com.nikolaev.JobSensei.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nikolaev.JobSensei.models.Job;
import com.nikolaev.JobSensei.services.JobService;

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

    @GetMapping("/some")
    public String getSome() {
        return " new some";
    }

    // Отправка запроса для предоставления анализа профессии
    @GetMapping("/analyze")
    public Job getJob(@RequestParam String profession, @RequestParam(required = false) String[] creteria) {
        return jobService.makeDesicion(profession, creteria);
    }
}
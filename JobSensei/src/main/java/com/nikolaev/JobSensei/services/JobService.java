package com.nikolaev.JobSensei.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nikolaev.JobSensei.models.Job;
import com.nikolaev.JobSensei.repositories.JobRepository;

@Service
public class JobService {
    // Класс для Бизнес - логики 

    private JobRepository jobRepository;
    private Handler handler;

    public JobService(@Autowired JobRepository jobRepository, @Autowired Handler handler) {
        this.jobRepository = jobRepository;
        this.handler = handler;
    }

    // если профессия найдена в базе данных то выводим, иначе вызываем handler 
    // для последующего анализа
    public Job makeDesicion(String profession, String[] creteria) {
        Optional<Job> result = jobRepository.findByProfession(profession);

        if (result.isEmpty()) {
            Job resultJob = handler.analyzeProfession(profession);
            jobRepository.save(resultJob);
            result = Optional.of(resultJob);
        }

        return result.orElse(null);
    }
}

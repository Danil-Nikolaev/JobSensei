package com.nikolaev.JobSensei.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.nikolaev.JobSensei.models.Job;

public interface JobRepository extends CrudRepository<Job, Long> {

    public Optional<Job> findByProfession(String profession);
    
}

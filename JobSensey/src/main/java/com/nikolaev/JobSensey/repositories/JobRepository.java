package com.nikolaev.JobSensey.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.nikolaev.JobSensey.models.Job;

public interface JobRepository extends CrudRepository<Job, Long> {

    public Optional<Job> findByProfession(String profession);
    
}

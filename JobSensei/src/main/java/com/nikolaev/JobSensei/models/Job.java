package com.nikolaev.JobSensei.models;


import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import com.fasterxml.jackson.databind.JsonNode;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
@Entity
@Table(name = "Job")
public class Job {
    
    @Id
    @GeneratedValue
    private Long id;

    private String profession;
    private String description;

    @JdbcTypeCode(SqlTypes.JSON)
    private JsonNode skills;
    private double averageSalary;

    @JdbcTypeCode(SqlTypes.JSON)
    private JsonNode cities;

    @JdbcTypeCode(SqlTypes.JSON)
    private JsonNode skillsByExperience;

    @JdbcTypeCode(SqlTypes.JSON)
    private JsonNode experiences;

    @JdbcTypeCode(SqlTypes.JSON)
    private JsonNode uniqueSkillsByExperience;

    public Job() {}

    public Job(Long id, String profession, String description, JsonNode skills, double salary, JsonNode cities, JsonNode skillsByExperience, JsonNode experiences, JsonNode uniqueSkillsByExperience) {
        this.id = id;
        this.profession = profession;
        this.description = description;
        this.skills = skills;
        this.averageSalary = salary;
        this.cities = cities;
        this.skillsByExperience = skillsByExperience;
        this.experiences = experiences;
        this.uniqueSkillsByExperience = uniqueSkillsByExperience;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfesstion(String profession) {
        this.profession = profession;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public JsonNode getSkills() {
        return skills;
    }

    public void setSkills(JsonNode skills) {
        this.skills = skills;
    }

    public double getAverageSalary() {
        return averageSalary;
    }

    public void setAverageSalary(double averageSalary) {
        this.averageSalary = averageSalary;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public JsonNode getCities() {
        return cities;
    }

    public void setCities(JsonNode cities) {
        this.cities = cities;
    }

    public JsonNode getSkillsByExperience() {
        return skillsByExperience;
    }

    public void setSkillsByExperience(JsonNode skillsByExperience) {
        this.skillsByExperience = skillsByExperience;
    }

    public JsonNode getExperiences() {
        return experiences;
    }

    public void setExperiences(JsonNode experiences) {
        this.experiences = experiences;
    }

    public JsonNode getUniqueSkillsByExperience() {
        return uniqueSkillsByExperience;
    }

    public void setUniqueSkillsByExperience(JsonNode uniqueSkillsByExperience) {
        this.uniqueSkillsByExperience = uniqueSkillsByExperience;
    }

    

}

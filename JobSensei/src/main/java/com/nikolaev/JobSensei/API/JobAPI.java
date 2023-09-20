package com.nikolaev.JobSensei.API;

import org.springframework.beans.factory.annotation.Autowired;

import com.nikolaev.JobSensei.converter.Converter;

public abstract class JobAPI {

    protected Converter converter;

    public JobAPI(@Autowired Converter converter) {
        this.converter = converter;
    }

    protected abstract String getAllJson(String profession);

    public String getResultJson(String profession) {
        String json = getAllJson(profession);
        json = this.converter.convert(json);
        return json;
    }
}

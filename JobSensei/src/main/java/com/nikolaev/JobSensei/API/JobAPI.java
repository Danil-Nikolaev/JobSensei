package com.nikolaev.JobSensei.API;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.nikolaev.JobSensei.converter.Converter;

public abstract class JobAPI {

    protected Converter converter;

    public JobAPI(@Autowired Converter converter) {
        this.converter = converter;
    }

    protected abstract ArrayNode getAllJson(String profession);

    public ArrayNode getResultJson(String profession) {
        ArrayNode json = getAllJson(profession);
        ArrayNode jsonArrayNode = this.converter.convert(json);
        return jsonArrayNode;
    }
}

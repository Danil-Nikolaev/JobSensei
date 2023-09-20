package com.nikolaev.JobSensei.converter;

public abstract class Converter {
    public String convert(String json) {
        String result = getInfo(json);
        return result;
    }

    protected abstract String getInfo(String json);
}

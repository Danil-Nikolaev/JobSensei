package com.nikolaev.JobSensey.converter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConverterHH extends Converter {

    @Override
    protected String getInfo(String json) {
        ObjectMapper mapper = new ObjectMapper();
        List<Map<String, Object>> resultListMap = new ArrayList<>();
        try {
            List<Map<String, Object>> items = mapper.readValue(json, new TypeReference<List<Map<String, Object>>>() {
            });
            for (Map<String, Object> item : items) {
                String name = getName(item);
                String description = getDescription(item);
                String salary = getSalary(item);
                String skills = getSkills(item);
                Map<String, Object> newJson = newItemResultJson(name, description, salary, skills);
                resultListMap.add(newJson);
            }
            return mapper.writeValueAsString(resultListMap);
        } catch (Exception e) {
            System.out.println("Module - ConverterHH, method - getInfo");
            System.out.println(e.getClass());
        }
        return null;
    }

    private Map<String, Object> newItemResultJson(String name, String description, String salary, String skills) {
        Map<String, Object> newJson = new HashMap<>();
        newJson.put("name", name);
        newJson.put("description", description);
        newJson.put("salary", salary);
        newJson.put("skills", skills);
        return newJson;
    }

    private String getName(Map<String, Object> item) {
        return String.valueOf(item.get("name"));
    }

    private String getDescription(Map<String, Object> item) {
        return String.valueOf(item.get("description"));
    }

    private String getSalary(Map<String, Object> item) {
        return String.valueOf(item.get("salary"));
    }

    private String getSkills(Map<String, Object> item) {
        return String.valueOf(item.get("key_skills"));
    }

}

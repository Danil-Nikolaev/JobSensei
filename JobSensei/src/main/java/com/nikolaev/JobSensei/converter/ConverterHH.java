package com.nikolaev.JobSensei.converter;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Component
public class ConverterHH extends Converter {

    @Override
    protected ArrayNode getInfo(ArrayNode json) {
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode arrayNode = mapper.createArrayNode();
        for (JsonNode jsonNode : json) {
            String name = getName(jsonNode);
            String description = getDescription(jsonNode);
            JsonNode salary = getSalary(jsonNode);
            JsonNode skills = getSkills(jsonNode);
            String city = getCity(jsonNode);
            String experience = getExperience(jsonNode);
            JsonNode jsonItem = newItemResultJson(name, description, salary, skills, city, experience);
            arrayNode.add(jsonItem);
        }
    
        return arrayNode;
    }

    private JsonNode newItemResultJson(String name, String description, JsonNode salary, JsonNode skills, String city, String experience) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode newJson = mapper.createObjectNode();
        newJson.put("name", name);
        newJson.put("description", description);
        newJson.set("salary", salary);
        newJson.set("skills", skills);
        newJson.put("city", city);
        newJson.put("experience", experience);
        return newJson;
    }

    private String getName(JsonNode item) {
        return item.get("name").asText();
    }

    private String getDescription(JsonNode item) {
        return item.get("description").asText();
    }

    private JsonNode getSalary(JsonNode item) {
        return item.get("salary");
    }

    private JsonNode getSkills(JsonNode item) {
        return item.get("key_skills");
    }

    private String getCity(JsonNode item) {
        return item.get("area").get("name").asText();
    }

    private String getExperience(JsonNode item) {
        return item.get("experience").get("id").asText();
    }

}

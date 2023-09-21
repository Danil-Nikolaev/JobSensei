package com.nikolaev.JobSensei.converter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

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
            JsonNode jsonItem = newItemResultJson(name, description, salary, skills);
            arrayNode.add(jsonItem);
        }
    
        return arrayNode;
    }

    private JsonNode newItemResultJson(String name, String description, JsonNode salary, JsonNode skills) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode newJson = mapper.createObjectNode();
        newJson.put("name", name);
        newJson.put("description", description);
        newJson.set("salary", salary);
        newJson.set("skills", skills);
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

}

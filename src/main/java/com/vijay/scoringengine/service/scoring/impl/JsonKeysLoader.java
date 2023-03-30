package com.vijay.scoringengine.service.scoring.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@Component
public class JsonKeysLoader {

    private Set<String> keys;

    @PostConstruct
    public void init() {
        try {
            keys = loadJsonKeys("example.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Set<String> getKeys() {
        return keys;
    }

    private Set<String> loadJsonKeys(String filename) throws IOException {
        Set<String> keys = new HashSet<>();
        ObjectMapper objectMapper = new ObjectMapper();

        ClassPathResource resource = new ClassPathResource(filename);
        try (InputStream inputStream = resource.getInputStream()) {
            JsonNode jsonNode = objectMapper.readTree(inputStream);
            collectKeys(jsonNode, keys);
        }

        return keys;
    }

    private void collectKeys(JsonNode jsonNode, Set<String> keys) {
        Iterator<String> fieldNames = jsonNode.fieldNames();
        while (fieldNames.hasNext()) {
            String fieldName = fieldNames.next();
            keys.add(fieldName);

            JsonNode fieldValue = jsonNode.get(fieldName);
            if (fieldValue.isObject()) {
                collectKeys(fieldValue, keys);
            } else if (fieldValue.isArray()) {
                for (JsonNode element : fieldValue) {
                    collectKeys(element, keys);
                }
            }
        }
    }
}

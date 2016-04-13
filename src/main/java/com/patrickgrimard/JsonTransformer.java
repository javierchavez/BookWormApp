package com.patrickgrimard;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 *
 */
public class JsonTransformer {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static String toJson(Object model) throws JsonProcessingException {
        return mapper.writeValueAsString(model);
    }
}

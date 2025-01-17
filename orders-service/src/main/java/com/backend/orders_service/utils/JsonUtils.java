package com.backend.orders_service.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Convierte un objeto a su representación JSON.
     *
     * @param object el objeto a convertir
     * @return la representación JSON del objeto
     */
    public static String toJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error al convertir a JSON", e);
        }
    }

    /**
     * Convierte una cadena JSON a un objeto de la clase especificada.
     *
     * @param json la cadena JSON a convertir
     * @param clazz la clase del objeto a devolver
     * @param <T> el tipo del objeto
     * @return el objeto convertido
     */
    public static <T> T fromJson(String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error al convertir de JSON", e);
        }
    }
}

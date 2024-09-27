package hbv501g.recipes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import hbv501g.recipes.Persistence.Entities.IngredientMeasurement;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class IngredientMeasurementAttributeConverter implements AttributeConverter<IngredientMeasurement,String> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(IngredientMeasurement ingredientMeasurement) {
        try {
            return objectMapper.writeValueAsString(ingredientMeasurement);
        } catch (JsonProcessingException jpe) {
            //log.warn("Cannot convert Address into JSON");
            return null;
        }
    }

    @Override
    public IngredientMeasurement convertToEntityAttribute(String value) {
        try {
            return objectMapper.readValue(value, IngredientMeasurement.class);
        } catch (JsonProcessingException e) {
            //log.warn("Cannot convert JSON into Address");
            return null;
        }
    }
}
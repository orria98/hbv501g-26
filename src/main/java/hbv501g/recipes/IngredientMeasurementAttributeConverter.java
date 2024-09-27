package hbv501g.recipes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import hbv501g.recipes.Persistence.Entities.IngredientMeasurement;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Til að nota java object í entity án þess að geyma í töflu
 */
@Converter
public class IngredientMeasurementAttributeConverter implements AttributeConverter<IngredientMeasurement, String> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(IngredientMeasurement ingredientMeasurement) {
        try {
            return objectMapper.writeValueAsString(ingredientMeasurement);
        } catch (JsonProcessingException jpe) {
            System.out.println("Cannot convert IngredientMeasurement into JSON");
            // log.warn("Cannot convert Address into JSON");
            return null;
        }
    }

    @Override
    public IngredientMeasurement convertToEntityAttribute(String value) {
        try {
            return objectMapper.readValue(value, IngredientMeasurement.class);
        } catch (JsonProcessingException e) {
            System.out.println("Cannot convert JSON into IngredientMeasurement");

            // log.warn("Cannot convert JSON into Address");
            return null;
        }
    }
}
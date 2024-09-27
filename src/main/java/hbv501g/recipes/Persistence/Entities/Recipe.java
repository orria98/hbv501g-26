package hbv501g.recipes.Persistence.Entities;

import java.util.Date;
import java.util.List;


import hbv501g.recipes.IngredientMeasurementAttributeConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "recipes")
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;

    private String title;
    private String instructions;
    private boolean isPrivate;
    @ManyToOne(fetch = FetchType.LAZY)
    private User createdBy;
    private Date dateOfCreation;

    // @ManyToOne
    // private List<Triple<Ingredient, Unit, Double>> ingredientSets;

    // @ManyToMany
    // private List<IngredientMeasurement> pantry;

    @Convert(converter = IngredientMeasurementAttributeConverter.class)
    @Column(name = "measurement", length = 500)
    private IngredientMeasurement ingredientMeasurement;

    private double price;
    private String store;
    private String brand;

}

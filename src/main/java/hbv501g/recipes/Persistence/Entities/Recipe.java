package hbv501g.recipes.Persistence.Entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import hbv501g.recipes.IngredientMeasurementAttributeConverter;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

    // @Convert(converter = IngredientMeasurementAttributeConverter.class)
    // @Column(name = "measurement", length = 500)
    // private IngredientMeasurement ingredientMeasurement;

    @Convert(converter = IngredientMeasurementAttributeConverter.class)
    private List<IngredientMeasurement> ingredientMeasurements;


    public Recipe(){
        ingredientMeasurements = new ArrayList<IngredientMeasurement>();
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getInstructions() {
        return instructions;
    }
    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }
    public boolean isPrivate() {
        return isPrivate;
    }
    public void setPrivate(boolean isPrivate) {
        this.isPrivate = isPrivate;
    }
    public User getCreatedBy() {
        return createdBy;
    }
    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }
    public Date getDateOfCreation() {
        return dateOfCreation;
    }
    public void setDateOfCreation(Date dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }
    // public IngredientMeasurement getIngredientMeasurement() {
    //     return ingredientMeasurement;
    // }
    // public void setIngredientMeasurement(IngredientMeasurement ingredientMeasurement) {
    //     this.ingredientMeasurement = ingredientMeasurement;
    // }

        public List<IngredientMeasurement> getIngredientMeasurements() {
        return ingredientMeasurements;
    }
    public void setIngredientMeasurement(List<IngredientMeasurement> ingredientMeasurements) {
        this.ingredientMeasurements = ingredientMeasurements;
    }

    public void addIngredientMeasurement(IngredientMeasurement ingredientMeasurement){
        ingredientMeasurements.add(ingredientMeasurement);
    }


    

}

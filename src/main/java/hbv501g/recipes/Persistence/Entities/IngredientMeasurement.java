package hbv501g.recipes.Persistence.Entities;


import com.fasterxml.jackson.annotation.JsonIdentityReference;

import jakarta.persistence.Embeddable;
import jakarta.persistence.ManyToOne;

@Embeddable
public class IngredientMeasurement {

    

    //@Column
    //private Ingredient ingredient;
    //private Long ingredientID;

    //@OneToMany
    //@JoinColumn(name = "Recipe_ID")
    //private List<Ingredient> ingredients;
    @ManyToOne
    @JsonIdentityReference
    private Ingredient ingredient; // TODO: aðeins að skoða hvernig þetta er

    //@Column
    private Unit unit;

    //@Column
    private double quantity;

    public IngredientMeasurement(){

    }

    public IngredientMeasurement(Ingredient ingredient, Unit unit, double quantity) {
        this.ingredient = ingredient;
        this.unit = unit;
        this.quantity = quantity;
    }

    public Ingredient getIngredientID() {
        return ingredient;
    }

    public void setIngredientID(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

}
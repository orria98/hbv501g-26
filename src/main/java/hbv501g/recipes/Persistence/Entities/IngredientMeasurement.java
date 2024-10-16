package hbv501g.recipes.Persistence.Entities;


import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class IngredientMeasurement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;

    

    //@Column
    //private Ingredient ingredient;
    //private Long ingredientID;

    //@OneToMany
    //@JoinColumn(name = "Recipe_ID")
    //private List<Ingredient> ingredients;
    @ManyToOne //(fetch = FetchType.LAZY)
    // @JsonIdentityReference
    @JsonIgnore
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
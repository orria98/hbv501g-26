package hbv501g.recipes.Persistence.Entities;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

/**
 * Klasinn heldur utan um sett af ingredient, unit og quantity, sem er notað í
 * ingredient lista í recipe og pantry hjá user.
 * 
 * Klasinn er embeddable þó hann sé hvergi embedded, en er í staðinn notaður í
 * elementCollection á tveimur stöðum í mismunandi töflum.
 */
@Embeddable
public class IngredientMeasurement implements Serializable {

    @ManyToOne
    @JoinColumn(name = "ingredient_id") // id í ingredient notað í töflu fyrir ingredient measurement
    private Ingredient ingredient;

    private Unit unit;

    private double quantity;

    public IngredientMeasurement() {

    }

    public IngredientMeasurement(Ingredient ingredient, Unit unit, double quantity) {
        this.ingredient = ingredient;
        this.unit = unit;
        this.quantity = quantity;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
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

    @Override
    public String toString() {
        return "IngredientMeasurement [ingredient=" + ingredient + ", unit=" + unit + ", quantity=" + quantity + "]";
    }

}
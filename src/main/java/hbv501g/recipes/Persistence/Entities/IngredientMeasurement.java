package hbv501g.recipes.Persistence.Entities;



public class IngredientMeasurement {

    private Ingredient ingredient;
    private Unit unit;
    private double quantity;

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

}
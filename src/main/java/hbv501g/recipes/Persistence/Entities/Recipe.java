package hbv501g.recipes.Persistence.Entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
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
    private long ID;

    private String title;
    private String instructions;
    private boolean isPrivate;
    private LocalDate dateOfCreation;
    /** Total price for all groceries bought new */
    private int totalPurchaseCost;
    /** price for quantity of each ingredient used */
    private double totalIngredientCost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIncludeProperties(value = { "id", "username" }) // properties úr user til að birta í json fyrir recipe
    private User createdBy;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "recipe_ingredients")
    private List<IngredientMeasurement> ingredientMeasurements = new ArrayList<>();

    public Recipe() {
    }

    public Recipe(String title, User user) {
        this.title = title;
        this.createdBy = user;
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

    public LocalDate getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(LocalDate dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public List<IngredientMeasurement> getIngredientMeasurements() {
        return ingredientMeasurements;
    }

    /**
     * Sets the ingredientMeasurements of a recipe and calculates the total purchase cost and the total ingredient cost
     * @param ingredientMeasurements
     */
    public void setIngredientMeasurements(List<IngredientMeasurement> ingredientMeasurements) {
        this.ingredientMeasurements = ingredientMeasurements;

        totalPurchaseCost = 0;
        totalIngredientCost = 0;
        for (IngredientMeasurement item : ingredientMeasurements) {
            addMeasurementToCost(item);
        }
    }

    /**
     * Adds cost of an ingredient measurement to the cost of a recipe.
     * TotalIngredientCost margfaldar heildarverð með hlutfalli
     * 
     * @param item Ingredient measurement
     */
    private void addMeasurementToCost(IngredientMeasurement item) {
        Ingredient ingredient = item.getIngredient();
        if (ingredient == null)
            return;
        double ingredientPrice = ingredient.getPrice();

        if (ingredient.getQuantity()!=0 && ingredient.getQuantityInMl()!=0){
            double measurementAmount = item.getQuantityInMl()/ingredient.getQuantityInMl();            
            totalPurchaseCost +=ingredientPrice* Math.ceil(measurementAmount);
            totalIngredientCost += ingredientPrice * measurementAmount;
        }
    }

    public void addIngredientMeasurement(IngredientMeasurement ingredientMeasurement) {
        ingredientMeasurements.add(ingredientMeasurement);
        addMeasurementToCost(ingredientMeasurement);

    }

    public long getID() {
        return ID;
    }

    public int getTotalPurchaseCost() {
        return totalPurchaseCost;
    }

    public void setTotalPurchaseCost(int totalPurchaseCost) {
        this.totalPurchaseCost = totalPurchaseCost;
    }

    public double getTotalIngredientCost() {
        return totalIngredientCost;
    }

    public void setTotalIngredientCost(double totalIngredientCost) {
        this.totalIngredientCost = totalIngredientCost;
    }

    /**
     * Til að hafa aðgang að nafni notanda í framenda, nafn fylgir í json
     * @return nafn notanda sem gerði uppskrift
     */
    public String getRecipeCreator(){
        return createdBy== null ? "" : createdBy.getUsername();
    }

}

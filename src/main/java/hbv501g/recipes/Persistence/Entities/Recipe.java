package hbv501g.recipes.Persistence.Entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
    private Date dateOfCreation;
    /** Total price for all groceries bought new */
    private int totalPurchaseCost;
    /** price for quantity of each ingredient used */
    private double totalIngredientCost;


    // @ManyToOne(fetch = FetchType.LAZY)

    @ManyToOne //(fetch = FetchType.LAZY)
    //@JsonIgnore // Til að koma í veg fyrir að sýna recipes undir user undir recipes...
    //@JsonBackReference // í staðinn fyrir jsonIgnore
    private User createdBy;

    // TODO: skoða þetta betur
    //@ElementCollection //(fetch = FetchType.LAZY)
    @OneToMany
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

    public Date getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(Date dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public List<IngredientMeasurement> getIngredientMeasurements() {
        return ingredientMeasurements;
    }

    public void setIngredientMeasurements(List<IngredientMeasurement> ingredientMeasurements) {
        this.ingredientMeasurements = ingredientMeasurements;
    }

    public void addIngredientMeasurement(IngredientMeasurement ingredientMeasurement) {
        ingredientMeasurements.add(ingredientMeasurement);
    }

    public Long getID() {
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

}

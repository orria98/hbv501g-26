/**
 * Entity klasi. Getur staðið einn sem java object, en 
 * útgáfa af þessum object fer í gagnagrunninn
 */

package hbv501g.recipes.Persistence.Entities;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "ingredients")
public class Ingredient {

    // ID er sjálfkrafa búið til þegar object er gerður
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ID;

    private String title;
    private Enum<Unit> unit;
    private double quantity;
    private double price;
    private String store;
    private String brand;
    private boolean isPrivate;
    private Date dateOfCreation;

    // Hver user getur verið á fleiri ingredients, en alltaf bara einn user á hverju
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIncludeProperties(value = {"id", "username"}) // properties úr user til að birta í json fyrir ingredient
    private User createdBy; // Bara til að geyma hver gerði ingredientið


    /**
     * Constructs an Ingredient object
     */
    public Ingredient(String title, Enum<Unit> unit, double quantity, double price) {
        this.title = title;
        this.unit = unit;
        this.quantity = quantity;
        this.price = price;
    }

    /**
     * Constructs an Ingredient object
     */
    public Ingredient(String title, Enum<Unit> unit, double quantity, double price, String store, String brand) {
        this.title = title;
        this.unit = unit;
        this.quantity = quantity;
        this.price = price;
        this.store = store;
        this.brand = brand;
    }

    /**
     * Constructs an Ingredient object with null values and a non-null id
     */
    public Ingredient() {

    }

    // Getters and setters

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Enum<Unit> getUnit() {
        return unit;
    }

    public void setUnit(Enum<Unit> unit) {
        this.unit = unit;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

    public Date getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(Date dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public long getID() {
        return ID;

    }

    // Custom toString aðferð
    @Override
    public String toString() {
        return "Ingredient [id:" + ID + ", title=" + title + ", " + quantity + " " + unit + ", " + price + "kr." + "]";
    }

}

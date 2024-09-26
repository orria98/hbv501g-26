/**
 * Entity klasi. Getur staðið einn sem java object, en 
 * útgáfa af þessum object fer í gagnagrunninn
 */

package hbv501g.recipes.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import hbv501g.recipes.enums.*;

@Entity
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Enum<Unit> unit;
    private double quantity;
    private double price;
    private String store;
    private String brand;

    /**
     * Constructs an Ingredient object
     * 
     * @param name:    an ingredient name
     * @param unit:    unit of measure
     * @param quantity
     * @param price
     */
    public Ingredient(String name, Enum<Unit> unit, double quantity, double price) {
        this.name = name;
        this.unit = unit;
        this.quantity = quantity;
        this.price = price;
    }

    /**
     * Constructs an Ingredient object
     * 
     * @param name:    an ingredient name
     * @param unit:    unit of measure
     * @param quantity  
     * @param price: total price of this amount of the ingredient
     * @param store: the store where this price was found
     * @param brand: company name or branding
     */
    public Ingredient(String name, Enum<Unit> unit, double quantity, double price, String store, String brand) {
        this.name = name;
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
    public void setName(String name) {
        this.name = name;
    }

    public void setUnit(Enum<Unit> unit) {
        this.unit = unit;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public void SetBrand(String brand) {
        this.brand = brand;
    }

    public String getName() {
        return this.name;
    }

    public Enum<Unit> getUnit() {
        return this.unit;
    }

    public double getQuantity() {
        return this.quantity;
    }

    public double getPrice() {
        return this.price;
    }

    public Long getId() {
        return this.id;
    }

    public String getStore() {
        return this.store;
    }

    public String getBrand() {
        return this.brand;
    }

    // Customizable toString
    @Override
    public String toString() {
        return "Ingredient [id:" + id + ", name=" + name + ", " + quantity + " " + unit + ", " + price + "kr." + "]";
    }

}

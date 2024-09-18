/**
 * Entity klasi (basically object). Þetta er það sem fer í gagnagrunninn.
 * 
 */

package hbv501g.recipes;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Ingredient {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String unit;
    private double quantity;
    private double price;

    /**
     * Constructs an Ingredient object
     * @param name an ingredient name
     * @param unit
     * @param quantity
     * @param price
     */
    public Ingredient(String name, String unit, double quantity, double price){
        this.name = name;
        this.unit = unit;
        this.quantity = quantity;
        this.price = price;
    }

    /**
     * Constructs an Ingredient object with null values and a non-null id
     */
    public Ingredient(){
        
    }

    // Getters and setters
    public void setName(String name){
        this.name = name;
    }
    public void setUnit(String unit){
        this.unit = unit;
    }
    public void setQuantity(double quantity){
        this.quantity = quantity;
    }
    public void setPrice(double price){
        this.price = price;
    }

    public String getName(){
        return this.name;
    }
    public String getUnit(){
        return this.unit;
    }
    public double getQuantity(){
        return this.quantity;
    }
    public double getPrice(){
        return this.price;
    }

    // Customizable toString
    @Override
    public String toString() {
        return "Ingredient [id=" + id + ", name=" + name + ", unit=" + unit + ", quantity=" + quantity + ", price="
                + price + "]";
    }

    
}

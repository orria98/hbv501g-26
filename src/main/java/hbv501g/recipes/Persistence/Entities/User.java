package hbv501g.recipes.Persistence.Entities;


import java.util.ArrayList;
import java.util.List;

import hbv501g.recipes.IngredientMeasurementAttributeConverter;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;

    private String username;
    private String password;
    private String email;

    @Convert(converter = IngredientMeasurementAttributeConverter.class)
    private List<IngredientMeasurement> pantry;

    public User(){
        pantry = new ArrayList<IngredientMeasurement>();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<IngredientMeasurement> getPantry() {
        return pantry;
    }

    public void setPantry(List<IngredientMeasurement> pantry) {
        this.pantry = pantry;
    }

    
}

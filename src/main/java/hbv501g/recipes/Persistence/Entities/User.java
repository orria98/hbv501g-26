package hbv501g.recipes.Persistence.Entities;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;



import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * A Java object for the user. Pantry has not been implemented.
 * The user entity was created so that the Ingredient entity could be fully made.
 */
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;

    private String username;
    private String password;
    private String email;

    //@ElementCollection //(fetch = FetchType.LAZY)
    //@CollectionTable(name = "ingredient_measurements")
    @OneToMany
    @JoinColumn(name = "recipeId")
    private List<IngredientMeasurement> pantry = new ArrayList<>();

    // TODO: passa hvernig þetta birtist í json
    @OneToMany(mappedBy = "createdBy" , cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnore //needed to avoid infinite recursion
    //@JsonManagedReference
    private List<Recipe> recipesByUser = new ArrayList<>();


    // TODO: passa hvernig þetta birtist í json
    @OneToMany(mappedBy = "createdBy" , cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnore //needed to avoid infinite recursion
    private List<Ingredient> ingredientsByUser = new ArrayList<>(); // Geymir hver gerði ingredientið



   

    public User() {

    }

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;

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

    public List<Recipe> getRecipesByUser() {
        return recipesByUser;
    }

    public void setRecipesByUser(List<Recipe> recipesByUser) {
        this.recipesByUser = recipesByUser;
    }

    public Long getID() {
        return ID;
    }

    public void addIngredientMeasurement(IngredientMeasurement ingredientMeasurement){
        pantry.add(ingredientMeasurement);
    }
    public void addRecipeByUser(Recipe recipeByUser){
        recipesByUser.add(recipeByUser);
    }

    public List<Ingredient> getIngredientsByUser() {
        return ingredientsByUser;
    }

    public void setIngredientsByUser(List<Ingredient> ingredientsByUser) {
        this.ingredientsByUser = ingredientsByUser;
    }

    


}

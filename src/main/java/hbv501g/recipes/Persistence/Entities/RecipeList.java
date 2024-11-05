package hbv501g.recipes.Persistence.Entities;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/*
 * Entity for recipe lists. 
 * Tengitafla er gerð með id fyrir recipe og lista
 */
@Entity
@Table(name = "recipe_lists")
public class RecipeList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIncludeProperties(value = { "id", "username" })
    private User createdBy;

    private String title;

    private String description;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIncludeProperties(value = { "id", "title", "instructions", "recipeCreator"})
    private List<Recipe> recipes = new ArrayList<>();
    private boolean isPrivate;

    public RecipeList() {

    }

    public RecipeList(User createdBy, String title, String description, boolean isPrivate) {
        this.createdBy = createdBy;
        this.title = title;
        this.description = description;
        this.isPrivate = isPrivate;
    }

    public long getID() {
        return ID;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }

    /**
     * If the same recipe exists in the list, nothing happens
     * 
     * @param recipe - the recipe to add
     */
    public void addRecipe(Recipe recipe) {
        if (!recipes.contains(recipe))
            recipes.add(recipe);
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

}

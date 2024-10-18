package hbv501g.recipes.Services;

import java.util.List;

import hbv501g.recipes.Persistence.Entities.Recipe;

public interface RecipeService {
    List<Recipe> initRecipes();
    
    List<Recipe> findAll();

    Recipe findByID(long id);

    Recipe save(Recipe recipe);

    Recipe update(Recipe recipe);

 

    
}

package hbv501g.recipes.Services;

import java.util.List;

import hbv501g.recipes.Persistence.Entities.Recipe;
import hbv501g.recipes.Persistence.Entities.User;

public interface RecipeService {
    List<Recipe> initRecipes();

    List<Recipe> findAll();

    Recipe findByID(long id);

    Recipe save(Recipe recipe);

    Recipe update(Recipe recipe);

    void deleteById(long id);

    int getTotalPurchaseCost(long id);

    double getTotalIngredientCost(long id);

    double getPersonalizedPurchaseCost(User user, long recipeId);

}

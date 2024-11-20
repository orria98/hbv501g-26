package hbv501g.recipes.Services;

import java.util.List;

import hbv501g.recipes.Persistence.Entities.Recipe;
import hbv501g.recipes.Persistence.Entities.Unit;
import hbv501g.recipes.Persistence.Entities.User;

public interface RecipeService {
    List<Recipe> initRecipes();

    List<Recipe> findAll();

    Recipe findByID(long id);

    Recipe findAccessibleByID(long id, long uid);

    List<Recipe> findByTitleContaining(long uid, String searchTerm);

    List<Recipe> findAccessibleToUser(long uid);

    Recipe save(Recipe recipe);

    Recipe update(Recipe recipe);

    void deleteById(long uid, long id);

    int getTotalPurchaseCost(long uid, long id);

    double getTotalIngredientCost(long uid,long id);

    double getPersonalizedPurchaseCost(long uid, long recipeId);

    Recipe addIngredients(long userID, long recipeID, List<Long> ingredientIDs, List<Double> qty, List<Unit> units);

    Recipe setRecipeAuthorAndDate(Recipe recipe, long uid);

    Recipe updateRecipeDetails(long id, Recipe updatedRecipe);

    List<Recipe> findUnderTPC(int upperLimit, long uid);

    List<Recipe> findUnderTIC(int upperLimit, long uid);

    List<Recipe> findOrderedRecipes(long uid);
}

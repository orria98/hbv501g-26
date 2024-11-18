package hbv501g.recipes.Services;

import java.util.List;

import hbv501g.recipes.Persistence.Entities.Recipe;
import hbv501g.recipes.Persistence.Entities.Unit;
import hbv501g.recipes.Persistence.Entities.User;

public interface RecipeService {
    List<Recipe> initRecipes();

    List<Recipe> findAll();

    Recipe findByID(long id);

    Recipe findAccessibleByID(long id, User user);

    List<Recipe> findByTitleContaining(User user, String searchTerm);

    List<Recipe> findAccessibleToUser(User user);

    Recipe save(Recipe recipe);

    Recipe update(Recipe recipe);

    void deleteById(User user, long id);

    int getTotalPurchaseCost(User user, long id);

    double getTotalIngredientCost(User user,long id);

    double getPersonalizedPurchaseCost(User user, long recipeId);

    Recipe addIngredients(long userID, long recipeID, List<Long> ingredientIDs, List<Double> qty, List<Unit> units);

    Recipe setRecipeAuthorAndDate(Recipe recipe, User author);

    Recipe updateRecipeDetails(long id, Recipe updatedRecipe);

    List<Recipe> findUnderTPC(int upperLimit, User user);

    List<Recipe> findUnderTIC(int upperLimit, User user);

    List<Recipe> findOrderedRecipes(User user);
    List<Recipe> findOrderedRecipesByTitle(User user);

    
}

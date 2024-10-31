package hbv501g.recipes.Services;

import java.util.List;

import hbv501g.recipes.Persistence.Entities.Recipe;
import hbv501g.recipes.Persistence.Entities.Unit;
import hbv501g.recipes.Persistence.Entities.User;

public interface RecipeService {
    List<Recipe> initRecipes();

    List<Recipe> findAll();

    List<Recipe> listAll();

    List<Recipe> listById(long id);

    Recipe findByID(long id);

    Recipe save(Recipe recipe);

    Recipe update(Recipe recipe);

    void deleteById(long id);

    void deleteList(long id);

    List<Recipe> removeRecipesListByID(long userID, long resID);

    int getTotalPurchaseCost(long id);

    double getTotalIngredientCost(long id);

    double getPersonalizedPurchaseCost(User user, long recipeId);

    Recipe addIngredients(long userID,long recipeID, List<Long> ingredientIDs, List<Double> qty,List<Unit> units );
    
    Recipe setRecipeAuthorAndDate(Recipe recipe, User author);
}

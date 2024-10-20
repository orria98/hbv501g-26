package hbv501g.recipes.Services;

import java.util.List;
import java.util.Date;

import hbv501g.recipes.Persistence.Entities.IngredientMeasurement;
import hbv501g.recipes.Persistence.Entities.Recipe;
import hbv501g.recipes.Persistence.Entities.Unit;
import hbv501g.recipes.Persistence.Entities.User;
import hbv501g.recipes.Persistence.Entities.User;

public interface RecipeService {
    List<Recipe> initRecipes();

    List<Recipe> findAll();

    Recipe findByID(long id);

    Recipe save(Recipe recipe);

    Recipe update(Recipe recipe);

    int getTotalPurchaseCost(long id);

    double getTotalIngredientCost(long id);

    double getPersonalizedPurchaseCost(User user, long recipeId);

    Recipe addIngredients(long recipeID, List<Long> ingredientIDs, List<Double> qty,List<Unit> units );
    Recipe setRecipeAuthorAndDate(Recipe recipe, User author);
}

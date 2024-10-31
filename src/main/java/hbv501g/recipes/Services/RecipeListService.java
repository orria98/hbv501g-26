package hbv501g.recipes.Services;

import java.util.List;

import hbv501g.recipes.Persistence.Entities.RecipeList;
import hbv501g.recipes.Persistence.Entities.Recipe;
import hbv501g.recipes.Persistence.Entities.User;

public interface RecipeListService {
    List<RecipeList> findAll();

    List<RecipeList> getAllRecipeLists(User user);

    List<RecipeList> findAllUserRecipeList(long id);
    
    RecipeList findByID(User user, long id);

    RecipeList save(User user, String title, String description, boolean isPrivate);

    RecipeList addRecipe(long recipeID, long listID, User user);

    Recipe getRecipeFromID(User user, long id, long recipeID);
}

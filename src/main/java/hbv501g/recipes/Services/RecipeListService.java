package hbv501g.recipes.Services;

import java.util.List;

import hbv501g.recipes.Persistence.Entities.RecipeList;
import hbv501g.recipes.Persistence.Entities.Recipe;
import hbv501g.recipes.Persistence.Entities.User;

public interface RecipeListService {
    List<RecipeList> findAll(User user);

    List<RecipeList> findAllUserRecipeLists(User user, long id);
    
    RecipeList findByID(User user, long id);

    RecipeList save(User user, String title, String description, boolean isPrivate);

    RecipeList updateTitle(User user, String newTitle, long id);

    RecipeList addRecipe(long recipeID, long listID, User user);

    List<Recipe> getAllRecipeFromID(User user, long id);

    void deletByID(User user, long id);

    RecipeList removeRecipeFromID(User user, long id, long recipeID);
}

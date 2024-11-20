package hbv501g.recipes.Services;

import java.util.List;

import hbv501g.recipes.Persistence.Entities.RecipeList;
import hbv501g.recipes.Persistence.Entities.Recipe;

public interface RecipeListService {
    List<RecipeList> findAll(long uid);

    List<RecipeList> findAllUserRecipeLists(long uid, long id);

    RecipeList findByID(long uid, long id);

    RecipeList save(long uid, String title, String description, boolean isPrivate);

    RecipeList updateTitle(long uid, String newTitle, long id);

    RecipeList addRecipe(long recipeID, long listID, long uid);

    List<Recipe> getAllRecipeFromID(long uid, long id);

    void deletByID(long uid, long id);

    RecipeList removeRecipeFromID(long uid, long id, long recipeID);
}

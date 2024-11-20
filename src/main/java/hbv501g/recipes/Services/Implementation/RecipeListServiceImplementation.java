package hbv501g.recipes.Services.Implementation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import hbv501g.recipes.Persistence.Entities.Recipe;
import hbv501g.recipes.Persistence.Entities.RecipeList;
import hbv501g.recipes.Persistence.Entities.User;
import hbv501g.recipes.Persistence.Repositories.RecipeListRepository;
import hbv501g.recipes.Services.RecipeListService;
import hbv501g.recipes.Services.RecipeService;
import hbv501g.recipes.Services.UserService;

@Service
public class RecipeListServiceImplementation implements RecipeListService {
    private RecipeListRepository recipeListRepository;
    private RecipeService recipeService;
    private UserService userService;

    // @Autowired
    public RecipeListServiceImplementation(RecipeListRepository recipeListRepository, RecipeService recipeService,
            UserService userService) {
        this.recipeListRepository = recipeListRepository;
        this.recipeService = recipeService;
        this.userService = userService;
    }

    /**
     * Finds and returns all public recipe lists by the user provided
     * 
     * @param user - the user who's public recipe lists are returned
     * @return the public recipe lists of the provided user
     */
    public List<RecipeList> findPublicRecipeListsByUser(long uid) {
        User user = userService.findByID(uid);
        return recipeListRepository.findByIsPrivateFalseAndCreatedBy(user);
    }

    /**
     * Get all RecipeList from the database that are not
     * privat unless the user owns them.
     *
     * @param user : the user requesting the recipes
     * @return a list of RecipeList.
     */
    public List<RecipeList> findAll(long uid) {
        User user = userService.findByID(uid);
        if (user == null) {
            return recipeListRepository.findByIsPrivateFalse();
        }
        return recipeListRepository.findAllAccessible(user);
    }

    /**
     * Get all recipeLists made by the user with the given id, which are accessible
     * to the user requesting the recipeLists
     * 
     * @param user : the user requesting the recipe lists
     * @param id   : the id of the user who's lists are being requested
     * @return All of the recipeLists from the owner which the requester can access
     */
    public List<RecipeList> findAllUserRecipeLists(long uid, long id) {
        User user = userService.findByID(uid);
        User owner = userService.findByID(id);
        if (owner == null) {
            return null;
        }

        if (user == null) {
            return recipeListRepository.findByIsPrivateFalseAndCreatedBy(owner);
        }

        return recipeListRepository.findAllAccessibleByUser(user, owner);
    }

    /**
     * Find and returns a RecipeList.
     * 
     * @param user : is the user that is loged in.
     * @param id   : the id valu of RecipeList
     * @return the recipeList whit the Id valu
     */
    public RecipeList findByID(long uid, long id) {
        User user = userService.findByID(uid);
        if (user != null) {
            return recipeListRepository.findAccessibleById(user, id);
        }
        return recipeListRepository.findByIsPrivateFalseAndID(id);
    }

    /**
     * A new list cannot be created without a user logged in
     * 
     * @param user        - the current user
     * @param title       - title of the new list
     * @param description - optional description for the list
     * @param isPrivate   - optionally declare whether the list is private. false by
     *                    default.
     */
    public RecipeList save(long uid, String title, String description, boolean isPrivate) {
        User user = userService.findByID(uid);
        if (user == null)
            return null;
        return recipeListRepository.save(new RecipeList(user, title, description, isPrivate));
    }

    /**
     * Updates the title of a recipeList, if it belongs to the given user
     * 
     * @param user     - the user who is changing the title
     * @param newTitle - the new title
     * @param id       - the id of the recipelist to be changed
     * @return the updated recipelist, if the change was successful, otherwise null
     */
    public RecipeList updateTitle(long uid, String newTitle, long id) {
        User user = userService.findByID(uid);
        RecipeList recipeList = findByID(uid, id);
        if (recipeList == null || user == null || recipeList.getCreatedBy() == null
                || recipeList.getCreatedBy().getID() != user.getID()) {
            return null;
        }

        recipeList.setTitle(newTitle);

        return recipeListRepository.save(recipeList);
    }

    /**
     * Adds the recipe specified to the given list, if both exists, the list
     * owner is logged in and can access the recipe
     * 
     * @param recipeID - the id of the Recipe
     * @param listID   - the id of the RecipeList
     * @param user     - the current User
     * @return the recipeList with the added recipe (if applicable)
     */
    public RecipeList addRecipe(long recipeID, long listID, long uid) {
        User user = userService.findByID(uid);
        if (user == null) {
            return null;
        }

        Recipe recipe = recipeService.findAccessibleByID(recipeID, uid);
        RecipeList list = findByID(uid, listID);

        if (recipe == null || list == null || list.getCreatedBy().getID() != user.getID())
            return null;

        if (list.getRecipes().contains(recipe))
            return list;

        list.addRecipe(recipe);

        return recipeListRepository.save(list);
    }

    /**
     * Find and get a resipe form a recipeList.
     *
     * @param user     - is the user that is the sesson
     * @param id       - is the ID value of a recipieList
     * @param recipeID - is the ID value of a recipe
     * @return The recipe if it is in the recipieList
     */
    public List<Recipe> getAllRecipesFromID(long uid, long id) {
        User user = userService.findByID(uid);
        if (user == null)
            return recipeListRepository.findAllRecipesFromId(id);

        return recipeListRepository.findAllRecipesFromId(user, id);
    }

    /**
     * Helper funsion find and get a resipe form a
     * recipeList.
     * 
     * @param list     - is a recipieList.
     * @param recipeID - is the ID value of a recipe
     * @return The recipe if it is in the recipieList
     */
    private Recipe getRecipeFromRecipeList(RecipeList list, long recipeID) {
        Recipe recipe = recipeService.findByID(recipeID);

        if (list == null || recipe == null)
            return null;

        if (list.getRecipes().contains(recipe))
            return recipe;

        return null;
    }

    /**
     * Find and delete RecipeList by it ID number.
     * 
     * @param user : is the user that is loged in.
     * @param id   : the id valu of RecipeList
     */
    public void deleteByID(long uid, long id) {
        User user = userService.findByID(uid);
        RecipeList list = findByID(uid, id);
        if (list == null || user == null)
            return;

        if (list.getCreatedBy().getID() == user.getID()) {
            recipeListRepository.delete(list);
        }
    }

    /**
     * Find and removes a resipe form a recipeList.
     *
     * @param user     - is the user that is the sesson
     * @param id       - is the ID value of a recipieList
     * @param recipeID - is the ID value of a recipe
     * @return The Recipelist with out the recipe.
     */
    public RecipeList removeRecipeFromID(long uid, long id, long recipeID) {
        User user = userService.findByID(uid);

        RecipeList list = findByID(uid, id);
        if (list == null || user == null)
            return list;

        if (user.getID() == list.getCreatedBy().getID()) {
            Recipe recipe = getRecipeFromRecipeList(list, recipeID);
            if (recipe == null)
                return list;

            list.getRecipes().remove(recipe);
            recipeListRepository.save(list);

            return list;
        }
        return null;
    }

}

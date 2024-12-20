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
     * Get all RecipeList from the database that are not
     * privat unless the user owns them.
     *
     * @param user : the user requesting the recipes
     * @return a list of RecipeList.
     */
    public List<RecipeList> findAll(User user) {
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
    public List<RecipeList> findAllUserRecipeLists(User user, long id) {
        User owner = userService.findByID(id);
        if(owner==null){
            return null;
        }

        if (user == null){
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
    public RecipeList findByID(User user, long id) {
        if (user!=null){
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
    public RecipeList save(User user, String title, String description, boolean isPrivate) {
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
    public RecipeList updateTitle(User user, String newTitle, long id) {
        RecipeList recipeList = findByID(user, id);
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
     */
    public RecipeList addRecipe(long recipeID, long listID, User user) {
        Recipe recipe = recipeService.findByID(recipeID);
        RecipeList list = findByID(user, listID);

        if (user == null || recipe == null || list == null)
            return null;

        if (recipe.isPrivate() && recipe.getCreatedBy().getID() != user.getID())
            return null;

        if (list.getRecipes().contains(recipe))
            return list;

        list.addRecipe(recipe);

        // TODO: finna hvort það átti að nota update
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
    public List<Recipe> getAllRecipeFromID(User user, long id) {
        RecipeList list = findByID(user, id);

        if (list == null)
            return null;
        ;

        return list.getRecipes();
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
    public void deletByID(User user, long id) {
        RecipeList list = findByID(user, id);
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
    public RecipeList removeRecipeFromID(User user, long id, long recipeID) {
        RecipeList list = findByID(user, id);
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

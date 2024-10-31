package hbv501g.recipes.Services.Implementation;

import java.util.List;

import org.hibernate.Remove;
import org.springframework.stereotype.Service;

import hbv501g.recipes.Persistence.Entities.Recipe;
import hbv501g.recipes.Persistence.Entities.RecipeList;
import hbv501g.recipes.Persistence.Entities.User;
import hbv501g.recipes.Persistence.Repositories.RecipeListRepository;
import hbv501g.recipes.Services.RecipeListService;
import hbv501g.recipes.Services.RecipeService;

@Service
public class RecipeListServiceImplementation implements RecipeListService {
    private RecipeListRepository recipeListRepository;
    private RecipeService recipeService;

    // @Autowired
    public RecipeListServiceImplementation(RecipeListRepository recipeListRepository, RecipeService recipeService) {
        this.recipeListRepository = recipeListRepository;
        this.recipeService = recipeService;
    }

    /**
     * Gets and returns all RecipeList objects from DB
     * 
     * @return all recipe lists
     */
    public List<RecipeList> findAll() {
        return recipeListRepository.findAll();
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
     * Adds the recipe specified to the given list, if both exists, the list
     * owner is logged in and can access the recipe
     * 
     * @param recipeID - the id of the Recipe
     * @param listID   - the id of the RecipeList
     * @param user     - the current User
     */
    public RecipeList addRecipe(long recipeID, long listID, User user) {
        Recipe recipe = recipeService.findByID(recipeID);
        RecipeList list = findByID(listID);

        if (user == null || recipe == null || list == null)
            return null;

        if (list.getCreatedBy().getID() != user.getID()
                || recipe.isPrivate() && recipe.getCreatedBy().getID() != user.getID())
            return null;

        if (list.getRecipes().contains(recipe))
            return list;

        list.addRecipe(recipe);

        // TODO: finna hvort það átti að nota update
        return recipeListRepository.save(list);
    }

    /**
     * Temporary, exchange for the real deal
     */
    @Remove
    private RecipeList findByID(long listID) {
        List<RecipeList> allLists = findAll();
        for (RecipeList l : allLists) {
            if (l.getID() == listID)
                return l;
        }
        return null;
    }

}

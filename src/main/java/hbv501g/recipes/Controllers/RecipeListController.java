package hbv501g.recipes.Controllers;

import java.util.List;

import hbv501g.recipes.Persistence.Entities.Recipe;
import hbv501g.recipes.Persistence.Entities.RecipeList;
import hbv501g.recipes.Persistence.Entities.User;
import hbv501g.recipes.Services.RecipeListService;
import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.*;

/**
 * Controller for the RecipeList entity, with endpoints directly accessing the
 * lists
 */
@RestController
public class RecipeListController {
    private RecipeListService recipeListService;

    // @Autowired
    public RecipeListController(RecipeListService recipeListService) {
        this.recipeListService = recipeListService;
    }

    /**
     * Endpoint to get all recipe lists. Not needed for any assignment, but helpful
     * for testing
     * 
     * @return all RecipeList objects in db
     */
    @GetMapping("/list/all")
    @ResponseBody
    public List<RecipeList> getAllRecipeLists() {
        return recipeListService.findAll();
    }

    /**
     * Find and return the Recipe list that a
     * user has.
     *
     * @param  id - the id number of a user
     * @return the list of recipe that the user
     *	       of the id number owns.
     */
    @GetMapping("/list/user/{id}")
    public List<RecipeList> getAllRecipesListByUserId(HttpSession session, @PathVariable(value = "id") long id){
        User user = (User) session.getAttribute("LoggedInUser");
        if(user != null && user.getID() == id){
            return recipeListService.getAllRecipeLists(user);
        }
	    return recipeListService.findAllUserRecipeList(user);
    }
    
    /**
     * Find and return the Recipe list that a
     * user has.
     *
     * @param  id - the id number of a list
     * @return the list of recipe that the user
     *	       of the id number owns.
     */
    @GetMapping("list/id/{id}")
    public RecipeList getRecipesListById(@PathVariable(value = "id") long id){
	    return recipeListService.listById(id);
    }

    /**
     * Endpoint to create a new recipe list for the current user. The parameters
     * description and isPrivate are not required, so they can be skipped.
     * 
     * @param session     - the current HTTP session
     * @param title       - title for the new list
     * @param description - optional description
     * @param isPrivate   - optionally decides if the list is private
     * @return the new list
     */
    @RequestMapping("/list/new")
    @ResponseBody
    public RecipeList newRecipeList(HttpSession session, @RequestParam String title,
            @RequestParam(required = false) String description, @RequestParam(required = false) boolean isPrivate) {
        return recipeListService.save((User) session.getAttribute("LoggedInUser"), title, description, isPrivate);
    }

    /**
     * Endpoint to add a specific recipe to a given recipe list
     * 
     * @param recipeID - the id of the recipe
     * @param listID   - the id of the list
     * @param session  - the current HTTP session
     * @return the updated recipe
     */
    @PutMapping("/list/addRecipe")
    public RecipeList addRecipeToList(@RequestParam long recipeID, @RequestParam long listID, HttpSession session) {
        return recipeListService.addRecipe(recipeID, listID, (User) session.getAttribute("LoggedInUser"));
    }

}

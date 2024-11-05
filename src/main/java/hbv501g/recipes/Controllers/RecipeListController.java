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
     * @param session - the current http session
     * @return all RecipeList objects that are not privite unless the
     *	       curet user own the list.
     */
    @GetMapping("/list/all")
    @ResponseBody
    public List<RecipeList> getAllRecipeLists(HttpSession session) {
        return recipeListService.findAll((User) session.getAttribute("LoggedInUser"));
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
        return recipeListService.findAllUserRecipeList((User) session.getAttribute("LoggedInUser"), id);
    }
    
    /**
     * Find and return the Recipe list if the user
     * has acess of it.
     *
     * @param session - the current http session
     * @param  id     - the id number of RecipeList
     * @return          the list of recipe that the user
     *	                of the id number owns.
     */
    @GetMapping("list/id/{id}")
    public RecipeList getRecipeListById(HttpSession session, @PathVariable(value = "id") long id){
        return recipeListService.findByID((User) session.getAttribute("LoggedInUser"), id);
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
    @GetMapping("/list/addRecipe")
    public RecipeList addRecipeToList(@RequestParam long recipeID, @RequestParam long listID, HttpSession session) {
        return recipeListService.addRecipe(recipeID, listID, (User) session.getAttribute("LoggedInUser"));
    }

    /**
     * Endpoint gets a Recipe form resiplist.
     *
     * @param session  - the current HTTP session
     * @param listID   - the id of the recipe
     * @param recipeID - the id of the list
     * @return The recipe that has the recipeID and
     *	       is in the recipeList that has the id
     *	       value of listID.
     */
    @GetMapping("/list/id/{listID}/recipe/{recipeID}")
    public Recipe getRecipeFormList(HttpSession session, @PathVariable(value = "listID") long listID,
            @PathVariable(value = "recipeID") long recipeID) {
        return recipeListService.getRecipeFromID((User) session.getAttribute("LoggedInUser"), listID, recipeID);

    /**
     * Find a Recipelist by it ID number and delets it
     *
     * @param session  - the current HTTP session
     * @param listID   - the id of the recipe
     */
    @GetMapping("list/id/{id}/delete")
    public void deletRecipeListByID(HttpSession session, @PathVariable(value = "id") long id){
	    recipeListService.deletByID(
	    			    (User) session.getAttribute("LoggedInUser"),
	    			    id
	    			   );
    }

    /**
     * Endpoint finds a Recipe form resiplist and
     * removes it from it.
     *
     * @param session  - the current HTTP session
     * @param listID   - the id of the recipe
     * @param recipeID - the id of the list
     * @return The recipe that has the recipeID and
     *	       is in the recipeList that has the id
     *	       value of listID.
     */
    @GetMapping("/list/id/{listID}/recipie/{recipeID}/remove")
    public RecipeList removeRecipeFormList(
					   HttpSession session,
					   @PathVariable(value = "listID") long listID,
					   @PathVariable(value = "recipeID") long recipeID
					   )
    {
	return recipeListService.removeRecipeFromID(
					      (User) session.getAttribute("LoggedInUser"),
						   listID,
						   recipeID
					      );
    }

}

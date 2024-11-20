package hbv501g.recipes.Controllers;

import java.util.List;

import hbv501g.recipes.Persistence.Entities.Recipe;
import hbv501g.recipes.Persistence.Entities.RecipeList;
import hbv501g.recipes.Services.RecipeListService;

import org.springframework.web.bind.annotation.*;

import java.util.Map;

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
     * Find and return the Recipe list that a
     * user has.
     *
     * @param id - the id number of a user
     * @return the list of recipe that the user
     *         of the id number owns.
     */
    @GetMapping("/list/user/{id}")
    @ResponseBody
    public List<RecipeList> getAllRecipeListsByUserId(@RequestParam(defaultValue = "-1") long uid,
            @PathVariable(value = "id") long id) {
        return recipeListService.findAllUserRecipeLists(uid, id);
    }

    /**
     * Find and return a Recipe list with the given id if the current user has
     * access to it.
     *
     * @param session - the current http session
     * @param id      - the id number of RecipeList
     * @return A recipe list with the given id, or null
     */
    @GetMapping("/list/id/{id}")
    @ResponseBody
    public RecipeList getRecipeListById(@RequestParam(defaultValue = "-1") long uid,
            @PathVariable(value = "id") long id) {
        return recipeListService.findByID(uid, id);
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
    @PostMapping("/list/new")
    @ResponseBody
    public RecipeList newRecipeList(@RequestParam(defaultValue = "-1") long uid, @RequestParam String title,
            @RequestParam(required = false) String description, @RequestParam(required = false) boolean isPrivate) {
        return recipeListService.save(uid, title, description, isPrivate);
    }

    /**
     * Endpoint to add a specific recipe to a given recipe list
     * 
     * @param recipeID - the id of the recipe
     * @param listID   - the id of the list
     * @param session  - the current HTTP session
     * @return the updated recipe
     */
    @ResponseBody
    @PutMapping("/list/addRecipe")
    public RecipeList addRecipeToList(@RequestParam long recipeID, @RequestParam long listID,
            @RequestParam(defaultValue = "-1") long uid) {
        return recipeListService.addRecipe(recipeID, listID, uid);
    }

    /**
     * Endpoint gets all Recipe form resiplist.
     *
     * @param session - the current HTTP session
     * @param id      - the id of the recipe
     * @return All of the recipe that are int the
     *         recipeList that has the id value of
     *         listID.
     */
    @GetMapping("/list/id/{id}/recipe")
    public List<Recipe> getAllRecipesFromList(@RequestParam(defaultValue = "-1") long uid,
            @PathVariable(value = "id") long id) {
        return recipeListService.getAllRecipesFromID(uid, id);
    }

    /**
     * Endpoint to find a Recipelist by its' ID number and delete it
     *
     * @param session - the current HTTP session
     * @param listID  - the id of the recipe
     */
    @DeleteMapping("list/id/{id}/delete")
    public void deleteRecipeListByID(@RequestParam(defaultValue = "-1") long uid, @PathVariable(value = "id") long id) {
        recipeListService.deleteByID(uid, id);
    }

    /**
     * Endpoint finds a Recipe form resiplist and
     * removes it from it.
     *
     * @param session  - the current HTTP session
     * @param listID   - the id of the recipe
     * @param recipeID - the id of the list
     * @return The recipe that has the recipeID and
     *         is in the recipeList that has the id
     *         value of listID.
     */
    @PatchMapping("/list/id/{listID}/recipe/{recipeID}/remove")
    public RecipeList removeRecipeFromList(@RequestParam(defaultValue = "-1") long uid,
            @PathVariable(value = "listID") long listID,
            @PathVariable(value = "recipeID") long recipeID) {
        return recipeListService.removeRecipeFromID(uid, listID, recipeID);
    }

    /**
     * Updates the title of a recipe list
     * 
     * @param session - the current http session
     * @param id      - the id of the recipe list
     * @param body    - a RequestBody containing a mapping with the new title
     * @return the updated recipelist
     */
    @PatchMapping("/list/updateTitle/{id}")
    public RecipeList updatetitle(@RequestParam(defaultValue = "-1") long uid, @PathVariable(value = "id") long id,
            @RequestBody Map<String, String> body) {
        String newTitle = body.get("title");
        return recipeListService.updateTitle(uid, newTitle, id);
    }

    /** Not in any assignment */
    /**
     * Endpoint to get all recipe lists. Not needed for any assignment, but helpful
     * for testing
     *
     * 
     * @param session - the current http session
     * @return all RecipeList objects that are not privite unless the
     *         curet user own the list.
     */
    @GetMapping("/list/all")
    @ResponseBody
    public List<RecipeList> getAllRecipesFromList(@RequestParam(defaultValue = "-1") long uid) {
        return recipeListService.findAll(uid);
    }

}

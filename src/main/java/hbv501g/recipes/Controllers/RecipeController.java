package hbv501g.recipes.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import hbv501g.recipes.Persistence.Entities.Recipe;
import hbv501g.recipes.Persistence.Entities.Unit;
import hbv501g.recipes.Persistence.Entities.User;
import hbv501g.recipes.Services.RecipeService;
import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class RecipeController {
    private RecipeService recipeService;

    @Autowired
    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    /**
     * Initializes a few recipes
     * 
     * @return all recipes from db
     */
    @GetMapping("/recipe/init")
    @ResponseBody
    public List<Recipe> initRecipes() {
        return recipeService.initRecipes();
    }

    /**
     * Gets all recipes from the database
     * 
     * @return all recipes
     */
    @GetMapping("/recipe/all")
    @ResponseBody
    public List<Recipe> getAllRecipes() {
        return recipeService.findAll();
    }

    /**
     * Find and return the Recipe list that a
     * user has.
     *
     * @param  id - the id number of a user
     * @return the list of recipe that the user
     *	       of the id number owns.
     */
    @GetMapping("/recipe/list/id/{id}")
    public List<Recipe> getRecipesListById(@PathVariable(value = "id") long id){
	return recipeService.listById();
    }

    /**
     * Finds and returns a recipe with a given ID. Returns that reipe if any recipe
     * has the ID, otherwise it returns null
     * 
     * @param id - the id of the requested recipe
     * @return the recipe with that id, or null
     */
    @GetMapping("/recipe/id/{id}")
    public Recipe getRecipeById(@PathVariable(value = "id") long id) {
        return recipeService.findByID(id);
    }

    /**
     * Endpoint that finds an recipe by id and
     * removes it form the database if the uesr
     * own the recipe.
     * 
     * @param session : is the current session
     * @param id      : ID number of the recipe
     */
    @GetMapping("/recipe/delete/{id}")
    public void deleteRecipeById(HttpSession session, @PathVariable(value = "id") long id) {
        User user = (User) session.getAttribute("LoggedInUser");
        
        if (user != null) {
            User author = recipeService.findByID(id).getCreatedBy();
            
            if (author != null) {
                if (author.getID() == user.getID()) {
                    recipeService.deleteById(id);
                }
            }
        }
    }

    /**
     * Find and deletes the Recipe list that a
     * user has.
     *
     * @param  id - the id number of a user
     */
    @GetMapping("/recipe/list/delete")
    public void deleteRecipesList(@PathVariable(value = "id") long id){
	User user = (User) session.getAttribute("LoggedInUser");
        
        if (user != null) recipeService.deleteList(user.getID);
    }

    /**
     * Find and remove the rescipe with maching id form a
     * recepie list if the user has it in its list.
     *
     * @param id : is a 8 byte integer and is the id
     * 		   of the precipe.
     */
    @GetMapping("/recipe/list/remove/id/{id}")
    public list<Recipe> removeRecipesListByID(@PathVariable(value = "id") long id){
	User user = (User) session.getAttribute("LoggedInUser");
        
        if (user != null){
	    return recipeService.removeRecipesListByID(user.getID, id);
	}
    }

    /**
     * Á þetta einu sinni að vera endpoint? Ekki endilega til að birta í viðmóti as
     * is
     * 
     * @param id
     * @return
     */
    @GetMapping("/recipe/id/{id}/totalpurch")
    @ResponseBody
    public int getTotalPurchaseCost(@PathVariable(value = "id") long id) {
        return recipeService.getTotalPurchaseCost(id);
    }

    /**
     * Á þetta einu sinni að vera endpoint? Ekki endilega til að birta í viðmóti as
     * is
     * 
     * @param id
     * @return
     */
    @GetMapping("/recipe/id/{id}/totalIng")
    @ResponseBody
    public double getTotalIngredientCost(@PathVariable(value = "id") long id) {
        return recipeService.getTotalIngredientCost(id);
    }

    @GetMapping("/recipe/id/{id}/personal")
    public double getPersonalizedPurchaseCost(@PathVariable(value = "id") long id, HttpSession session) {
        User user = (User) session.getAttribute("LoggedInUser");
        return recipeService.getPersonalizedPurchaseCost(user, id);
    }

    /**
     * Takes in a recipe. It can contain IngredientMeasurements already, but the
     * ingredients can also be added later, with the addIngredients method and
     * corresponding endpoint.
     * The current user and today's date are added to the recipe. The user is found
     * in the session. The recipe is saved in the database.
     * 
     * @param session   - the current http session
     * @param newRecipe - a recipe that is being saved
     * @return the new recipe
     */
    @PostMapping("/recipe/new")
    public Recipe newRecipe(HttpSession session, @RequestBody Recipe newRecipe) {
        User author = (User) session.getAttribute("LoggedInUser");
        return recipeService.setRecipeAuthorAndDate(newRecipe, author);
    }

    /**
     * Takes in the id of a recipe, and lists of units, ingredient ids and
     * quantities. Calls a method in the RecipeService to make make
     * IngredientMeasurements from the Lists, where the ingredient with the
     * ingredientID with index i, unit with index i and qty with index i form one
     * measurement. These are added to the recipe
     * 
     * @param recipeID      - the id of the recipe to which the ingredients are
     *                      added
     * @param units         - a list with the units of the measurements
     * @param ingredientIDs - a list with the ids of the ingredients in the
     *                      measurements
     * @param qty           - list with the quantities of the measurements
     * @return the recipe with the given recipeID with the measurements added
     */
    @RequestMapping("recipe/addIngredients")
    public Recipe addIngredients(@RequestParam long recipeID, @RequestParam List<Unit> units,
            @RequestParam List<Long> ingredientIDs, @RequestParam List<Double> qty) {
        return recipeService.addIngredients(recipeID, ingredientIDs, qty, units);
    }
}

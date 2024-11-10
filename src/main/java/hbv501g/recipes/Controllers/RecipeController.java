package hbv501g.recipes.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import hbv501g.recipes.Persistence.Entities.Recipe;
import hbv501g.recipes.Persistence.Entities.Unit;
import hbv501g.recipes.Persistence.Entities.User;
import hbv501g.recipes.Services.RecipeService;
import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * A controller containing endpoints relating to recipes
 */
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
     * Gets all recipes available to the user who is
     * currently logged in. This includes all public recipes, and private recipes by
     * the user
     * If there is no user currently logged in, only public recipes are returned
     * 
     * @param session - The current httpsession
     * @return all available recipes
     */
    @GetMapping("/recipe/all")
    @ResponseBody
    public List<Recipe> getAllRecipes(HttpSession session) {
        User user = (User) session.getAttribute("LoggedInUser");
        return recipeService.findAccessibleToUser(user);
    }

    /**
     * Given a maximum price, finds all recipes accessible to the current user which
     * have a total purchase cost under that price
     * 
     * @param tpc - Maximum total purchase cost of recipe
     * @return all accessible recipes with tpc under the given value
     */
    @GetMapping("/recipe/underTPC/{tpc}")
    public List<Recipe> getAllRecipesUnderTPC(@PathVariable(value = "tpc") int tpc, HttpSession session) {
        User user = (User) session.getAttribute("LoggedInUser");
        return recipeService.findUnderTPC(tpc, user);
    }

    /**
     * Given a maximum price, finds all recipes accessible to the current user which
     * have a total ingredient cost under that price
     * 
     * @param tic - Maximum total ingredient cost of recipe
     * @return all accessible recipes with tic under the given value
     */
    @GetMapping("/recipe/underTIC/{tic}")
    public List<Recipe> getAllRecipesUnderTIC(@PathVariable(value = "tic") int tic, HttpSession session) {
        User user = (User) session.getAttribute("LoggedInUser");
        return recipeService.findUnderTIC(tic, user);
    }

    /**
     * Gets all recipes that contain the search term in the title, which are
     * accessible
     * to the current user
     * 
     * @param term - the term the titles should include
     * @return list of all recipes with the search term in the title
     */
    @GetMapping("/recipe/search/{term}")
    public List<Recipe> findRecipesByTitle(HttpSession session, @PathVariable(value = "term") String term) {
        User user = (User) session.getAttribute("LoggedInUser");
        return recipeService.findByTitleContaining(user, term);
    }

    /**
     * Finds and returns a recipe with a given ID. Returns that reipe if any recipe
     * has the ID, and the current user has access to it, otherwise it returns null
     * 
     * @param id      - the id of the requested recipe
     * @param session - the current http session
     * @return the recipe with that id, or null
     */
    @GetMapping("/recipe/id/{id}")
    public Recipe getRecipeById(@PathVariable(value = "id") long id, HttpSession session) {
        User user = (User) session.getAttribute("LoggedInUser");
        return recipeService.findAccessibleByID(id, user);
    }

    /**
     * Endpoint that finds an recipe by id and
     * removes it form the database if the uesr
     * own the recipe.
     * 
     * @param session : is the current session
     * @param id      : ID number of the recipe
     */
    @RequestMapping("/recipe/delete/{id}")
    public void deleteRecipeById(HttpSession session, @PathVariable(value = "id") long id) {
        recipeService.deleteById((User) session.getAttribute("LoggedInUser"), id);
    }

    /**
     * gets the total cost to purchase all ingredients needed for a recipe
     * 
     * @param id - recipe id
     * @return total purchase cost
     */
    @GetMapping("/recipe/id/{id}/totalpurch")
    @ResponseBody
    public int getTotalPurchaseCost(@PathVariable(value = "id") long id) {
        return recipeService.getTotalPurchaseCost(id);
    }

    /**
     * Gets the total ingredient cost for a given recipe, that is the exact cost for
     * the quantity used
     * 
     * @param id - recipe id
     * @return total ingredient cost
     */
    @GetMapping("/recipe/id/{id}/totalIng")
    @ResponseBody
    public double getTotalIngredientCost(@PathVariable(value = "id") long id) {
        return recipeService.getTotalIngredientCost(id);
    }

    /**
     * Gets the total cost of ingredients the current user doesn't have in the
     * pantry for the recipe specified
     * 
     * @param id      - recipe id
     * @param session - current session
     * @return personalized purchase cost
     */
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
     * If no user is logged in, null is returned
     * 
     * @param session   - the current http session
     * @param newRecipe - a recipe that is being saved
     * @return the new recipe, or null
     */
    @PostMapping("/recipe/new")
    public Recipe newRecipe(HttpSession session, @RequestBody Recipe newRecipe) {
        User author = (User) session.getAttribute("LoggedInUser");
        if (author != null) {
            return recipeService.setRecipeAuthorAndDate(newRecipe, author);
        }
        return null;
    }

    /**
     * Takes in the id of a recipe, and lists of units, ingredient ids and
     * quantities. Calls a method in the RecipeService to make make
     * IngredientMeasurements from the Lists, where the ingredient with the
     * ingredientID with index i, unit with index i and qty with index i form one
     * measurement. These are added to the recipe. Users can only add ingredients
     * to their own recipes, and have to be logged in to do so
     * 
     * @param recipeID      - the id of the recipe to which the ingredients are
     *                      added
     * @param units         - a list with the units of the measurements
     * @param ingredientIDs - a list with the ids of the ingredients in the
     *                      measurements
     * @param qty           - list with the quantities of the measurements
     * @param session       - The current http session
     * @return the recipe with the given recipeID with the measurements added
     */
    @RequestMapping("recipe/addIngredients")
    public Recipe addIngredients(@RequestParam long recipeID, @RequestParam List<Unit> units,
            @RequestParam List<Long> ingredientIDs, @RequestParam List<Double> qty, HttpSession session) {
        User currUser = (User) session.getAttribute("LoggedInUser");
        return recipeService.addIngredients(currUser.getID(), recipeID, ingredientIDs, qty, units);
    }

    @PutMapping("/recipe/{id}/update")
    public Recipe updateRecipeDetails(@PathVariable(value = "id") long id, @RequestBody Recipe updatedRecipe,
            HttpSession session) {
        User currUser = (User) session.getAttribute("LoggedInUser");
        if (currUser == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User is not logged in.");
        }

        Recipe recipe = recipeService.updateRecipeDetails(id, updatedRecipe);
        return recipe;

    }

    // ** Not in any assignment */

    /**
     * Gets all recipes from the database. Not part of any assignment
     * 
     * @return all recipes
     */
    @GetMapping("/recipe/getAll")
    @ResponseBody
    @Deprecated
    public List<Recipe> getAllRecipesWithPrivate() {
        return recipeService.findAll();
    }

    /**
     * Finds and returns a recipe with a given ID. Returns that reipe if any recipe
     * has the ID, otherwise it returns null
     * 
     * @param id - the id of the requested recipe
     * @return the recipe with that id, or null
     */
    @Deprecated
    @GetMapping("/recipe/getById/{id}")
    public Recipe getRecipeByIdWithPrivate(@PathVariable(value = "id") long id) {
        return recipeService.findByID(id);
    }

    @GetMapping("/recipe/all/ordered")
    public List<Recipe> getAllOrderedRecipes(HttpSession session) {
        User user = (User) session.getAttribute("LoggedInUser");
        return recipeService.findOrderedRecipes(user);
    }
}

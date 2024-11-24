package hbv501g.recipes.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import hbv501g.recipes.Persistence.Entities.Recipe;
import hbv501g.recipes.Persistence.Entities.Unit;
import hbv501g.recipes.Services.RecipeService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

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
     * Gets all recipes available to the user who is currently logged in. This
     * includes all public recipes, and private recipes by the user If there is no
     * user currently logged in, only public recipes are returned
     * 
     * @param uid - the id of the current user, or 0 no user is logged in
     * @return all accessible recipes
     */
    @GetMapping("/recipe/all")
    @ResponseBody
    public List<Recipe> getAllRecipes(@RequestParam(defaultValue = "0") long uid) {
        return recipeService.findAccessibleToUser(uid);
    }

    /**
     * Given a maximum price, finds all recipes accessible to the current user which
     * have a total purchase cost under that price
     * 
     * @param tpc - Maximum total purchase cost of recipe
     * @param uid - the id of the current user, or 0 no user is logged in
     * @return all accessible recipes with tpc under the given value
     */
    @GetMapping("/recipe/underTPC/{tpc}")
    public List<Recipe> getAllRecipesUnderTPC(@PathVariable int tpc, @RequestParam(defaultValue = "0") long uid) {
        return recipeService.findUnderTPC(tpc, uid);
    }

    /**
     * Given a maximum price, finds all recipes accessible to the current user which
     * have a total ingredient cost under that price
     * 
     * @param tic - Maximum total ingredient cost of recipe
     * @param uid - the id of the current user, or 0 no user is logged in
     * @return all accessible recipes with tic under the given value
     */
    @GetMapping("/recipe/underTIC/{tic}")
    public List<Recipe> getAllRecipesUnderTIC(@PathVariable int tic,
            @RequestParam(defaultValue = "0") long uid) {
        return recipeService.findUnderTIC(tic, uid);
    }

    /**
     * Gets all recipes that contain the search term in the title, which are
     * accessible to the current user
     * 
     * @param uid  - the id of the current user, or 0 no user is logged in
     * @param term - the term the titles should include
     * @return list of all recipes with the search term in the title
     */
    @GetMapping("/recipe/search/{term}")
    public List<Recipe> findRecipesByTitle(@RequestParam(defaultValue = "0") long uid, @PathVariable String term) {
        return recipeService.findByTitleContaining(uid, term);
    }

    /**
     * Finds and returns a recipe with a given ID. Returns that reipe if any recipe
     * has the ID, and the current user has access to it, otherwise it returns null
     * 
     * @param id  - the id of the requested recipe
     * @param uid - the id of the current user, or 0 no user is logged in
     * @return the recipe with that id, or null
     */
    @GetMapping("/recipe/id/{id}")
    public Recipe getRecipeById(@PathVariable long id, @RequestParam(defaultValue = "0") long uid) {
        return recipeService.findAccessibleByID(id, uid);
    }

    /**
     * Endpoint that finds an recipe by id and removes it form the database if the
     * uesr own the recipe.
     * 
     * @param uid - the id of the current user, or 0 no user is logged in
     * @param id  : ID number of the recipe
     */
    @DeleteMapping("/recipe/delete/{id}")
    public void deleteRecipeById(@RequestParam(defaultValue = "0") long uid, @PathVariable long id) {
        recipeService.deleteById(uid, id);
    }

    /**
     * gets the total cost to purchase all ingredients needed for a recipe, if the
     * recipe exists and is accessible to the current user
     * 
     * @param id  - recipe id
     * @param uid : the id of the current user, or 0 no user is logged in
     * @return total purchase cost
     */
    @GetMapping("/recipe/id/{id}/totalpurch")
    @ResponseBody
    public int getTotalPurchaseCost(@PathVariable long id, @RequestParam(defaultValue = "0") long uid) {
        return recipeService.getTotalPurchaseCost(uid, id);
    }

    /**
     * Gets the total ingredient cost for a given recipe, that is the exact cost for
     * the quantity used, if the recipe exists and is accessible to the current user
     * 
     * @param id  - recipe id
     * @param uid : the id of the current user, or 0 no user is logged in
     * @return total ingredient cost
     */
    @GetMapping("/recipe/id/{id}/totalIng")
    @ResponseBody
    public double getTotalIngredientCost(@PathVariable long id, @RequestParam(defaultValue = "0") long uid) {
        return recipeService.getTotalIngredientCost(uid, id);
    }

    /**
     * Gets the total cost of ingredients the current user doesn't have in the
     * pantry for the recipe specified, if the recipe exists and is accessible to
     * the current user
     * 
     * @param id  - recipe id
     * @param uid : the id of the current user, or 0 no user is logged in
     * @return personalized purchase cost
     */
    @GetMapping("/recipe/id/{id}/personal")
    public double getPersonalizedPurchaseCost(@PathVariable long id, @RequestParam(defaultValue = "0") long uid) {
        return recipeService.getPersonalizedPurchaseCost(uid, id);
    }

    /**
     * Takes in a recipe. It can contain IngredientMeasurements already, but the
     * ingredients can also be added later, with the addIngredients method and
     * corresponding endpoint.
     * The current user and today's date are added to the recipe. The user is found
     * in the session. The recipe is saved in the database.
     * If no user is logged in, null is returned
     * 
     * @param uid       : the id of the current user, or 0 no user is logged in
     * @param newRecipe - a recipe that is being saved
     * @return the new recipe, or null
     */
    @PostMapping("/recipe/new")
    public Recipe newRecipe(@RequestParam(defaultValue = "0") long uid, @RequestBody Recipe newRecipe) {
        return recipeService.setRecipeAuthorAndDate(newRecipe, uid);
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
     * @param uid           : the id of the current user, or 0 no user is logged in
     * @return the recipe with the given recipeID with the measurements added
     */
    @PutMapping("/recipe/addIngredients")
    public Recipe addIngredients(@RequestParam long recipeID, @RequestParam List<Unit> units,
            @RequestParam List<Long> ingredientIDs, @RequestParam List<Double> qty,
            @RequestParam(defaultValue = "0") long uid) {
        return recipeService.addIngredients(uid, recipeID, ingredientIDs, qty, units);
    }

    /**
     * Endpoint to update the details of a recipe with the given id, if a user is
     * logged in and the recipe was made by them
     * 
     * @param id            - the id of the recipe that should be updated
     * @param updatedRecipe - a recipe with the updated information
     * @param uid           : the id of the current user, or 0 no user is logged in
     * @return the original recipe after being updated
     */
    @PutMapping("/recipe/{id}/update")
    public Recipe updateRecipeDetails(@PathVariable long id, @RequestBody Recipe updatedRecipe,
            @RequestParam(defaultValue = "0") long uid) {
        return recipeService.updateRecipeDetails(id, updatedRecipe, uid);
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
    public Recipe getRecipeByIdWithPrivate(@PathVariable long id) {
        return recipeService.findByID(id);
    }

    @GetMapping("/recipe/all/ordered")
    public List<Recipe> getAllOrderedRecipes(@RequestParam(defaultValue = "0") long uid) {
        return recipeService.findOrderedRecipes(uid);
    }

    /**
     * Gets all recipes which are accessible to the current user and returns them in
     * alphabetical order
     * 
     * @param uid : the id of the current user, or 0 no user is logged in
     * @return all recipes ordered alphabetically
     */
    @GetMapping("/recipe/all/orderedByTitle")
    public List<Recipe> getAllOrderedRecipesByTitle(@RequestParam(defaultValue = "0") long uid) {
        return recipeService.findOrderedRecipesByTitle(uid);
    }
}

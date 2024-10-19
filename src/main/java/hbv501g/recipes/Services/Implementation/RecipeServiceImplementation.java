package hbv501g.recipes.Services.Implementation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hbv501g.recipes.Persistence.Entities.Ingredient;
import hbv501g.recipes.Persistence.Entities.IngredientMeasurement;
import hbv501g.recipes.Persistence.Entities.Recipe;
import hbv501g.recipes.Persistence.Entities.Unit;
import hbv501g.recipes.Persistence.Entities.User;
import hbv501g.recipes.Persistence.Repositories.RecipeRepository;
import hbv501g.recipes.Services.IngredientService;
import hbv501g.recipes.Services.RecipeService;

@Service
public class RecipeServiceImplementation implements RecipeService {
    private RecipeRepository recipeRepository;
    private IngredientService ingredientService;

    @Autowired
    public RecipeServiceImplementation(RecipeRepository recipeRepository, IngredientService ingredientService) {
        this.recipeRepository = recipeRepository;
        this.ingredientService = ingredientService;
    }

    @Override
    public List<Recipe> findAll() {
        return recipeRepository.findAll();
    }

    @Override
    public Recipe findByID(long id) {
        return recipeRepository.findByID(id);
    }

    //  @Override
    public Recipe save(Recipe recipe) {
        return recipeRepository.save(recipe);
    }

    public Recipe update(Recipe updatedRecipe) {
        return recipeRepository.save(updatedRecipe);
    }

    /**
     * Initializes a few recipes, if none are found in the db
     */
    public List<Recipe> initRecipes() {
        List<Recipe> AllRecipes = findAll();

        if (AllRecipes.size() == 0) {
            Recipe recipe = new Recipe();
            recipe.setTitle("uppskrift 1");
            save(recipe);

            recipe = new Recipe();
            recipe.setTitle("uppskrift 2");
            save(recipe);

            AllRecipes = findAll();
        }

        return AllRecipes;
    }

    /**
     * Gets the total purchase cost for a recipe specified by an id
     * 
     * @param id: recipe id
     * @return total purchase cost for the recipe
     */
    public int getTotalPurchaseCost(long id) {
        Recipe recipe = findByID(id);
        if (recipe == null)
            return 0;

        return recipe.getTotalPurchaseCost();
    }

    /**
     * Gets the total ingredient cost for a recipe specified by an id
     * 
     * @param id: recipe id
     * @return total ingredient cost for the recipe
     */
    public double getTotalIngredientCost(long id) {
        Recipe recipe = findByID(id);
        if (recipe == null)
            return 0;

        return recipe.getTotalIngredientCost();
    }

    /**
     * Adds IngredientMeasurements to a recipe with the given recipeID. Also takes
     * in lists of units, ingredient ids and quantities. Makes
     * IngredientMeasurements from the Lists, where the ingredient with the
     * ingredientID with index i, unit with index i and qty with index i form one
     * measurement. These are added to the recipe. If the lists don't all have the
     * same size, the recipe is returned without adding any ingredientMEasuremetns
     * 
     * @param recipeID      - the id of the recipe to which the ingredients are
     *                      added
     * @param ingredientIDs -a list with the ids of the ingredients in the
     *                      measurements
     * @param qty           - list with the quantities of the measurements
     * @param units         - a list with the units of the measurements
     * @return Recipe - the recipe with the given recipeID with the measurements
     *         added
     */
    public Recipe addIngredients(long recipeID, List<Long> ingredientIDs, List<Double> qty, List<Unit> units) {
        Recipe recipe = findByID(recipeID);
        List<IngredientMeasurement> measurements = new ArrayList<>();
        if (units.size() != qty.size() || units.size() != ingredientIDs.size()) {
            return recipe;
        }
        for (int i = 0; i < units.size(); i++) {
            Ingredient ingredient = ingredientService.findByID(ingredientIDs.get(i));
            measurements.add(new IngredientMeasurement(ingredient, units.get(i), qty.get(i)));
        }
        recipe.setIngredientMeasurements(measurements);
        return recipe;
    }

    /**
     * @param recipe
     * @param author
     * @param creationDate
     * @return Recipe
     */
    @Override
    public Recipe setRecipeAuthorAndDate(Recipe recipe, User author, Date creationDate) {
        recipe.setCreatedBy(author);
        recipe.setDateOfCreation(creationDate);
        save(recipe);
        return recipe;
    }

}

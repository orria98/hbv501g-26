package hbv501g.recipes.Services.Implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hbv501g.recipes.Persistence.Entities.Ingredient;
import hbv501g.recipes.Persistence.Entities.IngredientMeasurement;
import hbv501g.recipes.Persistence.Entities.Recipe;
import hbv501g.recipes.Persistence.Entities.User;
import hbv501g.recipes.Persistence.Repositories.RecipeRepository;

import hbv501g.recipes.Services.RecipeService;
import hbv501g.recipes.Services.UserService;

@Service
public class RecipeServiceImplementation implements RecipeService {
    private RecipeRepository recipeRepository;
    private UserService userService;

    @Autowired
    public RecipeServiceImplementation(RecipeRepository recipeRepository, UserService userService) {
        this.recipeRepository = recipeRepository;
        this.userService = userService;
    }

    @Override
    public List<Recipe> findAll() {
        return recipeRepository.findAll();
    }

    @Override
    public Recipe findByID(long id) {
        return recipeRepository.findByID(id);
    }

    // @Override
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
     * If the units in pantry and recipe do not match, the ingredient will not be
     * found in pantry
     * 
     * @param user     - the user owning the pantry
     * @param recipeId - the id of the recipe to calculate for
     */
    public double getPersonalizedPurchaseCost(User user, long recipeId) {
        Recipe recipe = findByID(recipeId);

        if (user == null || recipe == null)
            return 0;

        int total = 0;
        List<IngredientMeasurement> pantry = user.getPantry();

        for (IngredientMeasurement recipeIngredient : recipe.getIngredientMeasurements()) {
            IngredientMeasurement pantryItem = userService.findItemInPantry(pantry, recipeIngredient.getIngredient());

            // TODO: passa að unit passi
            double qty = recipeIngredient.getQuantity();
            if (pantryItem != null && recipeIngredient.getUnit() == pantryItem.getUnit())
                qty -= pantryItem.getQuantity();
            if (qty > 0)
                total += calculateTotalPurchaseCost(recipeIngredient.getIngredient(), qty);
        }

        return total;
    }

    /**
     * Purchase cost based on how many packages are needed. Assumes both ingredient
     * and measurement have the same unit
     * 
     * @param ingredient - the ingredient as a package
     * @param quantity   - quantity to calculate for
     * @return total cost to purchase this measurement
     */
    private double calculateTotalPurchaseCost(Ingredient ingredient, double quantity) {
        if (ingredient == null)
            return 0;

        int packageCount = (quantity % ingredient.getQuantity() == 0 ? 0 : 1)
                + (int) (quantity / ingredient.getQuantity());

        return ingredient.getPrice() * packageCount;

    }

}

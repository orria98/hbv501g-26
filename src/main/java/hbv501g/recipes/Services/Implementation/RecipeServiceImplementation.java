package hbv501g.recipes.Services.Implementation;

import java.time.LocalDate;
import java.util.ArrayList;
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
import hbv501g.recipes.Services.UserService;

@Service
public class RecipeServiceImplementation implements RecipeService {
    private RecipeRepository recipeRepository;
    private UserService userService;
    private IngredientService ingredientService;

    @Autowired
    public RecipeServiceImplementation(RecipeRepository recipeRepository, UserService userService,
            IngredientService ingredientService) {
        this.recipeRepository = recipeRepository;
        this.userService = userService;
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

    /**
     * Finds the recipe with the given id, if one exists and is accessible to the
     * user. If the user is null, then only public recipes are accessible
     */
    public Recipe findAccessibleByID(long id, User user) {
        if (user == null) {
            return recipeRepository.findByIsPrivateFalseAndID(id);
        }
        return recipeRepository.findAccessibleByID(user, id);
    }

    // @Override
    public Recipe save(Recipe recipe) {
        return recipeRepository.save(recipe);
    }

    public Recipe update(Recipe updatedRecipe) {
        return recipeRepository.save(updatedRecipe);
    }

    /**
     * finds all recipes which are accessible to the given user, which have a given
     * search
     * term in the title
     * 
     * @param user       - the user who is searching
     * @param searchTerm - the string that should be in the title
     * @return A list of all recipes with the search term in the title
     */
    public List<Recipe> findByTitleContaining(User user, String searchTerm) {
        if (user == null) {
            return recipeRepository.findByIsPrivateFalseAndTitleContaining(searchTerm);
        }
        return recipeRepository.searchAccessibleRecipes(user, "%" + searchTerm + "%");
    }

    /**
     * Finds all recipes that are accessible to the given user.
     * This includes all public recipes, and private recipes by the user
     * If the user us null, only public recipes are returned
     * 
     * @param user - the user to find recipes for
     * @return all recipes available to the user
     */
    public List<Recipe> findAccessibleToUser(User user) {
        if (user == null) {
            return recipeRepository.findByIsPrivateFalse();
        }
        return recipeRepository.findByIsPrivateFalseOrCreatedBy(user);
    }

    /**
     * Find and delet the rescipe with maching id.
     *
     * @param id : is a 8 byte integer and is the id
     *           of the precipe.
     */
    @Override
    public void deleteById(long id) {
        recipeRepository.deleteById(id);
    }

    /*
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

            if (recipeIngredient != null && recipeIngredient.getIngredient() != null
                    && recipeIngredient.getUnit() != null) {
                Ingredient ingredient = recipeIngredient.getIngredient();

                // the quantity used in the recipe, in the unit of the ingredient
                double quantity = getQuantityInIngredientUnit(recipeIngredient);

                // The quantity of the ingredient which is in the pantry, in the unit of the
                // ingredient
                double quantityInPantry = getQuantityInIngredientUnit(pantryItem);

                quantity -= quantityInPantry;

                if (quantity > 0) {
                    total += calculateTotalPurchaseCost(ingredient, quantity);
                }
            }
        }

        return total;
    }

    /**
     * Calculates and returns the quantity of the ingredient used in the
     * IngredientMeasurement,
     * in the unit of the Ingredient used. Returns 0 if the measurement, its unit og
     * ingredient is null
     * 
     * @param measurement - ingredientmeasurement item
     * @return the quantity of the ingredient, in the same unit
     */
    private double getQuantityInIngredientUnit(IngredientMeasurement measurement) {
        if (measurement == null || measurement.getIngredient() == null || measurement.getUnit() == null) {
            return 0;
        }
        return (measurement.getQuantity() * measurement.getUnit().getMlInUnit())
                / (measurement.getUnit().getMlInUnit());
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
        if (ingredient != null && quantity != 0 && ingredient.getQuantity() != 0) {
            return ingredient.getPrice() * Math.ceil(quantity / ingredient.getQuantity());
        }
        return 0;
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
    public Recipe addIngredients(long userID, long recipeID, List<Long> ingredientIDs, List<Double> qty,
            List<Unit> units) {
        Recipe recipe = findByID(recipeID);
        User currUser = userService.findByID(userID);
        if (recipe == null || currUser == null || userID != recipe.getCreatedBy().getID()) {
            return null;
        }
        List<IngredientMeasurement> measurements = new ArrayList<>();
        if (units.size() != qty.size() || units.size() != ingredientIDs.size()) {
            return recipe;
        }
        for (int i = 0; i < units.size(); i++) {
            Ingredient ingredient = ingredientService.findByID(ingredientIDs.get(i));
            if (ingredient != null && (!ingredient.isPrivate()
                    || (ingredient.getCreatedBy() != null && ingredient.getCreatedBy().getID() == userID))) {
                measurements.add(new IngredientMeasurement(ingredient, units.get(i), qty.get(i)));
            }
        }
        recipe.setIngredientMeasurements(measurements);
        return save(recipe);
    }

    /**
     * Takes in a recipe and author. Adds the author and current date to the recipe
     * and saves it in the database, and returns the recipe
     * 
     * @param recipe - The recipe that is being saved
     * @param author - The user who made the recipe
     * @return The recipe with the added information
     */
    @Override
    public Recipe setRecipeAuthorAndDate(Recipe recipe, User author) {
        recipe.setCreatedBy(author);
        recipe.setDateOfCreation(LocalDate.now());
        return save(recipe);
    }

    @Override
    public Recipe updateRecipeDetails(long id, Recipe updatedRecipe) {
        Recipe recipe = findByID(id);
        if (recipe == null) {
            return null;
        }
        recipe.setTitle(updatedRecipe.getTitle());
        recipe.setInstructions(updatedRecipe.getInstructions());
        recipe.setPrivate(updatedRecipe.isPrivate());

        return update(recipe);
    }

    /**
     * Finds all recipes which are accessible to the given user and have a total
     * purchase cost under the given limit. If the user is null, then only public
     * recipes are searched
     * @param upperLimit - the upper limit of the total purchase cost
     * @param user - the user making the request
     * @return all accessible recipes under that price
     */
    public List<Recipe> findUnderTPC(int upperLimit, User user) {
        if (user == null){
            return recipeRepository.findPublicUnderTPC(upperLimit);
        }
        return recipeRepository.findAccessibleUnderTPC(user, upperLimit);
    }

    /**
     * Finds all recipes which are accessible to the given user and have a total
     * ingredient cost under the given limit. If the user is null, then only public
     * recipes are searched
     * @param upperLimit - the upper limit of the total ingredient cost
     * @param user - the user making the request
     * @return all accessible recipes under that price
     */
    public List<Recipe> findUnderTIC(int upperLimit, User user) {
        if (user == null){
            return recipeRepository.findPublicUnderTIC(upperLimit);
        }
        return recipeRepository.findAccessibleUnderTIC(user, upperLimit);
    }

}

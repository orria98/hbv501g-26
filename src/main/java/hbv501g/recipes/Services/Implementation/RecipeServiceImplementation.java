package hbv501g.recipes.Services.Implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hbv501g.recipes.Persistence.Entities.Recipe;
import hbv501g.recipes.Persistence.Repositories.RecipeRepository;

import hbv501g.recipes.Services.RecipeService;

@Service
public class RecipeServiceImplementation implements RecipeService {
    private RecipeRepository recipeRepository;

    @Autowired
    public RecipeServiceImplementation(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
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
}

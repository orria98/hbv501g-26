package hbv501g.recipes.Services.Implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hbv501g.recipes.Persistence.Entities.Recipe;
import hbv501g.recipes.Persistence.Repositories.RecipeRepository;

import hbv501g.recipes.Services.RecipeService;

@Service
public class RecipeServiceImplentation implements RecipeService {
    private RecipeRepository recipeRepository;

    @Autowired
    public RecipeServiceImplentation(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public List<Recipe> findAll() {
        return recipeRepository.findAll();
    }

    //@Override
    public Recipe save(Recipe recipe) {
        return recipeRepository.save(recipe);
    }

    public Recipe update(Recipe updatedRecipe) {
        return recipeRepository.save(updatedRecipe);
    }

    /**
     * Find and delet the rescipe with maching id.
     *
     * @param id : is a 8 byte integer and is the id
     * 		   of the precipe.
     */
    @Override
    public void deleteById(Long id){
	recipeRepository.delet(id);
    }

    public List<Recipe> initRecipes() {
        List<Recipe> AllRecipes = findAll();

        if (AllRecipes.size() == 0) {
            Recipe recipe = new Recipe();
            recipe.setTitle("uppskrift 1");
            // recipe.addIngredientMeasurement(new IngredientMeasurement(new Ingredient("hveiti1", Unit.G, 10000, 5004), Unit.G, 500));
            // recipe.addIngredientMeasurement(new IngredientMeasurement(new Ingredient("ger1", Unit.G, 100, 408), Unit.G, 20));
            
            save(recipe);

            recipe = new Recipe();
            recipe.setTitle("uppskrift2");
            save(recipe);

            AllRecipes = findAll();
        }

        return AllRecipes;
    }
}

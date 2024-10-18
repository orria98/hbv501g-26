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
    public Recipe findByID(long id){
        return recipeRepository.findByID(id);
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
    public void deleteById(long id){
	recipeRepository.deleteById(id);
    }
    
     /* Initializes a few recipes, if none are found in the db
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
}

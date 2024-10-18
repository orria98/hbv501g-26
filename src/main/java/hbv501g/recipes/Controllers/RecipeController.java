package hbv501g.recipes.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import hbv501g.recipes.Persistence.Entities.Recipe;
import hbv501g.recipes.Services.RecipeService;

@RestController
public class RecipeController {
    private RecipeService recipeService;

    @Autowired
    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/recipe/init")
    @ResponseBody
    public List<Recipe> initRecipes(){
        return recipeService.initRecipes();
    }

    @GetMapping("/recipe/all")
    @ResponseBody
    public List<Recipe> getAllRecipes(){
        return recipeService.findAll();
    }

    /**
     * Endpoint that finds an recipe by id and
     * removes it form the database.
     *
     * @param id : ID number of the recipe
     */
    @GetMapping("/recipe/delet/{id}")
    public void deleteRecipeById(@PathVariable(value = "id")long id){
	recipeService.deleteById(id);
    }
}

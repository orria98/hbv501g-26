package hbv501g.recipes.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import hbv501g.recipes.Persistence.Entities.Recipe;
import hbv501g.recipes.Persistence.Entities.User;
import hbv501g.recipes.Services.RecipeService;

import jakarta.servlet.http.HttpSession;

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

    @GetMapping("/recipe/id/{id}")
    public Recipe getRecipeById(@PathVariable(value = "id") long id) {
        return recipeService.findByID(id);
    }

    /**
     * Endpoint that finds an recipe by id and
     * removes it form the database if the uesr
     * own the recipe.
     * @param session : is the current session
     * @param id      : ID number of the recipe
     */
    @GetMapping("/recipe/delete/{id}")
    public void deleteRecipeById(HttpSession session, @PathVariable(value = "id")long id){
    User user = (User) session.getAttribute("LoggedInUser");
    if (user == null){
        if(recipeService.findByID(id).getCreatedBy() == null){
            recipeService.deleteById(id);
       }
   }
    else{
        User auther = recipeService.findByID(id).getCreatedBy();
        if(auther != null){
            if(aother.getID() == user.getID()){
	            recipeService.deleteById(id);
            }
        }
    }
    }
}

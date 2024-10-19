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
    public Recipe getUserById(@PathVariable(value = "id") long id) {
        return recipeService.findByID(id);
    }

    /**
     * Á þetta einu sinni að vera endpoint? Ekki endilega til að birta í viðmóti as
     * is
     * 
     * @param id
     * @return
     */
    @GetMapping("/recipe/id/{id}/totalpurch")
    @ResponseBody
    public int getTotalPurchaseCost(@PathVariable(value = "id") long id) {
        return recipeService.getTotalPurchaseCost(id);
    }

    /**
     * Á þetta einu sinni að vera endpoint? Ekki endilega til að birta í viðmóti as
     * is
     * 
     * @param id
     * @return
     */
    @GetMapping("/recipe/id/{id}/totalIng")
    @ResponseBody
    public double getTotalIngredientCost(@PathVariable(value = "id") long id) {
        return recipeService.getTotalIngredientCost(id);
    }

    @GetMapping("/recipe/id/{id}/personal")
    public double getPersonalizedPurchaseCost(@PathVariable(value = "id") long id, HttpSession session) {
        User user = (User) session.getAttribute("LoggedInUser");
        return recipeService.getPersonalizedPurchaseCost(user, id);
    }

}

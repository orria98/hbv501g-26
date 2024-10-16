package hbv501g.recipes.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import hbv501g.recipes.Persistence.Entities.Ingredient;
import hbv501g.recipes.Persistence.Entities.Recipe;
import hbv501g.recipes.Persistence.Entities.User;
import hbv501g.recipes.Services.IngredientService;
import hbv501g.recipes.Services.RecipeService;
import hbv501g.recipes.Services.UserService;

import java.util.List;

@RestController
public class HomeController {
    private IngredientService ingredientService;
    private RecipeService recipeService;
    private UserService userService;

    @Autowired
    public HomeController(IngredientService ingredientService, RecipeService recipeService, UserService userService) {
        this.ingredientService = ingredientService;
        this.recipeService = recipeService;
        this.userService = userService;
    }

    @GetMapping("/init")
    public void initAll() {
        List<Ingredient> ingredients = ingredientService.initIngredients();
        List<Recipe> recipes = recipeService.initRecipes();
        List<User> users = userService.initUsers();

        System.out.println(
                "ingredients: " + ingredients.size() + "  recipes: " + recipes.size() + "  users: " + users.size());

        Ingredient ingredient;
        Recipe recipe;
        User user;



        for (int i = 0; i < recipes.size(); i++) {
            recipe = recipes.get(i);
            recipe.setCreatedBy(users.get(1));
            recipeService.update(recipe);
        }


        users = userService.findAll();

        for (int i = 0; i < ingredients.size(); i++) {
            ingredient = ingredients.get(i);
            ingredient.setCreatedBy(users.get(1));
            ingredientService.update(ingredient);
        }

        // users = userService.findAll();
        // ingredients = ingredientService.findAll();
        // ingredient = ingredients.get(0);

        // for(int i = 0; i < users.size(); i++){
        //     user = users.get(i);
        //     user.addIngredientMeasurement(new IngredientMeasurement(ingredient, Unit.G, 20+i*10));
        //     userService.update(user);
        // }


        



    }
}

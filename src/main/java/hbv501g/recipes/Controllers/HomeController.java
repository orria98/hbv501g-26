package hbv501g.recipes.Controllers;

import org.hibernate.Remove;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import hbv501g.recipes.Persistence.Entities.Ingredient;
import hbv501g.recipes.Persistence.Entities.IngredientMeasurement;
import hbv501g.recipes.Persistence.Entities.Recipe;
import hbv501g.recipes.Persistence.Entities.Unit;
import hbv501g.recipes.Persistence.Entities.User;
import hbv501g.recipes.Services.IngredientService;
import hbv501g.recipes.Services.RecipeService;
import hbv501g.recipes.Services.UserService;

import java.util.List;

/**
 * Klasi sem upphafsstillir gagnagrunn með gögnum sem tengjast. Tímabundið, ekki
 * hluti af lokaskilum
 */
@RestController
@Remove
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

    /**
     * Endpoint to initialize the database with some basic data
     * 
     * @return String with a message about the success of the initialization
     */
    @Remove
    @GetMapping("/initialize")
    public String initializeData() {
        if (!ingredientService.findAll().isEmpty() || !recipeService.findAll().isEmpty()
                || !userService.findAll().isEmpty()) {
            return "Ekki hægt að upphafsstilla gögn, þar sem gagnagrunnur er ekki tómur.";
        }
        List<User> users;
        List<Ingredient> ingredients;
        List<Recipe> recipes;

        Ingredient ingredient;
        Recipe recipe;
        User user;

        users = userService.findAll();

        if (users.size() == 0) {
            user = new User("Jón", "jon123", "jon123@gmail.com");
            userService.save(user);

            user = new User("Superman", "123", "superman@gmail.com");
            userService.save(user);

            user = new User("admin", "admin", "admin@hi.is");
            userService.save(user);

            users = userService.findAll();
        }

        ingredients = ingredientService.findAll();

        if (ingredients.size() == 0) {
            ingredient = new Ingredient("ger", Unit.G, 25, 250, "Krónan", "Gestus");
            ingredientService.save(users.get(2).getID(), ingredient);

            ingredient = new Ingredient("hveiti", Unit.G, 2000, 500, "Bónus", "Kornax");
            ingredientService.save(users.get(2).getID(), ingredient);

            ingredient = new Ingredient("sykur", Unit.G, 1000, 400, "Costco", "Kirkland");
            ingredientService.save(users.get(0).getID(), ingredient);

            ingredient = new Ingredient("vatn", Unit.ML, 1000, 200);
            ingredientService.save(users.get(1).getID(), ingredient);

            ingredient = new Ingredient("Ostur", Unit.G, 500, 1200, "Bónus", "Gotti");
            ingredient.setPrivate(true);
            ingredientService.save(users.get(2).getID(), ingredient);

            ingredients = ingredientService.findAll();
        }

        users = userService.findAll();
        recipes = recipeService.findAll();

        if (recipes.size() == 0) {
            recipe = new Recipe();
            recipe.setTitle("Pizza");
            recipe.addIngredientMeasurement(new IngredientMeasurement(ingredients.get(0), Unit.G, 20));
            recipe.addIngredientMeasurement(new IngredientMeasurement(ingredients.get(1), Unit.G, 700));
            recipe.addIngredientMeasurement(new IngredientMeasurement(ingredients.get(3), Unit.ML, 250));
            recipe.addIngredientMeasurement(new IngredientMeasurement());
            recipe.setCreatedBy(users.get(2));
            recipeService.save(recipe);

            recipe = new Recipe();
            recipe.setTitle("Steik");
            recipeService.save(recipe);

            recipe = new Recipe();
            recipe.setTitle("Vatnsglas");
            recipe.setCreatedBy(users.get(0));
            recipe.setPrivate(true);
            recipe.addIngredientMeasurement(new IngredientMeasurement(ingredients.get(3), Unit.ML, 225));
            recipeService.save(recipe);

            recipes = recipeService.findAll();
        }

        users = userService.findAll();

        user = users.get(0);
        user.addIngredientMeasurement(new IngredientMeasurement(ingredients.get(3), Unit.ML, 10000));
        user.addIngredientMeasurement(new IngredientMeasurement(ingredients.get(1), Unit.G, 300));
        userService.update(user);

        user = users.get(2);
        user.addIngredientMeasurement(new IngredientMeasurement(ingredients.get(1), Unit.G, 300));
        user.addIngredientMeasurement(new IngredientMeasurement(ingredients.get(2), Unit.G, 1200));
        user.addIngredientMeasurement(new IngredientMeasurement(ingredients.get(3), Unit.ML, 10000));
        // user.addIngredientMeasurement(new IngredientMeasurement());
        userService.update(user);

        return String.format("%d users, %d ingredients and %d recipes have been initialized", users.size(),
                ingredients.size(), recipes.size());
    }

}

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
     * Upphafsstillir töflu með einhverjum gildum, með tengingum á milli mismunandi
     * entity
     */
    @Remove
    @GetMapping("/init")
    public String initAll() {
        if (!ingredientService.findAll().isEmpty() || !recipeService.findAll().isEmpty()
                || !userService.findAll().isEmpty())
            return "Ekki hægt að upphafsstilla gögn, þar sem gagnagrunnur er ekki tómur.";

        List<Ingredient> ingredients = ingredientService.initIngredients();
        List<Recipe> recipes = recipeService.initRecipes();
        List<User> users = userService.initUsers();

        Ingredient ingredient;
        Recipe recipe;
        User user;

        for (int i = 0; i < recipes.size(); i++) {
            recipe = recipes.get(i);
            ingredient = ingredients.get(i % (ingredients.size() - 1));
            recipe.setCreatedBy(users.get(1));
            recipe.addIngredientMeasurement(new IngredientMeasurement(ingredient, Unit.ML, (i + 1) * 1000));
            recipeService.update(recipe);
        }

        users = userService.findAll();

        for (int i = 0; i < ingredients.size(); i++) {
            ingredient = ingredients.get(i);
            ingredient.setCreatedBy(users.get(1));
            ingredientService.update(ingredient);
        }

        users = userService.findAll();
        ingredients = ingredientService.findAll();
        ingredient = ingredients.get(0);

        for (int i = 0; i < users.size(); i++) {
            user = users.get(i);
            userService.addPantryItem(user, ingredient.getID(), Unit.G, 20 + i * 10);
            userService.update(user);
        }

        return String.format("%d users, %d ingredients and %d recipes have been initialized", users.size(),
                ingredients.size(), recipes.size());

    }

    /**
     * Initialize fall sem gerir fallegri gögn
     */
    @Remove
    @GetMapping("/initialize")
    public String initializeData() {
        if (!ingredientService.findAll().isEmpty() || !recipeService.findAll().isEmpty()
                || !userService.findAll().isEmpty())
            return "Ekki hægt að upphafsstilla gögn, þar sem gagnagrunnur er ekki tómur.";

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
            ingredient.setCreatedBy(users.get(2));
            ingredientService.save(ingredient);

            ingredient = new Ingredient("hveiti", Unit.G, 2000, 500, "Bónus", "Kornax");
            ingredient.setCreatedBy(users.get(2));
            ingredientService.save(ingredient);

            ingredient = new Ingredient("sykur", Unit.G, 1000, 400, "Costco", "Kirkland");
            ingredient.setCreatedBy(users.get(0));
            ingredientService.save(ingredient);

            ingredient = new Ingredient("vatn", Unit.ML, 1000, 200);
            ingredient.setCreatedBy(users.get(1));
            ingredientService.save(ingredient);

            ingredient = new Ingredient("Ostur", Unit.G, 500, 1200, "Bónus", "Gotti");
            ingredient.setCreatedBy(users.get(2));
            ingredient.setPrivate(true);
            ingredientService.save(ingredient);

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
        //user.addIngredientMeasurement(new IngredientMeasurement());
        userService.update(user);

        return String.format("%d users, %d ingredients and %d recipes have been initialized", users.size(),
                ingredients.size(), recipes.size());
    }

}

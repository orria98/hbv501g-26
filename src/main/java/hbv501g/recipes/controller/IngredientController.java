/**
 * Controller fyrir hráefni. Ákvarðar hvar endpoints eru (með GetMapping)
 * Kallar á aðferðir í service til að nýta endpoints.
 * Heldur utan um andpoints fyrir ingredient.
 * 
 */
package hbv501g.recipes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import hbv501g.recipes.model.Ingredient;
import hbv501g.recipes.service.IngredientService;
import hbv501g.recipes.enums.*;

import java.util.List;

@RestController
public class IngredientController {

    @Autowired
    private IngredientService ingredientService;

    /**
     * 
     * @return texti sem prentast í localhost
     */
    @GetMapping("/")
    public String index() {
        return "You are currently running the recipe program. Please see the project readme for information on possible endpoints and actions.";
    }

    /**
     * Nær í hráefni eftir nafni.
     * 
     * @param name : nafn hráefnis
     * @return one or no ingredient
     */
    @GetMapping("/Ingredient/name/{name}")
    @ResponseBody
    public Ingredient getIngredientByName(@PathVariable(value = "name") String name) {
        return ingredientService.getIngredientByName(name);
    }

    /**
     * Nær í hráefni eftir id.
     * 
     * @param id
     * @return
     */
    @GetMapping("/Ingredient/{id}")
    @ResponseBody
    public Ingredient getIngredientById(@PathVariable(value = "id") Long id) {
        return ingredientService.getIngredientById(id);
    }

    /**
     * Endpoint sem skilar öllum ingredients í töflunni :)
     * 
     * @return all ingredients in db
     */
    @GetMapping("/Ingredient/all")
    @ResponseBody
    public List<Ingredient> getAllIngredients() {
        return ingredientService.getAllIngredients();
    }

    /**
     * Setur nokkur dummy gildi inn í gagnagrunninn ef taflan er tóm.
     * 
     * @return innihald Ingredient töflunnar
     */
    @GetMapping("/Ingredient/init")
    @ResponseBody
    public List<Ingredient> InitIngredients() {
        List<Ingredient> AllIngredients = ingredientService.getAllIngredients();

        if (AllIngredients.size() == 0) {
            Ingredient ingredient = new Ingredient("ger", Unit.G, 25, 250);
            ingredientService.createIngredient(ingredient);

            ingredient = new Ingredient("hveiti", Unit.G, 2000, 500, "Bónus", "Kornax");
            ingredientService.createIngredient(ingredient);

            ingredient = new Ingredient("sykur", Unit.G, 1000, 400);
            ingredientService.createIngredient(ingredient);

            ingredient = new Ingredient("vatn", Unit.ML, 1000, 200);
            ingredientService.createIngredient(ingredient);

            AllIngredients = ingredientService.getAllIngredients();
        }

        return AllIngredients;
    }


}

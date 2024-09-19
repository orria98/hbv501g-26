/**
 * Controller fyrir forritið. Ákvarðar hvar endpoints eru (með GetMapping)
 * 
 */
package hbv501g.recipes.controller;

import org.hibernate.Remove;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import hbv501g.recipes.model.Ingredient;
import hbv501g.recipes.service.IngredientService;

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
    @GetMapping("/GetIngredient")
    @ResponseBody
    public Ingredient getIngredientByName(@RequestParam(value = "name", defaultValue = "hveiti") String name) {
        return ingredientService.getIngredientByName(name);
    }

    /**
     * Nær í hráefni eftir id.
     * 
     * @param id
     * @return
     */
    @GetMapping("/GetIngredientByID")
    @ResponseBody
    public Ingredient getIngredientById(@RequestParam(value = "id", defaultValue = "1") String id) {
        return ingredientService.getIngredientById(Long.parseLong(id));
    }

    /**
     * Nær í hráefni eftir id.
     * 
     * @param id
     * @return
     */
    @GetMapping("/Ingredient/{id}")
    @ResponseBody
    public Ingredient getTheIngredientById(@PathVariable(value = "id") Long id) {
        return ingredientService.getIngredientById(id);
    }

    /**
     * Endpoint sem skilar öllum ingredients í töflunni :)
     * 
     * @return all ingredients in db
     */
    @GetMapping("/GetAllIngredients")
    @ResponseBody
    public List<Ingredient> getAllIngredients() {
        return ingredientService.getAllIngredients();
    }

    /**
     * Setur nokkur dummy gildi inn í gagnagrunninn ef taflan er tóm.
     * 
     * @return innihald Ingredient töflunnar
     */
    @GetMapping("/InitIngredients")
    @ResponseBody
    public List<Ingredient> InitIngredients() {
        List<Ingredient> AllIngredients = ingredientService.getAllIngredients();

        if (AllIngredients.size() == 0) {
            Ingredient ingredient = new Ingredient("ger", "g", 25, 250);
            ingredientService.createIngredient(ingredient);

            ingredient = new Ingredient("hveiti", "kg", 2, 500);
            ingredientService.createIngredient(ingredient);

            ingredient = new Ingredient("sykur", "kg", 1, 400);
            ingredientService.createIngredient(ingredient);

            AllIngredients = ingredientService.getAllIngredients();
        }

        return AllIngredients;
    }

    // Gerir faux ingredient með http://localhost:8080/DummyIngredient?name=sykur
    @GetMapping("/DummyIngredient")
    @ResponseBody
    @Remove
    public Ingredient ingredient(@RequestParam(value = "name", defaultValue = "Hveiti") String name) {
        return new Ingredient(name, "g", 100, 250);
    }

    @GetMapping("/GetHveiti")
    @ResponseBody
    @Remove
    public Ingredient getHveiti() {
        return ingredientService.getIngredientByName("hveiti");
    }

}

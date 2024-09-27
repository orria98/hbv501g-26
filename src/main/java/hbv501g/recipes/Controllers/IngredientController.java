/**
 * Controller fyrir hráefni. Ákvarðar hvar ingredient endpoints eru (með GetMapping og requestMapping)
 * Kallar á aðferðir í service til að nýta endpoints.
 * 
 */
package hbv501g.recipes.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import hbv501g.recipes.Persistence.Entities.Ingredient;
import hbv501g.recipes.Services.IngredientService;

import java.util.List;

@RestController
public class IngredientController {
    private IngredientService ingredientService;

    @Autowired
    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    // Til að prenta línu frekar en að birta index.html
    // @RequestMapping("/")
    // public String index() {
    // return "You are currently running the recipe program. Please see the project
    // readme for information on possible endpoints and actions.";
    // }



    /**
     * Nær í hráefni eftir id.
     * 
     * @param id
     * @return
     */
    @GetMapping("/ingredient/id/{id}")
    @ResponseBody
    public Ingredient getIngredientById(@PathVariable(value = "id") Long id) {
        return ingredientService.findByID(id);
    }

    /**
     * Endpoint sem skilar öllum ingredients í töflunni :)
     * 
     * @return all ingredients in db
     */
    @GetMapping("/ingredient/all")
    @ResponseBody
    public List<Ingredient> getAllIngredients() {
        return ingredientService.findAll();
    }

    @GetMapping("/ingredient/init")
    @ResponseBody
    public List<Ingredient> InitIngredients() {
        return ingredientService.initIngredients();
    }

    /**
     * Nær í hráefni eftir nafni.
     *
     * @param name : nafn hráefnis
     * @return one or no ingredient
     */
    @GetMapping("/ingredient/name/{name}")
    @ResponseBody
    public Ingredient getIngredientByName(@PathVariable(value = "name") String name) {
        return ingredientService.findByName(name);
    }

}

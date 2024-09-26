/**
 * Controller fyrir hráefni. Ákvarðar hvar endpoints eru (með GetMapping)
 * Kallar á aðferðir í service til að nýta endpoints.
 * Heldur utan um andpoints fyrir ingredient.
 * 
 */
package hbv501g.recipes.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import hbv501g.recipes.Persistence.Entities.Ingredient;
import hbv501g.recipes.Persistence.enums.*;
import hbv501g.recipes.Services.IngredientService;


import java.util.List;

@RestController // eða bara controller??
public class IngredientController {
    private IngredientService ingredientService;

    @Autowired
    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    // /**
    //  * 
    //  * @return texti sem prentast í localhost
    //  */
    // @RequestMapping("/")
    // public String index() {
    // return "You are currently running the recipe program. Please see the project readme for information on possible endpoints and actions.";
    // }

    @GetMapping("/ingredient/init")
    @ResponseBody
    public List<Ingredient> InitIngredients() {
        List<Ingredient> AllIngredients = ingredientService.findAll();

        if (AllIngredients.size() == 0) {
            Ingredient ingredient = new Ingredient("ger", Unit.G, 25, 250);
            ingredientService.save(ingredient);

            ingredient = new Ingredient("hveiti", Unit.G, 2000, 500, "Bónus", "Kornax");
            ingredientService.save(ingredient);

            ingredient = new Ingredient("sykur", Unit.G, 1000, 400);
            ingredientService.save(ingredient);

            ingredient = new Ingredient("vatn", Unit.ML, 1000, 200);
            ingredientService.save(ingredient);

            AllIngredients = ingredientService.findAll();
        }

        return AllIngredients;
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
        return ingredientService.findByName(name);
    }

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

    @PostMapping("/ingredients")
    Ingredient createNewIngredient(@RequestBody Ingredient newIngredient) {
        return ingredientService.save(newIngredient);
    }

    // ÞETTA VIRKAR TIL AÐ BREYTA NAFNI!!!!!
    @RequestMapping(value = "/ingredient/put/name/{oldName}/{newName}", method = { RequestMethod.GET,
            RequestMethod.PUT })
    @ResponseBody
    public Ingredient change(@PathVariable String oldName, @PathVariable String newName) {
        Ingredient ingredient = getIngredientByName(oldName);
        ingredient.setName(newName);
        ingredientService.update(ingredient);
        return ingredient;
    }


    @GetMapping("ingredient/all/ordered")
    public List<Ingredient> getOrderedIngredients(){
        return ingredientService.findOrderedIngredients();
    }
}

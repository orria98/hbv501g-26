/**
 * Controller fyrir Ingredient. Ákvarðar hvar ingredient endpoints eru (með GetMapping og requestMapping)
 * Kallar á aðferðir í service til að nýta endpoints.
 * 
 */
package hbv501g.recipes.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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

    /**
     * Endpoint sem nær í hráefni með gefið id.
     * 
     * @param id
     * @return Ingredient með það id
     */
    @GetMapping("/ingredient/id/{id}")
    @ResponseBody
    public Ingredient getIngredientById(@PathVariable(value = "id") Long id) {
        return ingredientService.findByID(id);
    }

    /**
     * Endpoint sem skilar öllum ingredients í töflunni
     * 
     * @return all ingredients in db
     */
    @GetMapping("/ingredient/all")
    @ResponseBody
    public List<Ingredient> getAllIngredients() {
        return ingredientService.findAll();
    }

    /**
     * Initializes a few ingredients. Ekki hluti af skilum, en 
     * gerir það auðveldara að prófa hvort forritið virki.
     * 
     * @return some ingredients
     */
    @GetMapping("/ingredient/init")
    @ResponseBody
    public List<Ingredient> InitIngredients() {
        return ingredientService.initIngredients();
    }

    /**
     * Endpoint sem nær í hráefni eftir nafni. Ekki hluti 
     * af endpoints fyrir þetta verkefni.
     *
     * @param title : nafn hráefnis
     * @return one or no ingredient
     */
    @GetMapping("/ingredient/title/{title}")
    @ResponseBody
    public Ingredient getIngredientByTitle(@PathVariable(value = "title") String title) {
        return ingredientService.findByTitle(title);
    }

        // ÞETTA VIRKAR TIL AÐ BREYTA NAFNI!!!!!
    @RequestMapping(value = "/ingredient/put/name/{oldName}/{newName}", method = { RequestMethod.GET,
            RequestMethod.PUT })
    @ResponseBody
    public Ingredient change(@PathVariable String oldName, @PathVariable String newName) {
        Ingredient ingredient = ingredientService.findByTitle(oldName);
        ingredient.setTitle(newName);
        ingredientService.update(ingredient);
        return ingredient;
    }


    @GetMapping("ingredient/all/ordered")
    public List<Ingredient> getOrderedIngredients(){
        return ingredientService.findOrderedIngredients();
    }



}

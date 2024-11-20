/**
 * Controller fyrir Ingredient. Ákvarðar hvar ingredient endpoints eru (með GetMapping og requestMapping)
 * Kallar á aðferðir í service til að nýta endpoints.
 * 
 */
package hbv501g.recipes.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import hbv501g.recipes.Persistence.Entities.Ingredient;
import hbv501g.recipes.Services.IngredientService;

import java.util.List;

import java.util.Map;

@RestController
public class IngredientController {
    private IngredientService ingredientService;

    @Autowired
    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    /**
     * Endpoint to find an ingredient with the given id, if one exists and is
     * accessible to the current user
     * 
     * @param id  - the id of the ingredient
     * @param uid - the id of the current user, or 0 no user is logged in
     * @return the requested ingredient, or null
     */
    @GetMapping("/ingredient/id/{id}")
    @ResponseBody
    public Ingredient getIngredientById(@PathVariable long id,
            @RequestParam(defaultValue = "0") long uid) {
        return ingredientService.findAccessibleByID(id, uid);
    }

    /**
     * Finds all ingredients that are accessible to the current user. If no user is
     * logged in, this is only the public ingredients
     * 
     * @param uid - the id of the current user, or 0 no user is logged in
     * @return all ingredients accessible to the current user
     */
    @GetMapping("/ingredient/all")
    @ResponseBody
    public List<Ingredient> getAllIngredients(@RequestParam(defaultValue = "0") long uid) {
        return ingredientService.findAccessibleToUser(uid);
    }

    /**
     * Endpoint to create a new ingredient for the current user. No ingredient is
     * created if no user is logged in
     *
     * @param uid           - the id of the current user, or 0 no user is logged in
     * @param newIngredient - an Ingredient that is being saved
     * @return the new Ingredient
     */
    @PostMapping("/ingredient/created")
    @ResponseBody
    public Ingredient saveIngredient(@RequestParam(defaultValue = "0") long uid,
            @RequestBody Ingredient newIngredient) {
        return ingredientService.save(uid, newIngredient);
    }

    /**
     * Deletes an ingredient with the given id, if one exists and it belongs to the
     * current user
     * 
     * @param uid : the id of the current user, or 0 no user is logged in
     * @param id  : ID number of the ingredient
     */
    @DeleteMapping("/ingredient/delete/{id}")
    public void deleteIngredientById(@RequestParam(defaultValue = "0") long uid, @PathVariable long id) {
        ingredientService.deleteById(uid, id);
    }

    /**
     * Endpoint for updating the title of an ingredient
     * 
     * @param uid  : the id of the current user, or 0 no user is logged in
     * @param id   : ID of the ingredient
     * @param body : Request body containing the new title
     * @return : The updated ingredient
     */
    @PatchMapping("/ingredient/updateTitle/{id}")
    public Ingredient updateIngredientName(@RequestParam(defaultValue = "0") long uid,
            @PathVariable long id,
            @RequestBody Map<String, String> body) {
        String newTitle = body.get("title");
        return ingredientService.updateIngredientTitle(id, newTitle, uid);
    }

    /** Not part of any assignment */
    @GetMapping("/ingredient/all/ordered")
    public List<Ingredient> getOrderedIngredients() {
        return ingredientService.findOrderedIngredients();
    }

    /**
     * Endpoint sem nær í hráefni með gefið id.
     * 
     * @param id
     * @return Ingredient með það id
     */
    @Deprecated
    @GetMapping("/ingredient/allId/{id}")
    @ResponseBody
    public Ingredient oldGetIngredientById(@PathVariable long id) {
        return ingredientService.findByID(id);
    }

    /**
     * Endpoint sem skilar öllum ingredients í töflunni
     * 
     * @return all ingredients in db
     */
    @Deprecated
    @GetMapping("/ingredient/oldAll")
    @ResponseBody
    public List<Ingredient> oldGetAllIngredients() {
        return ingredientService.findAll();
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
    public Ingredient getIngredientByTitle(@PathVariable String title) {
        return ingredientService.findByTitle(title);
    }
}

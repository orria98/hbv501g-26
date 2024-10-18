/**
 * Controller fyrir Ingredient. Ákvarðar hvar ingredient endpoints eru (með GetMapping og requestMapping)
 * Kallar á aðferðir í service til að nýta endpoints.
 * 
 */
package hbv501g.recipes.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

import hbv501g.recipes.Persistence.Entities.Ingredient;
import hbv501g.recipes.Persistence.Entities.Unit;
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

    /**
     * Endpoint createds new idgrediet for the database
     *
     * @param title : String value
     * @param unit : is a Enum of unit
     * @param quantity : double value
     * @param price : double value
     * @param store : String value
     * @param brand : Stirng value
     */
    @GetMapping("ingredient/created")
    public Ingredient saveInredient(
				    @RequestParam String title,
				    @RequestParam Enum<Unit> unit,
				    @RequestParam double quantity,
				    @RequestParam double price,
				    @RequestParam String store,
				    @RequestParam String brand
				    )
    {
	Ingredient ingredient = new Ingredient(title, unit, quantity, price, store, brand);
	return ingredientService.save(ingredient);
    }
    
    /**
     * Endpoint that finds an ingredient by id and
     * removes it form the database.
     *
     * @param id : ID number of the ingreadient.
     */
    @GetMapping("ingredient/delet/{id}")
    public void deleteIngredientById(@PathVariable(value = "id") long id)
    {
	ingredientService.deleteById(id);
    }

    // Ekki hluti af neinum skilum held ég
    @GetMapping("ingredient/all/ordered")
    public List<Ingredient> getOrderedIngredients(){
        return ingredientService.findOrderedIngredients();
    }




}

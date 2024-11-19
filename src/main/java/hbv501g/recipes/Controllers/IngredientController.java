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

import hbv501g.recipes.Persistence.Entities.Ingredient;
import hbv501g.recipes.Persistence.Entities.User;
import hbv501g.recipes.Services.IngredientService;

import jakarta.servlet.http.HttpSession;

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
     * accessible to the
     * current user
     * 
     * @param session - the current http session
     * @param id      - the id of the ingredient
     * @return the requested ingredient, or null
     */
    @GetMapping("/ingredient/id/{id}")
    @ResponseBody
    public Ingredient getIngredientById(@PathVariable(value = "id") long id, HttpSession session) {
        User user = (User) session.getAttribute("LoggedInUser");
        return ingredientService.findAccessibleByID(id, user);
    }

    /**
     * Finds all ingredients that are accessible to the current user. If no user is
     * logged in, this is only the public ingredients
     * 
     * @param session - the current http session
     * @return all ingredients accessible to the current user
     */
    @GetMapping("/ingredient/all")
    @ResponseBody
    public List<Ingredient> getAllIngredients(HttpSession session) {
        User user = (User) session.getAttribute("LoggedInUser");
        return ingredientService.findAccessibleToUser(user);
    }

    /**
     * Endpoint to create a new ingredient for the current user. No ingredient is
     * created if no user is logged in
     *
     * @param session       - The current http session
     * @param newIngredient - an Ingredient that is being saved
     * @return the new Ingredient
     */
    @PostMapping("/ingredient/created")
    @ResponseBody
    public Ingredient saveIngredient(HttpSession session, @RequestBody Ingredient newIngredient){
	    return ingredientService.save((User) session.getAttribute("LoggedInUser"), newIngredient);
    }

    /**
     * Deletes an ingredient with the given id, if one exists and it belongs to the
     * current user
     * 
     * @param session : is the current session
     * @param id      : ID number of the ingredient
     */
    @DeleteMapping("/ingredient/delete/{id}")
    public void deleteIngredientById(HttpSession session, @PathVariable(value = "id") long id) {
        ingredientService.deleteById((User) session.getAttribute("LoggedInUser"), id);
    }

    /**
     * Endpoint for updating the title of an ingredient
     * 
     * @param session : Current session
     * @param id      : ID of the ingredient
     * @param body    : Request body containing the new title
     * @return : The updated ingredient
     */
    @PatchMapping("/ingredient/updateTitle/{id}")
    public Ingredient updateIngredientName(HttpSession session, @PathVariable(value = "id") long id,
            @RequestBody Map<String, String> body) {
        User user = (User) session.getAttribute("LoggedInUser");
        String newTitle = body.get("title");
        return ingredientService.updateIngredientTitle(id, newTitle, user);
    }

    /** Not part of any assignment */
    @GetMapping("/ingredient/all/ordered")
    public List<Ingredient> getOrderedIngredients(){
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
    public Ingredient oldGetIngredientById(@PathVariable(value = "id") long id) {
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
    public Ingredient getIngredientByTitle(@PathVariable(value = "title") String title) {
        return ingredientService.findByTitle(title);
    }

    /**
     * Initializes a few ingredients. Ekki hluti af skilum, en
     * gerir það auðveldara að prófa hvort forritið virki.
     * 
     * @return some ingredients
     */
    // @GetMapping("/ingredient/init")
    // @ResponseBody
    // public List<Ingredient> InitIngredients() {
    // return ingredientService.initIngredients();
    // }
}

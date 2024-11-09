/**
 * Controller fyrir Ingredient. Ákvarðar hvar ingredient endpoints eru (með GetMapping og requestMapping)
 * Kallar á aðferðir í service til að nýta endpoints.
 * 
 */
package hbv501g.recipes.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import hbv501g.recipes.Persistence.Entities.Ingredient;
import hbv501g.recipes.Persistence.Entities.User;
import hbv501g.recipes.Services.IngredientService;

import jakarta.servlet.http.HttpSession;

import java.util.List;
import java.util.Map;
import java.time.LocalDate;

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
    public Ingredient getIngredientById(@PathVariable(value = "id") long id) {
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
     * @param session       : is the current session
     * @param newIngredient - a Ingredient that is being saved
     * @return the new Ingredient
     */
    @RequestMapping("ingredient/created")
    public Ingredient saveIngredient(HttpSession session, @RequestBody Ingredient newIngredient) {
        return ingredientService.save((User) session.getAttribute("LoggedInUser"), newIngredient);
    }

    /**
     * Endpoint that finds an ingredient by id and
     * removes it form the database if the uesr
     * own the ingredient.
     * 
     * @param session : is the current session
     * @param id      : ID number of the ingredient
     */
    @RequestMapping("ingredient/delete/{id}")
    public void deleteIngredientById(HttpSession session, @PathVariable(value = "id") long id) {
        ingredientService.deleteById((User) session.getAttribute("LoggedInUser"), id);
    }

    @PatchMapping("ingredient/updateTitle/{id}")
    public Ingredient updateIngredientName(HttpSession session, @PathVariable(value = "id") long id,
            @RequestBody Map<String, String> body) {
        Ingredient ingredient = ingredientService.findByID(id);
        User user = (User) session.getAttribute("LoggedInUser");

        if (ingredient == null || user == null || ingredient.getCreatedBy().getID() != user.getID()) {
            return null;
        }

        String newTitle = body.get("title");
        System.out.println(newTitle);
        ingredient.setTitle(newTitle);
        return ingredientService.save(user, ingredient);
    }

    // Ekki hluti af neinum skilum
    @GetMapping("ingredient/all/ordered")
    public List<Ingredient> getOrderedIngredients() {
        return ingredientService.findOrderedIngredients();
    }

}

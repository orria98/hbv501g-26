package hbv501g.recipes.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import hbv501g.recipes.Persistence.Entities.IngredientMeasurement;
import hbv501g.recipes.Persistence.Entities.Unit;
import hbv501g.recipes.Persistence.Entities.User;
import hbv501g.recipes.Services.UserService;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * All endpoints for the user table will be here
 */
@RestController
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/init")
    @ResponseBody
    public List<User> initUsers() {
        return userService.initUsers();
    }

    /**
     * Endpoint to get all users from the db
     * 
     * @return all users
     */
    @GetMapping("/user/all")
    @ResponseBody
    public List<User> getAllUsers() {
        return userService.findAll();
    }

    @GetMapping("/user/id/{id}")
    public User getUserById(@PathVariable(value = "id") Long id) {
        return userService.findByID(id);
    }

    /**
     * Endpoint to get the pantry of a user with the specified id
     * 
     * @param id - user id
     * @return
     */
    @GetMapping("/user/id/{id}/pantry")
    public List<IngredientMeasurement> getUserPantry(@PathVariable(value = "id") Long id) {
        return userService.findUserPantry(id);
    }

    /**
     * Deletes a pantry item for specified user, where the pantry item uses the
     * specified ingredient
     * 
     * @param uid id of user owning pantry
     * @param iid id of ingredient in pantry item to delete
     */
    @RequestMapping(value = "/user/pantry/delete", method = { RequestMethod.GET, RequestMethod.DELETE })
    public void deletePantryItem(@RequestParam Long uid, @RequestParam Long iid) {
        userService.deletePantryItem(uid, iid);
    }

    /**
     * Adds to pantry
     * 
     * @param uid
     * @param iid
     * @param unit - t.d. G, ML...
     * @param qty
     * @return
     */
    @RequestMapping(value = "/user/pantry/add", method = { RequestMethod.GET, RequestMethod.PUT })
    @ResponseBody
    public IngredientMeasurement addPantryItem(@RequestParam Long uid, @RequestParam Long iid, @RequestParam Unit unit,
            @RequestParam double qty) {

        return userService.addPantryItem(uid, iid, unit, qty);
    }

}

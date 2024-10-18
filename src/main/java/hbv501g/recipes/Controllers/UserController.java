package hbv501g.recipes.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
    public User getUserById(@PathVariable(value = "id") long id) {
        return userService.findByID(id);
    }

    /**
     * Gets the user who is currently logged in
     */
    @GetMapping("/user/curr")
    public User getCurrentUser(HttpSession session) {
        return (User) session.getAttribute("LoggedInUser");
    }

    /**
     * Logs in a user with a given username and password. This is stored in the
     * session. If the user does not exist, or the password is wrong, then the
     * method returns null
     * Example of use:
     * http://localhost:8080/user/login?username=admin&password=admin
     * 
     * @param session
     * @param username
     * @param password
     * @return the user with the given username or password, or null
     */
    @RequestMapping(value = "/user/login")
    public User login(HttpSession session, @RequestParam String username, @RequestParam String password) {
        if (userService.findByUsername(username) != null) {
            User exists = userService.login(userService.findByUsername(username));
            if (exists != null) {
                session.setAttribute("LoggedInUser", exists);
                return exists;
            }
        }
        return null;
    }

    /**
     * Makes a new user with the given username and password, if the username is
     * available, and returns the user. If the username is being used, the function
     * returns null. If a new user is made, it is then set as the current user.
     * Example of use: http://localhost:8080/user/signup?username=Sep&password=sep
     * 
     * @param session
     * @param username
     * @param password
     * @return a new user with the given username and password, or null if no user
     *         created
     */
    @RequestMapping(value = "user/signup")
    public User signup(HttpSession session, @RequestParam String username, @RequestParam String password) {
        if (userService.findByUsername(username) == null) {
            User newUser = new User(username, password);
            userService.save(newUser);
            session.setAttribute("LoggedInUser", newUser);
            return newUser;
        }
        return null;
    }

    /**
     * Endpoint to get the pantry of a user with the specified id
     * 
     * @param id : user id
     * @return pantry contents for user
     */
    @GetMapping("/user/id/{id}/pantry")
    public List<IngredientMeasurement> getUserPantry(@PathVariable(value = "id") long id) {
        return userService.findUserPantry(id);
    }

    /**
     * Deletes a pantry item for specified user, where the pantry item uses the
     * specified ingredient. Assumes only one item for each ingredient
     * 
     * @param uid id of user owning pantry
     * @param iid id of ingredient in pantry item to delete
     */
    @RequestMapping(value = "/user/pantry/delete", method = { RequestMethod.GET, RequestMethod.PUT })
    public void deletePantryItem(@RequestParam long uid, @RequestParam long iid) {
        userService.deletePantryItem(uid, iid);
    }

    /**
     * Adds to pantry, get if using url such as
     * http://localhost:8080/user/pantry/add?uid=1&iid=1&unit=G&qty=100
     * put if using put request on postman.
     * 
     * If the ingredient is already in the pantry, that item is returned and not
     * added to pantry
     * 
     * @param uid  user id
     * @param iid  ingredient id
     * @param unit - t.d. G, ML...
     * @param qty  quantity
     * @return
     */
    @RequestMapping(value = "/user/pantry/add", method = { RequestMethod.GET, RequestMethod.PUT })
    @ResponseBody
    public IngredientMeasurement addPantryItem(@RequestParam long uid, @RequestParam long iid, @RequestParam Unit unit,
            @RequestParam double qty) {

        return userService.addPantryItem(uid, iid, unit, qty);
    }

}

package hbv501g.recipes.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import hbv501g.recipes.Persistence.Entities.IngredientMeasurement;
import hbv501g.recipes.Persistence.Entities.Unit;
import hbv501g.recipes.Persistence.Entities.User;
import hbv501g.recipes.Services.UserService;
import jakarta.servlet.http.HttpSession;

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

    /**
     * Finds and returns a user with a given ID. Returns that user if any user has
     * the ID, otherwise it returns null
     * 
     * @param id - the userID of the requested user
     * @return the user with that id, or null
     */
    @GetMapping("/user/id/{id}")
    public User getUserById(@PathVariable(value = "id") long id) {
        return userService.findByID(id);
    }

    /**
     * Gets the user who is currently logged in. It is stored as LoggedInUser in the
     * current http session. Returns the user, or null if no user is logged in
     * 
     * @param session - The current http session
     * @return - The current user (or null)
     */
    @GetMapping("/user/curr")
    public User getCurrentUser(HttpSession session) {
        return (User) session.getAttribute("LoggedInUser");
    }

    /**
     * Logs in a user with a given username and password and stores it as the
     * current user/logged in user of the session. Returns the user, or null if the
     * login fails
     * Example of use:
     * http://localhost:8080/user/login?username=admin&password=admin
     * 
     * @param session  - The current http session
     * @param username - The username of the user logging in
     * @param password - The password of the user logging in
     * @return the user with the given username or password, or null
     */
    @GetMapping(value = "/user/login")
    public User login(HttpSession session, @RequestParam String username, @RequestParam String password) {
        User exists = userService.login(username, password);
        session.setAttribute("LoggedInUser", exists);
        return exists;
    }

    /**
     * Makes a new user with the given username and password, if the username is
     * available, and returns the user. If the username is being used, the function
     * returns null, and there is no user currently logged in. If a new user is
     * made, it is then set as the current user.
     * 
     * Example of use: http://localhost:8080/user/signup?username=Sep&password=sep
     * 
     * @param session  - The current http session
     * @param username - The username of the user signing up
     * @param password - The password of the user signing up
     * @return a new user with the given username and password, or null if no user
     *         created
     */
    @PostMapping(value = "user/signup")
    public User signup(HttpSession session, @RequestParam String username, @RequestParam String password) {
        User newUser = userService.signup(username, password);
        session.setAttribute("LoggedInUser", newUser);
        return newUser;
    }

    /**
     * Endpoint to get the pantry of the current user
     * 
     * @param session - The current http session
     * @return pantry contents for user
     */
    @GetMapping("/user/pantry")
    public List<IngredientMeasurement> getUserPantry(HttpSession session) {
        return userService.findUserPantry((User) session.getAttribute("LoggedInUser"));
    }

    /**
     * Deletes a pantry item for current user, where the pantry item uses the
     * specified ingredient. Assumes only one item for each ingredient
     * 
     * @param iid     - id of ingredient in pantry item to delete
     * @param session - the current session
     */
    // @RequestMapping(value = "/user/pantry/delete", method = { RequestMethod.GET, RequestMethod.PUT })
    @PutMapping("/user/pantry/delete")
    public void deletePantryItem(@RequestParam long iid, HttpSession session) {
        userService.deletePantryItem((User) session.getAttribute("LoggedInUser"), iid);
    }

    /**
     * If the ingredient is already in the pantry, that item is returned and not
     * added to pantry
     * 
     * @param uid  user id
     * @param iid  ingredient id
     * @param unit - t.d. G, ML...
     * @param qty  quantity
     * @return the ingredient measurement for the ingredient
     */
    // @RequestMapping(value = "/user/pantry/add", method = { RequestMethod.GET, RequestMethod.PUT })
    @PutMapping("/user/pantry/add")
    @ResponseBody
    public IngredientMeasurement addPantryItem(@RequestParam long iid, @RequestParam Unit unit,
            @RequestParam double qty, HttpSession session) {

        return userService.addPantryItem((User) session.getAttribute("LoggedInUser"), iid, unit, qty);
    }

    /*Not in any assignment */
    @GetMapping("/user/init")
    @ResponseBody
    public List<User> initUsers() {
        return userService.initUsers();
    }

}

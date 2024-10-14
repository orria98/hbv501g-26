package hbv501g.recipes.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import hbv501g.recipes.Persistence.Entities.IngredientMeasurement;
import hbv501g.recipes.Persistence.Entities.Recipe;
import hbv501g.recipes.Persistence.Entities.User;
import hbv501g.recipes.Services.UserService;

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
    public List<User> initUsers(){
        return userService.initUsers();
    }

    @GetMapping("/user/all")
    @ResponseBody
    public List<User> getAllUsers(){
        List<User> users = userService.findAll();

        for(User u: users){
            System.out.println("user " + u.getID() + " has " + u.getRecipesByUser().size() + " recipes" );
        }

        return users;
        //return userService.findAll();
    }

    @GetMapping("/user/id/{id}")
    @ResponseBody
    public User getUserById(@PathVariable(value = "id") Long id){
        return userService.findByID(id);
    }

    @GetMapping("/user/print/{id}")
    public void printUserPantry(@PathVariable(value = "id") Long id){
        User user = userService.findByID(id);
        List<IngredientMeasurement> userPantry = user.getPantry();
        System.out.println("Pantry of user: " + user.getUsername());
        for(int i = 0; i<userPantry.size(); i++){
            System.out.println(userPantry.get(i));
        }

        List<Recipe> recipes = user.getRecipesByUser();
        System.out.println("Recipes of user: " + user.getUsername());
        
        for(int i = 0; i<recipes.size(); i++){
            System.out.println(recipes.get(i));
        }
    }
}

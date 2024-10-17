package hbv501g.recipes.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
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
    public List<User> initUsers(){
        return userService.initUsers();
    }

    @GetMapping("/user/all")
    @ResponseBody
    public List<User> getAllUsers(){
        return userService.findAll();
    }

    @GetMapping("/user/id/{id}")
    public User getUserById(@PathVariable(value =  "id") Long id) {
        return userService.findByID(id);
    }

    @GetMapping("/user/curr")
    public User getCurrentUser(HttpSession session, Model model) {
        User sessionUser = (User) session.getAttribute("LoggedInUser");
        if(sessionUser  != null){
            model.addAttribute("LoggedInUser", sessionUser);
            return sessionUser;
        }
        return new User();
    }
    

    @RequestMapping(value = "/user/login/{username}/{password}", method={RequestMethod.GET, RequestMethod.POST})
    public User login(/*BindingResult result, */Model model, HttpSession session,@PathVariable(value = "username") String username, @PathVariable(value = "password") String password) {
        /*if(result.hasErrors()){
            return null;
        }*/
        User exists = userService.login(userService.findByUsername(username));
        if(exists != null){
            session.setAttribute("LoggedInUser", exists);
            model.addAttribute("LoggedInUser", exists);
            return exists;
        }
        return new User();
    }
}

package hbv501g.recipes.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import hbv501g.recipes.Persistence.Entities.User;
import hbv501g.recipes.Services.UserService;

import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.RestController;

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


}

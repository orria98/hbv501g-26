package hbv501g.recipes.Services.Implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hbv501g.recipes.Persistence.Entities.Ingredient;
import hbv501g.recipes.Persistence.Entities.IngredientMeasurement;
import hbv501g.recipes.Persistence.Entities.Recipe;
import hbv501g.recipes.Persistence.Entities.Unit;
import hbv501g.recipes.Persistence.Entities.User;
import hbv501g.recipes.Persistence.Repositories.RecipeRepository;
import hbv501g.recipes.Persistence.Repositories.UserRepository;
import hbv501g.recipes.Services.UserService;

@Service
public class UserServiceImplementation implements UserService {
   private UserRepository userRepository;

    @Autowired
    public UserServiceImplementation(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }
      public List<User> initUsers() {
        List<User> AllUsers = findAll();

        if (AllUsers.size() == 0) {
            User user = new User("Nafn", "Lykilord", "postur@gmail.is");
            save(user);

            user = new User("Nafn1", "Lykilord", "postur@gmail.is");
            save(user);

            user = new User("Nafn2", "Lykilord", "postur@gmail.is");
            save(user);

            AllUsers = findAll();
        }

        return AllUsers;
    }
}

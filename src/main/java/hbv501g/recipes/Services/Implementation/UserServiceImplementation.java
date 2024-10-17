package hbv501g.recipes.Services.Implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hbv501g.recipes.Persistence.Repositories.UserRepository;
import hbv501g.recipes.Services.IngredientService;
import hbv501g.recipes.Services.UserService;
import hbv501g.recipes.Persistence.Entities.*;

@Service
public class UserServiceImplementation implements UserService {
    private UserRepository userRepository;
    private IngredientService ingredientService;

    @Autowired
    public UserServiceImplementation(UserRepository userRepository, IngredientService ingredientService) {
        this.userRepository = userRepository;
        this.ingredientService = ingredientService;
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    /**
     * Initializes a few users if none are found in the database
     */
    public List<User> initUsers() {
        List<User> AllUsers = findAll();

        if (AllUsers.size() == 0) {
            User user = new User("Jón", "jon123", "jon123@gmail.com");
            save(user);

            user = new User("Superman", "123", "superman@gmail.com");
            save(user);

            user = new User("admin", "admin", "admin@hi.is");
            save(user);

            AllUsers = findAll();
        }

        return AllUsers;
    }

    @Override
    public User findByID(Long id) {
        return userRepository.findByID(id);
    }

    public User update(User updatedUser) {
        return userRepository.save(updatedUser);
    }

    public List<IngredientMeasurement> findUserPantry(Long userId) {
        return findByID(userId).getPantry();
    }

    /**
     * Adds ingredientMeasurement to pantry
     */
    public IngredientMeasurement addPantryItem(Long uid, Long iid, Unit unit, double quantity) {
        User user = findByID(uid);
        Ingredient ingredient = ingredientService.findByID(iid);

        IngredientMeasurement ingredientMeasurement = new IngredientMeasurement(ingredient, unit, quantity);
        user.addIngredientMeasurement(ingredientMeasurement);
        update(user);
        return ingredientMeasurement;
    }

    /**
     * Deletes this ingredient from the user's pantry, and updates the user
     * 
     * @param uid user id
     * @param iid ingredient id
     */
    public void deletePantryItem(Long uid, Long iid) {
        User user = findByID(uid);
        List<IngredientMeasurement> pantry = user.getPantry();
        int index = indexOf(iid, user.getPantry());
        if (index != -1)
            pantry.remove(index);

        user.setPantry(pantry);
        update(user);
    }

    /**
     * Finds the index of an ingredient measurement for this ingredient in the
     * specified pantry
     * 
     * @param ingredientId
     * @param pantry
     * @return index within pantry
     */
    private int indexOf(Long ingredientId, List<IngredientMeasurement> pantry) {
        int index = 0;
        for (IngredientMeasurement item : pantry) {
            if (item.getIngredient().getID() == ingredientId)
                return index;
            index++;
        }
        return -1;
    }

}

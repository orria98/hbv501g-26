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

    /**
     * Takes in a username and finds a user with that username in the database.
     * Returns the user, or null if no user with that username exists
     * 
     * @param username - The username of the requested user
     * @return The requested user, or null
     */
    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * Takes in a username and password. If neither is null, it checks whether there
     * is a user with both the given username and password in the database. if one
     * exists, the method returns the user, otherwise it returns null
     * 
     * @param username - The username of the user signing in
     * @param password - The password of the user signing in
     * @return User - The user who was signed in, or null
     */
    @Override
    public User login(String username, String password) {
        if (username == null || password == null) {
            return null;
        }
        User doesExist = findByUsername(username);
        if (doesExist != null) {
            if (doesExist.getPassword().equals(password)) {
                return doesExist;
            }
        }
        return null;
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
            userRepository.save(user);

            user = new User("Superman", "123", "superman@gmail.com");
            userRepository.save(user);

            user = new User("admin", "admin", "admin@hi.is");
            userRepository.save(user);

            AllUsers = findAll();
        }

        return AllUsers;
    }

    @Override
    public User findByID(long id) {
        return userRepository.findByID(id);
    }

    public User update(User updatedUser) {
        return userRepository.save(updatedUser);
    }

    /**
     * Takes in a username and password. If neither is null, and the username does
     * not belong to a user in the database, a new user is created. The user is
     * returned, if no user was made, the method returns null
     * 
     * @param username - the username of the user being created
     * @param password - the password of the user being created
     * @return the new user, or null
     */
    public User signup(String username, String password) {
        if (username == null || password == null) {
            return null;
        }
        if (findByUsername(username) == null) {
            User newUser = new User(username, password);
            userRepository.save(newUser);
            return newUser;
        }
        return null;
    }

    /**
     * finds pantry of a user, and returns the contents
     * 
     * @param user - user owning pantry
     * @return pantry contents for the user
     */
    public List<IngredientMeasurement> findUserPantry(long uid) {
        User user = findByID(uid);

        if (user == null)
            return null;

        return user.getPantry();
    }

    /**
     * Adds ingredientMeasurement to pantry, if the ingredient isn't already in the
     * pantry.
     * 
     * @param user      user owning pantry
     * @param iid       id of ingredient to add to pantry
     * @param unit      unit of measure
     * @param quantityy quantity in pantry
     * @return ingredient measurement with the ingredient
     */
    public IngredientMeasurement addPantryItem(long uid, long iid, Unit unit, double quantity) {
        User user = findByID(uid);
        Ingredient ingredient = ingredientService.findByID(iid);
        if (ingredient == null || ingredient.isPrivate() && ingredient.getCreatedBy() != user || user == null)
            return null;

        List<IngredientMeasurement> pantry = user.getPantry();
        int index = indexOf(iid, pantry);
        IngredientMeasurement ingredientMeasurement;

        if (index == -1) {
            ingredientMeasurement = new IngredientMeasurement(ingredient, unit, quantity);
            user.addIngredientMeasurement(ingredientMeasurement);
            update(user);
        } else {
            ingredientMeasurement = pantry.get(index);
        }

        return ingredientMeasurement;
    }

    /**
     * Deletes this ingredient from the user's pantry, and updates the user
     * 
     * @param user - user owning pantry
     * @param iid  - id of ingredient in the pantry item
     */
    public void deletePantryItem(long uid, long iid) {
        User user = findByID(uid);
        if (user == null)
            return;

        List<IngredientMeasurement> pantry = user.getPantry();
        int index = indexOf(iid, user.getPantry());
        if (index != -1) {
            pantry.remove(index);
            user.setPantry(pantry);
            update(user);
        }

    }

    /**
     * Finds the index of an ingredient measurement for this ingredient in the
     * specified pantry
     * 
     * @param ingredientId
     * @param pantry
     * @return index within pantry
     */
    private int indexOf(long ingredientId, List<IngredientMeasurement> pantry) {
        int index = 0;
        for (IngredientMeasurement item : pantry) {
            if (item.getIngredient() != null && item.getIngredient().getID() == ingredientId)
                return index;
            index++;
        }
        return -1;
    }

    /**
     * Gets the first instance of an ingredient measurement containing the specified
     * ingredient from a given pantry
     * 
     * @param pantry     - the pantry to search
     * @param ingredient - the ingredient to search for
     * @return an ingredient measurement from pantry
     */
    public IngredientMeasurement findItemInPantry(List<IngredientMeasurement> pantry, Ingredient ingredient) {
        if (ingredient == null)
            return null;
        int index = indexOf(ingredient.getID(), pantry);
        if (index == -1)
            return null;
        return pantry.get(index);
    }

}

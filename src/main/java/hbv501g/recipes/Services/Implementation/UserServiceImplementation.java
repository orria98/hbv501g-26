package hbv501g.recipes.Services.Implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import hbv501g.recipes.Persistence.Repositories.UserRepository;
import hbv501g.recipes.Services.IngredientService;
import hbv501g.recipes.Services.RecipeListService;
import hbv501g.recipes.Services.RecipeService;
import hbv501g.recipes.Services.UserService;
import hbv501g.recipes.Persistence.Entities.*;

@Service
public class UserServiceImplementation implements UserService {
    private UserRepository userRepository;
    private IngredientService ingredientService;
    private RecipeService recipeService;
    private RecipeListService recipeListService;

    @Autowired
    public UserServiceImplementation(UserRepository userRepository, IngredientService ingredientService,
            @Lazy RecipeService recipeService, @Lazy RecipeListService recipeListService) {
        this.userRepository = userRepository;
        this.ingredientService = ingredientService;
        this.recipeListService = recipeListService;
        this.recipeService = recipeService;
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

    /**
     * Saves a user to the database
     * 
     * @param user - the user to save to the database
     * @return the saved user
     */
    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    /**
     * Finds all users in the system
     * 
     * @return all saved users
     */
    public List<User> findAll() {
        return userRepository.findAll();
    }

    /**
     * Finds a user with the given id
     * 
     * @param id - the id of the user
     * @return the user with the given id, or null if none exists
     */
    @Override
    public User findByID(long id) {
        return userRepository.findByID(id);
    }

    /**
     * Finds and returns a user with a given ID, if one exists. Otherwise, it
     * returns null. If the user making the request and the user
     * we want to find are the same, the returned user contains all information
     * about the user. If not, sensitive or private information are not included.
     * The only information that is provided are the public recipes, ingredients and
     * recipe lists, and the username
     * 
     * @param id   - the userID of the requested user
     * @param user - the user making the request
     * @return the user with that id, or null
     */
    public User findByID(long uid, long id) {
        User user = findByID(uid);
        User userToShow = userRepository.findByID(id);
        if (userToShow == null) {
            return null;
        }
        if (user == null || user.getID() != id) {
            // bara public uppskriftir, listar og hráefni
            User tmp = new User();
            tmp.setUsername(userToShow.getUsername());
            tmp.setIngredientsByUser(ingredientService.findPublicIngredientsByUser(userToShow));
            tmp.setRecipeLists(recipeListService.findPublicRecipeListsByUser(id));
            tmp.setRecipesByUser(recipeService.findPublicRecipesByUser(id));
            return tmp;
        }
        return userToShow;

    }

    /**
     * Updates a user, sets it as updatedUser
     * 
     * @param updatedUser - the user with the updated information
     * @return the updated user
     */
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
     * Deletes the given user if the given password matches them. Returns true if
     * successful and false if not
     * 
     * @param user     - the user to delete
     * @param password - the password given to confirm the delete
     * @return true if successful, false if not
     */
    public boolean deleteUser(User user, String password) {
        if (user != null && user.getPassword() != null && user.getPassword().equals(password)) {
            userRepository.deleteById(user.getID());
            if (findByID(user.getID()) == null) {
                return true;
            }
        }
        return false;
    }

    /**
     * Changes the password of the given user to the new password, if the old
     * password matches the one of the user. If the user is null, or the old
     * password is incorrect, then nothing happens
     * 
     * @param user        - the user to change the password of
     * @param newPassword - the new password of the user
     * @param oldPassword - the old password of the user
     */
    public void changePassword(User user, String newPassword, String oldPassword) {
        if (user != null) {
            if (user.getPassword().equals(oldPassword)) {
                user.setPassword(newPassword);
                update(user);
            }
        }
    }

    /**
     * Adds ingredientMeasurement to pantry for the given user, if the ingredient
     * isn't already in the pantry and if the user is not null. Users can add any
     * public ingredient or their own private ingredients to the pantry.
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
        if (ingredient == null || user == null || (ingredient.isPrivate()
                && (ingredient.getCreatedBy() == null || ingredient.getCreatedBy().getID() != user.getID())))
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

    /**
     * Initializes a few users if none are found in the database
     * 
     * @return all users made
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

}

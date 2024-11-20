package hbv501g.recipes.Services.Implementation;

import java.util.List;
import java.time.LocalDate;

import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import hbv501g.recipes.Persistence.Entities.Ingredient;
import hbv501g.recipes.Persistence.Repositories.IngredientRepository;
import hbv501g.recipes.Services.IngredientService;
import hbv501g.recipes.Services.UserService;
import hbv501g.recipes.Persistence.Entities.User;

@Service
public class IngredientServiceImplementation implements IngredientService {

    private IngredientRepository ingredientRepository;
    private UserService userService;

    public IngredientServiceImplementation(IngredientRepository ingredientRepository, @Lazy UserService userService) {
        this.ingredientRepository = ingredientRepository;
        this.userService = userService;
    }

    /**
     * 
     * @return All ingredients from the database
     */
    @Override
    public List<Ingredient> findAll() {
        return ingredientRepository.findAll();
    }

    /**
     * Finds an ingredient with the given id
     * 
     * @param id to search for
     */
    @Override
    public Ingredient findByID(long id) {
        return ingredientRepository.findByID(id);
    }

    /**
     * Finds an ingredient with the given id, if one exists and is accessible to the
     * given user. Otherwise, null is returned
     * 
     * @param id   - the id of the ingredient to find
     * @param user - the user requesting the ingredient
     * @return an ingredient with the given id, or null
     */
    public Ingredient findAccessibleByID(long id, long uid) {
        User user = userService.findByID(uid);

        if (user == null) {
            return ingredientRepository.findByIsPrivateFalseAndID(id);
        }
        return ingredientRepository.findAccessibleByID(user, id);
    }

    /**
     * Finds all ingredients that are accessible to the given user. If the user is
     * null, this is all public ingredients
     * 
     * @param user - the user looking for ingredients
     * @return all ingredients accessible to the given user
     */
    public List<Ingredient> findAccessibleToUser(long uid) {
        User user = userService.findByID(uid);

        if (user == null) {
            return ingredientRepository.findByIsPrivateFalse();
        }
        return ingredientRepository.findByIsPrivateFalseOrCreatedBy(user);
    }

    /**
     * Saves the given ingredient to the database
     *
     * @param author     - the user creating the Ingredient
     * @param ingredient - the ingredient
     * @return the ingredient saved to db
     */
    @Override
    public Ingredient save(long uid, Ingredient ingredient) {
        User author = userService.findByID(uid);

        if (author == null) {
            return null;
        }
        ingredient.setCreatedBy(author);
        ingredient.setDateOfCreation(LocalDate.now());

        return ingredientRepository.save(ingredient);
    }

    /**
     * Finds the first ingredient with a given title
     * 
     * @param title - the ingredient title to search for
     * @return One or no ingredients
     */
    public Ingredient updateIngredientTitle(long id, String newTitle, long uid) {
        Ingredient ingredient = findByID(id);
        User user = userService.findByID(uid);

        if (ingredient == null || user == null) {
            return null;
        }
        if (ingredient.getCreatedBy().getID() != user.getID()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You are not the creator of this ingredient.");
        }
        ingredient.setTitle(newTitle);
        return update(ingredient);
    }

    /**
     * Find and delet the ingredient with maching id.
     *
     * @param User : is the user in the sesion.
     * @param id   : is a 8 byte integer and is the id
     *             of the ingredient.
     */
    @Override
    public void deleteById(long uid, long id) {
        User user = userService.findByID(uid);
        if (user != null) {
            User author = findByID(id).getCreatedBy();
            if (author != null) {
                if (author.getID() == user.getID()) {
                    ingredientRepository.deleteById(id);
                }
            }
        }
    }

    @Override
    public Ingredient findByTitle(String title) {
        return ingredientRepository.findByTitle(title).get(0);
    }

    @Override
    public Ingredient update(Ingredient updatedIngredient) {
        return ingredientRepository.save(updatedIngredient);
    }

    public List<Ingredient> findOrderedIngredients() {
        return ingredientRepository.findAllByOrderByPrice();

    }
}

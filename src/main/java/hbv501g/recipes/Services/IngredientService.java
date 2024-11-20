/**
 * Interface sem heldur utan um aðferðir sem hægt er að kalla á úr implementationinu
 */
package hbv501g.recipes.Services;

import hbv501g.recipes.Persistence.Entities.Ingredient;
import hbv501g.recipes.Persistence.Entities.User;

import java.util.List;

public interface IngredientService {
    List<Ingredient> findAll();

    Ingredient findByID(long id);
    
    Ingredient findAccessibleByID(long id, long uid);

    List<Ingredient> findAccessibleToUser(long uid);

    Ingredient save(long uid, Ingredient ingredient);

    Ingredient update(Ingredient updatedIngredient);

    void deleteById(long uid, long id);

    // Ekki hluti af skilum
    // List<Ingredient> initIngredients();
    
    Ingredient findByTitle(String title);

    List<Ingredient> findOrderedIngredients();

    Ingredient updateIngredientTitle(long id, String newTitle, long uid);

    List<Ingredient> findPublicIngredientsByUser(User user);

}

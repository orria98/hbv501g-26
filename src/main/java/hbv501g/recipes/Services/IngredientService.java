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

    Ingredient save(User author, Ingredient ingredient);

    Ingredient update(Ingredient updatedIngredient);

    void deleteById (User user, long id);

    // Ekki hluti af skilum
    // List<Ingredient> initIngredients();
    
    Ingredient findByTitle(String title);

    List<Ingredient> findOrderedIngredients();

}

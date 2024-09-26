/**
 * Interface sem heldur utan um aðferðir sem hægt er að kalla á úr implementationinu
 */
package hbv501g.recipes.Services;

import hbv501g.recipes.Persistence.Entities.Ingredient;

import java.util.List;

public interface IngredientService {
    List<Ingredient> findAll();

    Ingredient findByID(Long id);

    // Seinni skil
    Ingredient findByName(String name);

    Ingredient save(Ingredient ingredient);

    // void delete(Long ID);

    Ingredient update(Ingredient updatedIngredient);

    List<Ingredient> findOrderedIngredients();
}

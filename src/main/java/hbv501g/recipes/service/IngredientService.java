/**
 * Aðferðir sem kallað er á til að eiga við Ingredient töflu í gagnagrunni.
 */
package hbv501g.recipes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hbv501g.recipes.model.Ingredient;
import hbv501g.recipes.repository.IngredientRepository;

import java.util.List;


@Service
public class IngredientService {
    
    @Autowired
    private IngredientRepository ingredientRepository;

    /**
     * 
     * @return All ingredients from the database
     */
    public List<Ingredient> getAllIngredients(){
        return ingredientRepository.findAll();
    }

    /**
     * Adds a given Java object to the database
     * @param ingredient - the java object
     * @return the ingredient saved to db
     */
    public Ingredient createIngredient(Ingredient ingredient) {
        return ingredientRepository.save(ingredient);
    }

    public Ingredient getIngredientById(Long id) {
        return ingredientRepository.findById(id).orElse(null);
    }

    // TODO: Passa hvað gerist ef fleiri hafa sama nafn
    /**
     * 
     * @param IngredientName - the name to search for
     * @return One or no ingredients
     */
    public Ingredient getIngredientByName(String IngredientName){
        return ingredientRepository.findByIngredientName(IngredientName);
    }

    public Ingredient updateIngredient(Ingredient updatedIngredient) {
        return ingredientRepository.save(updatedIngredient);
    }

    public void deleteIngredient(Long id) {
        ingredientRepository.deleteById(id);
    }
}

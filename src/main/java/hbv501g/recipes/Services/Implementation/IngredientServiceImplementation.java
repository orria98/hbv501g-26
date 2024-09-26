package hbv501g.recipes.Services.Implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hbv501g.recipes.Persistence.Entities.Ingredient;
import hbv501g.recipes.Persistence.Repositories.IngredientRepository;
import hbv501g.recipes.Services.IngredientService;

@Service
public class IngredientServiceImplementation implements IngredientService {

    private IngredientRepository ingredientRepository;

    @Autowired
    public IngredientServiceImplementation(IngredientRepository ingredientRepository){
        this.ingredientRepository = ingredientRepository;
    }

    /**
     * 
     * @return All ingredients from the database
     */
    @Override
    public List<Ingredient> findAll() {
        return ingredientRepository.findAll();
    }

    //@Override
    public Ingredient findByID(Long id) {
        return ingredientRepository.findById(id).orElse(null);
    }

        /**
     * Adds a given Java object to the database
     * 
     * @param ingredient - the java object
     * @return the ingredient saved to db
     */
    @Override
    public Ingredient save(Ingredient ingredient) {
        return ingredientRepository.save(ingredient);
    }

    /**
     * Assumes the ingredient name is unique
     * 
     * @param IngredientName - the name to search for
     * @return One or no ingredients
     */
    @Override
    public Ingredient findByName(String IngredientName) {
        return ingredientRepository.findByName(IngredientName).get(0);
    }

    @Override
    public Ingredient update(Ingredient updatedIngredient) {
        return ingredientRepository.save(updatedIngredient);
    }

    public List<Ingredient> findOrderedIngredients(){
        return ingredientRepository.findOrderedIngredients();
    }

    // @Override
    // public void delete(Long id) {
    //     ingredientRepository.deleteById(id);
    // }
}
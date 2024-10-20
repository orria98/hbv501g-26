package hbv501g.recipes.Services.Implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hbv501g.recipes.Persistence.Entities.Ingredient;
import hbv501g.recipes.Persistence.Entities.Unit;
import hbv501g.recipes.Persistence.Repositories.IngredientRepository;
import hbv501g.recipes.Services.IngredientService;

@Service
public class IngredientServiceImplementation implements IngredientService {

    private IngredientRepository ingredientRepository;

    @Autowired
    public IngredientServiceImplementation(IngredientRepository ingredientRepository) {
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


    /**
     * Finds an ingredient with the given id
     * @param id to search for
     */
    @Override
    public Ingredient findByID(long id) {
        return ingredientRepository.findByID(id);
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
     * Finds the first ingredient with a given title
     * 
     * @param title - the ingredient title to search for
     * @return One or no ingredients
     */
    @Override
    public Ingredient findByTitle(String title) {
        return ingredientRepository.findByTitle(title).get(0);
    }

    @Override
    public Ingredient update(Ingredient updatedIngredient) {
        return ingredientRepository.save(updatedIngredient);
    }

    public List<Ingredient> findOrderedIngredients(){
        return ingredientRepository.findAllByOrderByPrice();
    }

    public List<Ingredient> initIngredients(){
            List<Ingredient> AllIngredients = findAll();

        if (AllIngredients.size() == 0) {
            Ingredient ingredient = new Ingredient("ger", Unit.G, 25, 250);
            save(ingredient);

            ingredient = new Ingredient("hveiti", Unit.G, 2000, 500, "Bónus", "Kornax");
            save(ingredient);

            ingredient = new Ingredient("sykur", Unit.G, 1000, 400);
            save(ingredient);


            ingredient = new Ingredient("vatn", Unit.ML, 1000, 200);
            save(ingredient);

            AllIngredients = findAll();
        }

        return AllIngredients;
    }

    /**
     * Find and delet the ingredient with maching id.
     * 
     * @param id : is a 8 byte integer and is the id
     * 		   of the ingredient.
     */
    @Override
    public void deleteById(long id){
        ingredientRepository.deleteById(id);
    }
}

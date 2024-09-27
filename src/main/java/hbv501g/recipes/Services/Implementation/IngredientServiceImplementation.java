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

    // @Override
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
     * Finds the first ingredient with a given name
     * 
     * @param IngredientName - the name to search for
     * @return One or no ingredients
     */
    @Override
    public Ingredient findByName(String IngredientName) {
        return ingredientRepository.findByName(IngredientName).get(0);
    }

    /**
     * Þetta er ekki hluti af verkefninu, en auðveldar það að testa
     */
    public List<Ingredient> initIngredients() {
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
}
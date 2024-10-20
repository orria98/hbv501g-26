/*
 * Service interface fyrir User. Tiltekur hvaða aðferðir eru í service klasanum
 */
package hbv501g.recipes.Services;

import java.util.List;

import hbv501g.recipes.Persistence.Entities.Ingredient;
import hbv501g.recipes.Persistence.Entities.IngredientMeasurement;
import hbv501g.recipes.Persistence.Entities.Unit;
import hbv501g.recipes.Persistence.Entities.User;

public interface UserService {
    List<User> findAll();

    User findByID(long id);
    User findByUsername(String username);

    User save(User user);

    List<User> initUsers();

    User login(User user);
    User update(User user);

    List<IngredientMeasurement> findUserPantry(long userId);

    void deletePantryItem(long uid, long iid);

    IngredientMeasurement addPantryItem(long uid, long iid, Unit unit, double quantity);

    IngredientMeasurement findItemInPantry(List<IngredientMeasurement> pantry, Ingredient ingredient);

}

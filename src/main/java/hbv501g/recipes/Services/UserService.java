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
    User signup(String username, String password);
    User login(String username, String password);
    User update(User user);

    boolean deleteUser(User user, String password);

    List<IngredientMeasurement> findUserPantry(User user);

    void deletePantryItem(User user, long iid);

    IngredientMeasurement addPantryItem(User user, long iid, Unit unit, double quantity);

    IngredientMeasurement findItemInPantry(List<IngredientMeasurement> pantry, Ingredient ingredient);

}

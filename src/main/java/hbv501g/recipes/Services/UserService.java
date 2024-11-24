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
    User findByID(long uid,long id); 

    User findByUsername(String username);

    User save(User user);

    User signup(String username, String password);

    User login(String username, String password);

    User update(User user);

    boolean deleteUser(long uid, String password);

    void changePassword(long uid, String newPassword, String oldPassword);
    List<IngredientMeasurement> findUserPantry(long uid);

    void deletePantryItem(long uid, long iid);

    IngredientMeasurement addPantryItem(long uid, long iid, Unit unit, double quantity);

    IngredientMeasurement findItemInPantry(List<IngredientMeasurement> pantry, Ingredient ingredient);

}

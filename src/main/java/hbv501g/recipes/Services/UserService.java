/*
 * Service interface fyrir User. Tiltekur hvaða aðferðir eru í service klasanum
 */
package hbv501g.recipes.Services;

import java.util.List;

import hbv501g.recipes.Persistence.Entities.IngredientMeasurement;
import hbv501g.recipes.Persistence.Entities.Unit;
import hbv501g.recipes.Persistence.Entities.User;

public interface UserService {
    List<User> findAll();

    User findByID(Long id);

    User save(User user);

    List<User> initUsers();

    User update(User user);

    List<IngredientMeasurement> findUserPantry(Long userId);

    void deletePantryItem(Long uid, Long iid);

    IngredientMeasurement addPantryItem(Long uid, Long iid, Unit unit, double quantity);
}

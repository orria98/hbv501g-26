/*
 * Service interface fyrir User. Tiltekur hvaða aðferðir eru í service klasanum
 */
package hbv501g.recipes.Services;

import java.util.List;

import hbv501g.recipes.Persistence.Entities.User;

public interface UserService {


     List<User> findAll();

     User findByID(Long id);


    User save(User user);

    List<User> initUsers();

    User update(User user);

}

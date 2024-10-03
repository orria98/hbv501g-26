/*
 * Service interface fyrir User. Tiltekur hvaða aðferðir eru í service klasanum
 */
package hbv501g.recipes.Services;

import java.util.List;

import hbv501g.recipes.Persistence.Entities.User;

public interface UserService {


     List<User> findAll();


    User save(User user);

    List<User> initUsers();
}

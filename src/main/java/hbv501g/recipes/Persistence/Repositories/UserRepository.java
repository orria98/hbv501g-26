package hbv501g.recipes.Persistence.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import hbv501g.recipes.Persistence.Entities.User;

public interface UserRepository extends JpaRepository<User, Long>{

    User findByID(long id);

    User findByUsername(String username);

}

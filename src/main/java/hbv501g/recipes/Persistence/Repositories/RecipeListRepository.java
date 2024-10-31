package hbv501g.recipes.Persistence.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import hbv501g.recipes.Persistence.Entities.RecipeList;
import hbv501g.recipes.Persistence.Entities.User;

public interface RecipeListRepository extends JpaRepository<RecipeList, Long> {
    List<RecipeList> findByCreatedBy(User user);
    
    RecipeList findById(long id);
}

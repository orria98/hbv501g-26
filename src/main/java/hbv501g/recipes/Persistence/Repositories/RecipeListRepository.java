package hbv501g.recipes.Persistence.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import hbv501g.recipes.Persistence.Entities.RecipeList;
import hbv501g.recipes.Persistence.Entities.User;

public interface RecipeListRepository extends JpaRepository<RecipeList, Long> {
    @Query("select r from RecipeList r where r.createdBy = ?1 or not r.isPrivate")
    List<RecipeList> findAllAccessible(User user);

    List<RecipeList> findByIsPrivateFalse();
    
    List<RecipeList> findByCreatedBy(User user);
    
    RecipeList findById(long id);

    void delete(RecipeList list);
}

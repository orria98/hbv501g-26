package hbv501g.recipes.Persistence.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import hbv501g.recipes.Persistence.Entities.Recipe;
import hbv501g.recipes.Persistence.Entities.RecipeList;
import hbv501g.recipes.Persistence.Entities.User;

public interface RecipeListRepository extends JpaRepository<RecipeList, Long> {
    @Query("select r from RecipeList r where r.createdBy = ?1 or not r.isPrivate")
    List<RecipeList> findAllAccessible(User user);

    List<RecipeList> findByIsPrivateFalse();
    
    List<RecipeList> findByCreatedBy(User user);
    
    RecipeList findById(long id);

    @Query("select r from Recipe r where (r.createdBy = ?1 or not r.isPrivate) and r.ID IN"
        + 
        "(selcet RecipeList rl where rl.ID = ?2 and (r.createdBy = ?1 or not r.isPrivate))")
    List<Recipe> findAllRecipesFormId(User user, long id);

    void delete(RecipeList list);
}
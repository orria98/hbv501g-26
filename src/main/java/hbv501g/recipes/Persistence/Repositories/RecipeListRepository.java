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

    @Query("select r from RecipeList r where (r.createdBy = ?1 or not r.isPrivate) and r.id=?2")
    RecipeList findAccessibleById(User user, long id);

    List<RecipeList> findByIsPrivateFalse();

    RecipeList findByIsPrivateFalseAndID(long id);
    
    List<RecipeList> findByCreatedBy(User user);
    
    RecipeList findById(long id);

    @Query
    (
        "select r from Recipe r where not r.isPrivate and r IN" 
        + 
        "(select rl.recipes From RecipeList rl where rl.ID = ?1 and not rl.isPrivate)"
    )
    List<Recipe> findAllRecipesFromId(long id);

    @Query
    (
        "select r from Recipe r where (r.createdBy = ?1 or not r.isPrivate) and r IN" 
        + 
        "(select rl.recipes From RecipeList rl where rl.ID = ?2 and (rl.createdBy = ?1 or not rl.isPrivate))"
    )
    List<Recipe> findAllRecipesFormId(User user, long id);

    void delete(RecipeList list);

    List<RecipeList> findByIsPrivateFalseAndCreatedBy(User user);

    @Query("select r from RecipeList r where (r.createdBy = ?1 or not r.isPrivate) and r.createdBy=?2")
    List<RecipeList> findAllAccessibleByUser(User requester, User creator);


}

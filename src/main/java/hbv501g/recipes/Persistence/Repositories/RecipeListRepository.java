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

    /**
     * Gets all public recipes from a recipeList with the given id, if one exists
     * and is public
     *
     * @param id - the ID of the recipeList
     * @return The public recipes of the recipelist with the id, if one exists and
     *         is public
     */
    @Query("select r from Recipe r where not r.isPrivate and r IN"
            +
            "(select rl.recipes From RecipeList rl where rl.ID = ?1 and not rl.isPrivate)")
    List<Recipe> findAllRecipesFromId(long id);

    /**
     * Gets all recipes accessible to the given user from a recipeList with the
     * given id, if one exists and is accessible to the user.
     *
     * @param user - the user making the request
     * @param id   - the id of the recipelist
     * @return The accessible recipes of the recipelist with the id, if one exists
     *         and is accessible to the user
     */
    @Query("select r from Recipe r where (r.createdBy = ?1 or not r.isPrivate) and r IN"
            +
            "(select rl.recipes From RecipeList rl where rl.ID = ?2 and (rl.createdBy = ?1 or not rl.isPrivate))")
    List<Recipe> findAllRecipesFromId(User user, long id);

    void delete(RecipeList list);

    List<RecipeList> findByIsPrivateFalseAndCreatedBy(User user);

    @Query("select r from RecipeList r where (r.createdBy = ?1 or not r.isPrivate) and r.createdBy=?2")
    List<RecipeList> findAllAccessibleByUser(User requester, User creator);

}

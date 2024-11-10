package hbv501g.recipes.Persistence.Repositories;

import hbv501g.recipes.Persistence.Entities.*;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    List<Recipe> findAll();

    @Query("select r from Recipe r where (r.createdBy = ?1 or not r.isPrivate ) and r.title like ?2")
    List<Recipe> searchAccessibleRecipes(User user, String searchTerm);

    @Query("select r from Recipe r where (r.createdBy = ?1 or not r.isPrivate ) and r.id = ?2")
    Recipe findAccessibleByID(User user, long id);

    @Query("select r from Recipe r where (r.createdBy = ?1 or not r.isPrivate ) and r.totalPurchaseCost < ?2")
    List<Recipe> findAccessibleUnderTPC(User user, int tpc);

    @Query("select r from Recipe r where not r.isPrivate  and r.totalPurchaseCost < ?1")
    List<Recipe> findPublicUnderTPC(int tpc);

    @Query("select r from Recipe r where (r.createdBy = ?1 or not r.isPrivate ) and r.totalIngredientCost < ?2")
    List<Recipe> findAccessibleUnderTIC(User user, int tic);

    @Query("select r from Recipe r where not r.isPrivate  and r.totalIngredientCost < ?1")
    List<Recipe> findPublicUnderTIC(int tic);

    Recipe findByIsPrivateFalseAndID(long id);

    List<Recipe> findByIsPrivateFalseAndTitleContaining(String searchTerm);

    List<Recipe> findByIsPrivateFalseOrCreatedBy(User user);

    List<Recipe> findByIsPrivateFalse();

    void deleteById(long id);

    Recipe findByID(long iD);

    List<Recipe> findByIsPrivateFalseOrderByTotalPurchaseCostAsc();

    @Query("select r from Recipe r where (r.createdBy = ?1 or not r.isPrivate ) order by r.totalPurchaseCost asc")
    List<Recipe> findRecipesOrderedByTotalPurchasePriceAscending(User user);
}

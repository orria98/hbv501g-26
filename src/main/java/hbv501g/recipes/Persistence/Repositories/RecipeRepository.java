package hbv501g.recipes.Persistence.Repositories;

import hbv501g.recipes.Persistence.Entities.*;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    List<Recipe> findAll();

    List<Recipe> findByTitleContaining(String searchTerm);

    List<Recipe> findByIsPrivateFalseOrCreatedByAndTitleContaining(User user, String searchTerm);

    @Query("select r from Recipe r where (r.createdBy = ?1 or not r.isPrivate ) and r.title like ?2")
    List<Recipe> searchAccessibleRecipes(User user,String searchTerm);

    @Query("select r from Recipe r where (r.createdBy = ?1 or not r.isPrivate ) and r.id = ?2")
    Recipe findAccessibleByID(User user, long id);

    Recipe findByIsPrivateFalseAndID(long id);

    List<Recipe> findByIsPrivateFalseAndTitleContaining(String searchTerm);

    List<Recipe> findByIsPrivateFalseOrCreatedBy(User user);

    List<Recipe> findByIsPrivateFalse();
    
    void deleteById(long id);
    
    Recipe findByID(long iD);
    
} 

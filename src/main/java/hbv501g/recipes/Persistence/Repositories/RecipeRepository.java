package hbv501g.recipes.Persistence.Repositories;

import hbv501g.recipes.Persistence.Entities.Recipe;
import hbv501g.recipes.Persistence.Entities.*;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    List<Recipe> findAll();

    List<Recipe> findByTitleContaining(String searchTerm);

    List<Recipe> findByIsPrivateFalseOrCreatedBy(User user);

    List<Recipe> findByIsPrivateFalse();
    
    void deleteById(long id);
    
    Recipe findByID(long iD);
    
} 

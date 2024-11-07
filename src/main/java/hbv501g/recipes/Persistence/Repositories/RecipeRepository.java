package hbv501g.recipes.Persistence.Repositories;

import hbv501g.recipes.Persistence.Entities.Recipe;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    List<Recipe> findAll();

    void deleteById(long id);

    Recipe findByID(long iD);

    List<Recipe> findAllByOrderByTotalPurchaseCostAsc();
}

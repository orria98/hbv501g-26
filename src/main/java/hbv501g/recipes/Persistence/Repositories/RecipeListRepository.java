package hbv501g.recipes.Persistence.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import hbv501g.recipes.Persistence.Entities.RecipeList;

public interface RecipeListRepository extends JpaRepository<RecipeList, Long> {

}

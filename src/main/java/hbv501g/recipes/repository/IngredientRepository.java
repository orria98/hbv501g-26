/**
 * Interface sem tilgreinir hvaða aðferðir (og queries) eru notuð á Ingredient töflu í gagnagrunni. 
 * Kallað er á aðferðirnar í IngredientService klasanum.
 */
package hbv501g.recipes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import hbv501g.recipes.model.Ingredient;


public interface IngredientRepository extends JpaRepository<Ingredient, Long>{

    @Query("select i from Ingredient i where i.name like ?1")
    Ingredient findByIngredientName(String ingredientName);

}

/**
 * Interface sem tilgreinir hvaða aðferðir (og queries) eru notuð á Ingredient töflu í gagnagrunni. 
 * Kallað er á aðferðirnar í IngredientService klasanum.
 * Nafnið á aðferðinni ræður því hvað er sótt. findByName(String name) sækir eftir nafni, en findByNafn gæti það ekki.
 */
package hbv501g.recipes.Persistence.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Query;

import hbv501g.recipes.Persistence.Entities.Ingredient;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    List<Ingredient> findAll();

    Ingredient findByID(Long id);

    //Seinni skil
    //@Query("select i from Ingredient i where i.name like ?1")
    List<Ingredient> findByName(String name);

    

    // Þarf ekki að tilgreina því þetta er í JpaRepository
    //Ingredient save(Ingredient ingredient);

    // void delete(Long id);

    // public Ingredient update(Ingredient updatedIngredient);

    @Query("select i from Ingredient i order by price")
    List<Ingredient> findOrderedIngredients();

}

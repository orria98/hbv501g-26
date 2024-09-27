/**
 * Interface sem tilgreinir hvaða aðferðir (og queries) eru notuð á Ingredient töflu í gagnagrunni. 
 * Kallað er á aðferðirnar í IngredientService klasanum.
 * Nafnið á aðferðinni ræður því hvað er sótt. findByName(String name) sækir eftir nafni, en findByNafn gæti það ekki.
 * 
 * To get items by more specific criteria, @Query may be used. 
 * 
 * For example: @Query("select i from Ingredient i order by price") gets all ingredients 
 * ordered by price
 * 
 * Það þarf ekki að tilgreina save, delete og nokkur fleiri því það er í JpaRepository
 */
package hbv501g.recipes.Persistence.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import hbv501g.recipes.Persistence.Entities.Ingredient;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    List<Ingredient> findAll();

    Ingredient findByID(Long id);

    Ingredient save(Ingredient ingredient); // Ætti að mega sleppa þessu

    //Seinni skil
    List<Ingredient> findByName(String name);

}

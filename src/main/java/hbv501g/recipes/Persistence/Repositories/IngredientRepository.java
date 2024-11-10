/**
 * Interface sem tilgreinir hvaða aðferðir (og queries) eru notuð á Ingredient töflu í gagnagrunni. 
 * Kallað er á aðferðirnar í IngredientService klasanum.
 * Nafnið á aðferðinni ræður því hvað er sótt. findByTitle(String title) sækir eftir titli, en findByNafn gæti það ekki.
 * 
 * Það þarf ekki að tilgreina save, delete og nokkur fleiri því það er innbyggt?
 */
package hbv501g.recipes.Persistence.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import hbv501g.recipes.Persistence.Entities.*;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    List<Ingredient> findAll();

    Ingredient findByID(long id);

    void deleteById(long id);

    @Query("select i from Ingredient i where (i.createdBy = ?1 or not i.isPrivate ) and i.id = ?2")
    Ingredient findAccessibleByID(User user, long id);

    Ingredient findByIsPrivateFalseAndID(long id);

    List<Ingredient> findByIsPrivateFalse();

    List<Ingredient> findByIsPrivateFalseOrCreatedBy(User user);
    
    // Ekki hluti af skilum
    List<Ingredient> findByTitle(String title);

    List<Ingredient> findAllByOrderByPrice();
}

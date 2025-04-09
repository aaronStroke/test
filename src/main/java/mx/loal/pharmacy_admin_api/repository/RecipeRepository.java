package mx.loal.pharmacy_admin_api.repository;

import mx.loal.pharmacy_admin_api.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    @Modifying
    @Query("""
        UPDATE Recipe r
        SET r.recipeDir = :dir
        WHERE r.id = :id
    """)
    void updateRecipeDir(Long id, String dir);

}

package com.spring.recipes.repo;

import com.spring.recipes.entities.Recipe;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Alex Giazitzis
 */
@Repository
public interface RecipeRepository extends CrudRepository<Recipe, Long> {

    /**
     * Fetches all the {@link com.spring.recipes.entities.Recipe}s that their name contains the passed {@code name} value,
     * ordering the list by the date they were created in descending order.
     * @param name the string to search in the recipe names, ignores the case of both the parameter and the entity's name
     * @return {@link java.util.List} of {@link com.spring.recipes.entities.Recipe}s with the found entities.
     */
    List<Recipe> findAllByNameIgnoreCaseContainingOrderByDateDesc(final String name);

    /**
     * Fetches all the {@link com.spring.recipes.entities.Recipe}s that are categorized under the {@code category} value,
     * ordering the list by the date they were created in descending order.
     * @param category the string to search in the recipe names, ignores the case of both the parameter and the entity's category
     * @return {@link java.util.List} of {@link com.spring.recipes.entities.Recipe}s with the found entities.
     */

    List<Recipe> findAllByCategoryIgnoreCaseOrderByDateDesc(final String category);

}

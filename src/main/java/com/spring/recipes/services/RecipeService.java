package com.spring.recipes.services;

import com.spring.recipes.dto.RecipeDto;
import com.spring.recipes.entities.Recipe;
import com.spring.recipes.entities.user.User;

import java.util.List;
import java.util.Optional;

/**
 * @author Alex Giazitzis
 */
public interface RecipeService {

    /**
     * Persist a {@link com.spring.recipes.entities.Recipe} by unwrapping its {@link com.spring.recipes.dto.RecipeDto}
     * and linking it to it's author from the passed {@link com.spring.recipes.entities.user.User} entity.
     * @param dto {@link com.spring.recipes.dto.RecipeDto} to unwrap and persist.
     * @param user {@link com.spring.recipes.entities.user.User} to link to the recipe before persisting it.
     * @return the {@link java.lang.Long} id of the persisted recipe.
     */
    Long save(final RecipeDto dto, final User user);

    /**
     * Finds a recipe by its {@code id} and updates it based on the data from the {@link com.spring.recipes.dto.RecipeDto}
     * and its current data.
     * @param id of the recipe to update.
     * @param dto {@link com.spring.recipes.dto.RecipeDto} with the updated data to persist.
     */
    void update(final Long id, final RecipeDto dto);

    /**
     * Tries to find if a recipe with the specified {@code id}.
     * @param id to query for.
     * @return {@link java.util.Optional} of {@link com.spring.recipes.entities.Recipe} that may or may not exist.
     */
    Optional<Recipe> getRecipe(final Long id);

    /**
     * Tries to wrap the output of the {@link RecipeService#getRecipe(Long id)} in a data transfer object.
     * @param id of the recipe to be wrapped.
     * @return {@link java.util.Optional} of {@link com.spring.recipes.dto.RecipeDto} that may or may not exist.
     */
    Optional<RecipeDto> getRecipeDto(final Long id);

    /**
     * Finds a {@link java.util.List} of {@link com.spring.recipes.dto.RecipeDto}s that their unwrapped counterpart's name contains
     * the value passed as a parameter.
     * @param name value to query by, ignoring the case both of it and the entity's respective field.
     * @return {@link java.util.List} of {@link com.spring.recipes.dto.RecipeDto}s.
     */
    List<RecipeDto> findRecipesWithNameContaining(final String name);

    /**
     * Finds a {@link java.util.List} of {@link com.spring.recipes.dto.RecipeDto}s that their unwrapped counterpart's category
     * is the value passed as a parameter.
     * @param category value to query by, ignoring the case both of it and the entity's respective field.
     * @return {@link java.util.List} of {@link com.spring.recipes.dto.RecipeDto}s.
     */
    List<RecipeDto> findRecipesInCategory(final String category);

    /**
     * Deletes the specified {@link com.spring.recipes.entities.Recipe} from the persistence source.
     * @param recipe to be deleted.
     */
    void deleteRecipe(final Recipe recipe);

}

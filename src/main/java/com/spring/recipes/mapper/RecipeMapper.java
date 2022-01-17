package com.spring.recipes.mapper;

import com.spring.recipes.dto.RecipeDto;
import com.spring.recipes.entities.Recipe;
import com.spring.recipes.entities.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

/**
 * {@link org.mapstruct.Mapper} interface for wrapping/unwrapping {@link com.spring.recipes.dto.RecipeDto} objects from/to
 * {@link com.spring.recipes.entities.Recipe} entities.
 * @author Alex Giazitzis
 */
@Mapper
public interface RecipeMapper {

    /**
     * Maps a {@link com.spring.recipes.dto.RecipeDto} to a {@link com.spring.recipes.entities.Recipe} and sets its' author field
     * to the {@link com.spring.recipes.entities.user.User} entity.
     * @param dto {@link com.spring.recipes.dto.RecipeDto} to be unwrapped.
     * @param user {@link com.spring.recipes.entities.user.User} that posted the recipe.
     * @return the unwrapped {@link com.spring.recipes.entities.Recipe}.
     */
    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "author", source = "user")
    })
    Recipe toRecipe(final RecipeDto dto, final User user);

    /**
     * Updates an existing {@link com.spring.recipes.entities.Recipe} with data from the data transfer object {@link com.spring.recipes.dto.RecipeDto}.
     * @param recipe {@link com.spring.recipes.entities.Recipe} that exists already.
     * @param dto {@link com.spring.recipes.dto.RecipeDto} with the updated data for the recipe.
     * @return the updated {@link com.spring.recipes.entities.Recipe}.
     */
    @Mappings({
            @Mapping(target = "id", source = "recipe.id"),
            @Mapping(target = "author", source = "recipe.author"),
            @Mapping(target = "name", source = "dto.name"),
            @Mapping(target = "category", source = "dto.category"),
            @Mapping(target = "date", source = "dto.date"),
            @Mapping(target = "description", source = "dto.description"),
            @Mapping(target = "ingredients", source = "dto.ingredients"),
            @Mapping(target = "directions", source = "dto.directions")
    })
    Recipe toRecipe(final Recipe recipe, final RecipeDto dto);

    /**
     * Wraps a {@link com.spring.recipes.entities.Recipe} as a {@link com.spring.recipes.dto.RecipeDto}.
     * @param recipe to be wrapped
     * @return data transfer object of the recipe entity.
     */
    RecipeDto toDto(final Recipe recipe);

    /**
     * Converts a list of {@link com.spring.recipes.entities.Recipe}s to a list of {@link com.spring.recipes.dto.RecipeDto}s.
     * @param recipeList to be wrapped.
     * @return {@link java.util.List} of {@link com.spring.recipes.dto.RecipeDto}s from the wrapped entities.
     */
    List<RecipeDto> toDtoList(List<Recipe> recipeList);

}

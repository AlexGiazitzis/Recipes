package com.spring.recipes.services.impl;

import com.spring.recipes.dto.RecipeDto;
import com.spring.recipes.entities.Recipe;
import com.spring.recipes.entities.user.User;
import com.spring.recipes.mapper.RecipeMapper;
import com.spring.recipes.repo.RecipeRepository;
import com.spring.recipes.services.RecipeService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @author Alex Giazitzis
 */
@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RecipeServiceImpl implements RecipeService {

    RecipeRepository recipeRepository;
    RecipeMapper     recipeMapper;

    @Override
    public Long save(final RecipeDto dto, final User user) {

        return recipeRepository.save(recipeMapper.toRecipe(dto, user)).getId();

    }

    @Override
    public void update(final Long id, final RecipeDto dto) {

        Optional<Recipe> recipe = recipeRepository.findById(id);
        recipe.ifPresent(value -> recipeRepository.save(recipeMapper.toRecipe(value, dto)));

    }

    @Override
    public Optional<Recipe> getRecipe(final Long id) {

        Optional<Recipe> recipe = recipeRepository.findById(id);

        if (recipe.isEmpty()) {
            return Optional.empty();
        }
        return recipe;
    }

    @Override
    public Optional<RecipeDto> getRecipeDto(final Long id) {

        Optional<Recipe> recipe = getRecipe(id);
        if (recipe.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(recipeMapper.toDto(recipe.get()));

    }

    @Override
    public List<RecipeDto> findRecipesWithNameContaining(final String name) {

        List<Recipe> list = recipeRepository.findAllByNameIgnoreCaseContainingOrderByDateDesc(name);
        return checkAndReturn(list);

    }

    @Override
    public List<RecipeDto> findRecipesInCategory(final String category) {

        List<Recipe> list = recipeRepository.findAllByCategoryIgnoreCaseOrderByDateDesc(category);
        return checkAndReturn(list);

    }

    /**
     * Validates a {@link java.util.List} of {@link com.spring.recipes.entities.Recipe}s and if it's not empty, returns
     * it, wrapped in a {@link java.util.List} of {@link com.spring.recipes.dto.RecipeDto}s, else returns an empty list.
     * @param recipeList {@link java.util.List} of {@link com.spring.recipes.entities.Recipe}s to be wrapped in a data transfer object.
     * @return an empty list if the passed {@link java.util.List} of {@link com.spring.recipes.entities.Recipe}s is empty,
     * else a {@link java.util.List} of {@link com.spring.recipes.dto.RecipeDto}s
     */
    private List<RecipeDto> checkAndReturn(List<Recipe> recipeList) {

        if (recipeList.isEmpty()) {
            return Collections.emptyList();
        }
        return recipeMapper.toDtoList(recipeList);

    }

    @Override
    public void deleteRecipe(final Recipe recipe) {

        recipe.setAuthor(null);
        recipeRepository.delete(recipe);

    }

}

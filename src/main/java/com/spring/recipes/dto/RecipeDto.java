package com.spring.recipes.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

/**
 * The main data transfer object that wraps a/unwraps to {@link com.spring.recipes.entities.Recipe}.
 * Contains the main information of the recipe.
 * @author Alex Giazitzis
 */
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Getter
public class RecipeDto {

    @NotBlank(message = "Recipe must have a name.")
    String name;

    @NotBlank(message = "Recipe should be categorized.")
    String category;

    LocalDateTime date = LocalDateTime.now();

    @NotBlank(message = "Recipe should have a description.")
    String description;

    @NotNull
    @Size(min = 1, message = "Recipe should contain at least one ingredient.")
    List<@NotBlank String> ingredients;

    @NotNull
    @Size(min = 1, message = "Recipe should have at least one direction to be made.")
    List<@NotBlank String> directions;

}

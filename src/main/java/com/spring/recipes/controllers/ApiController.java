package com.spring.recipes.controllers;

import com.spring.recipes.dto.RecipeDto;
import com.spring.recipes.dto.RegisterUserDto;
import com.spring.recipes.entities.Recipe;
import com.spring.recipes.entities.user.User;
import com.spring.recipes.entities.user.UserDetailsImpl;
import com.spring.recipes.services.RecipeService;
import com.spring.recipes.services.UserService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * {@link org.springframework.web.bind.annotation.RestController} for the /api/** endpoints. Secured with {@link com.spring.recipes.config.WebSecConfig}, exposing only /api/register
 * to everyone. Handles user posting/updating/deleting as well, based on authorization.
 *
 * @author Alex Giazitzis
 */
@RestController
@RequestMapping(path = "/api")
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ApiController {

    RecipeService recipeService;
    UserService   userService;

    /**
     * POST /api/register endpoint <br>
     * Users are able to register by providing a JSON payload with a user and password fields, which are validated
     * and if found invalid, return a {@link org.springframework.http.HttpStatus} 400 Bad Request that contains a message
     * of what was wrong.
     *
     * @param dto {@link com.spring.recipes.dto.RegisterUserDto} that contains the user and password the user registered with.
     */
    @PostMapping({"/register", "/register/"})
    public void registerUser(@Valid @RequestBody final RegisterUserDto dto) {

        if (userService.isEmailInUse(dto.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        userService.save(dto);

    }

    /**
     * POST /api/recipe/new endpoint <br>
     * Users can post new recipes they want uploaded on the server with a JSON payload of the form : <br>
     * <pre>
     * {
     *     "name" : "name...",
     *     "category" : "category...",
     *     "description" : "description...",
     *     "ingredients" : [
     *          "ingredient 1",
     *          "ingredient 2",
     *          ...
     *     ]
     *     "directions" : [
     *          "direction 1",
     *          "direction 2",
     *          ...
     *     ]
     * }
     * </pre>
     * Only registered users can use the endpoint. If the recipe is invalid an {@link org.springframework.http.HttpStatus}
     * 400 Bad Request is returned with a message of what was invalid.
     *
     * @param dto         {@link com.spring.recipes.dto.RecipeDto} with a valid recipe.
     * @param userDetails {@link com.spring.recipes.entities.user.UserDetailsImpl} with the users information.
     * @return JSON payload with the id of the recipe that was successfully posted.
     */
    @PostMapping({"/recipe/new", "/recipe/new/"})
    public Map<String, Long> postRecipe(@Valid @RequestBody final RecipeDto dto,
                                        @AuthenticationPrincipal UserDetailsImpl userDetails) {

        Optional<User> user = userService.getUser(userDetails.getId());
        if (user.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        Long id = recipeService.save(dto, user.get());

        userService.update(user.get(), recipeService.getRecipe(id).orElse(null));

        return Map.of("id", id);

    }

    /**
     * GET /api/recipe/{id} <br>
     * Returns a JSON payload of the recipe with the specified ID, if it exists. Only registered users can access the endpoint.
     *
     * @param id of the recipe to search for.
     * @return {@link com.spring.recipes.dto.RecipeDto} with the information of the requested recipe.
     */
    @GetMapping({"/recipe/{id}", "/recipe/{id}/"})
    public RecipeDto getRecipe(@PathVariable final Long id) {

        Optional<RecipeDto> dto = recipeService.getRecipeDto(id);

        if (dto.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return dto.get();

    }

    /**
     * DELETE /api/recipe/{id} <br>
     * Deletes the specified recipe only if the recipe is posted by the user trying to delete it.
     *
     * @param id of the recipe to delete.
     * @param userDetails {@link com.spring.recipes.entities.user.UserDetailsImpl} with the users information.
     */
    @DeleteMapping({"/recipe/{id}", "/recipe/{id}/"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRecipe(@PathVariable final Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {

        Optional<Recipe> recipe = recipeService.getRecipe(id);
        if (recipe.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        if (!userService.removeRecipeFromUser(recipe.get(), userDetails.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        recipeService.deleteRecipe(recipe.get());

    }

    /**
     * PUT /api/recipe/{id} <br>
     * Allows a user to update a recipe, only if the recipe they're trying to manipulate is created by them.
     * @param id of the recipe to update
     * @param dto {@link com.spring.recipes.dto.RecipeDto} as JSON with the same structure as when posting it
     * @param userDetails {@link com.spring.recipes.entities.user.UserDetailsImpl} with the users information.
     */
    @PutMapping({"/recipe/{id}", "/recipe/{id}/"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateRecipe(@PathVariable final Long id, @Valid @RequestBody final RecipeDto dto,
                             @AuthenticationPrincipal UserDetailsImpl userDetails) {

        Optional<Recipe> recipe = recipeService.getRecipe(id);
        if (recipe.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        if (!userService.isRecipeOfUser(recipe.get(), userDetails.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        recipeService.update(id, dto);

    }

    /**
     * GET /api/recipe/search <br>
     * Allows the a registered user to query the server and get a list of recipes, by passing a parameter in the request
     * url of key <strong>name</strong> or <strong>category</strong>, effectively searching by either the recipe <em>name</em>
     * containing the passed value or the recipe being in the <em>category</em> specified.
     * @param pathVariables name or category with a value.
     * @return {@link java.util.List} of {@link com.spring.recipes.dto.RecipeDto}s in a JSON format.
     */
    @GetMapping({"/recipe/search", "/recipe/search/"})
    public List<RecipeDto> getRecipeByNameOrCategory(@Valid @RequestParam Map<@NotBlank String, @NotBlank String> pathVariables) {

        if (pathVariables.size() != 1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        } else if (pathVariables.containsKey("name")) {
            return recipeService.findRecipesWithNameContaining(pathVariables.get("name"));
        } else if (pathVariables.containsKey("category")) {
            return recipeService.findRecipesInCategory(pathVariables.get("category"));
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

    }

}

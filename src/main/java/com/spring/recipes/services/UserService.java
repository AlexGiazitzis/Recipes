package com.spring.recipes.services;

import com.spring.recipes.dto.RegisterUserDto;
import com.spring.recipes.entities.Recipe;
import com.spring.recipes.entities.user.User;

import java.util.Optional;

/**
 * @author Alex Giazitzis
 */
public interface UserService {

    void save(RegisterUserDto dto);

    void update(User user, Recipe recipe);

    Optional<User> getUser(final Long id);

    boolean isEmailInUse(final String email);

    boolean removeRecipeFromUser(final Recipe recipe, final Long id);

    boolean isRecipeOfUser(final Recipe recipe, final Long id);

}

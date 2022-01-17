package com.spring.recipes.services.impl;

import com.spring.recipes.dto.RegisterUserDto;
import com.spring.recipes.entities.Recipe;
import com.spring.recipes.entities.user.User;
import com.spring.recipes.mapper.UserMapper;
import com.spring.recipes.repo.UserRepository;
import com.spring.recipes.services.UserService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author Alex Giazitzis
 */
@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {

    UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    UserMapper userMapper;

    @Override
    public void save(final RegisterUserDto dto) {

        User user = userMapper.getUser(dto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

    }

    @Override
    public void update(final User user, final Recipe recipe) {

        user.getRecipes().add(recipe);
        userRepository.save(user);

    }

    @Override
    public Optional<User> getUser(final Long id) {

        return userRepository.findById(id);

    }

    @Override
    public boolean isEmailInUse(final String email) {

        Optional<User> user = userRepository.findUserByEmail(email);
        return user.isPresent();

    }

    @Override
    public boolean removeRecipeFromUser(final Recipe recipe, final Long id) {

        Optional<User> user = userRepository.findById(id);
        return user.map(value -> value.getRecipes().remove(recipe)).orElse(false);

    }

    @Override
    public boolean isRecipeOfUser(final Recipe recipe, final Long id) {

        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            return false;
        }
        return user.get().getRecipes().contains(recipe);

    }
}

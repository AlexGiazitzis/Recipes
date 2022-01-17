package com.spring.recipes.services.impl;

import com.spring.recipes.entities.user.User;
import com.spring.recipes.entities.user.UserDetailsImpl;
import com.spring.recipes.repo.UserRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * The implementation of the {@link org.springframework.security.core.userdetails.UserDetailsService} interface
 * utilized when a user does an HTTP Basic Authentication with the server.
 * @author Alex Giazitzis
 */
@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserDetailsServiceImpl implements UserDetailsService {

    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {

        Optional<User> user = userRepository.findUserByEmail(username);

        if (user.isEmpty()) {
            throw new UsernameNotFoundException("Email not registered.");
        }

        return new UserDetailsImpl(user.get());

    }

}

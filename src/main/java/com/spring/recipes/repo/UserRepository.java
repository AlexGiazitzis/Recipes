package com.spring.recipes.repo;

import com.spring.recipes.entities.user.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 *
 * @author Alex Giazitzis
 */
@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    /**
     * Searches for a user by an {@code email} value.
     * @param email value to query by.
     * @return {@link java.util.Optional} of {@link com.spring.recipes.entities.user.User} that may or may not exist.
     */
    Optional<User> findUserByEmail(final String email);

}

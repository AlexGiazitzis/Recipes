package com.spring.recipes.mapper;

import com.spring.recipes.dto.RegisterUserDto;
import com.spring.recipes.entities.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * {@link org.mapstruct.Mapper} for unwrapping a {@link com.spring.recipes.dto.RegisterUserDto} to a {@link com.spring.recipes.entities.user.User} entity.
 * @author Alex Giazitzis
 */
@Mapper
public interface UserMapper {

    /**
     * Unwraps a {@link com.spring.recipes.dto.RegisterUserDto} to a {@link com.spring.recipes.entities.user.User} while ignoring
     * some fields of the latter so they can be set later or default initialized, while giving the new entity a standard value for the
     * role field.
     * @param dto {@link com.spring.recipes.dto.RegisterUserDto} to be unwrapped
     * @return {@link com.spring.recipes.entities.user.User} that registered.
     */
    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "role", constant = "ROLE_USER"),
            @Mapping(target = "recipes", ignore = true)
    })
    User getUser(RegisterUserDto dto);

}

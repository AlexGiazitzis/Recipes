package com.spring.recipes.config;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * The configuration class for the server's security. Overrides two configure methods from
 * {@link org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter} and offers a {@link org.springframework.security.crypto.password.PasswordEncoder} bean with
 * returning an object of {@link org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder}.
 *
 * @author Alex Giazitzis
 * @see WebSecurityConfigurerAdapter
 */
@Configuration
@EnableWebSecurity
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class WebSecConfig extends WebSecurityConfigurerAdapter {

    UserDetailsService userDetailsService;

    /**
     * Configures the service class for the Authentication process to use along with the password encoder.
     *
     * @param auth the default {@link org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder} with which the UserDetails service class and PasswordEncoder are specified.
     * @throws Exception if an error occurs when adding the UserDetailsService based authentication.
     */
    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(userDetailsService).passwordEncoder(getEncoder());

    }

    /**
     * Configures the accessibility of the provided endpoints based on roles and/or authentication status.
     *
     * @param http the default {@link org.springframework.security.config.annotation.web.builders.HttpSecurity} with which the accessibility is specified.
     * @throws Exception
     */
    @Override
    protected void configure(final HttpSecurity http) throws Exception {

        http.authorizeRequests()
            .mvcMatchers("/api/register").permitAll()
            .mvcMatchers("/api/**").authenticated()
            .mvcMatchers("/**").permitAll()
            .and()
            .csrf().disable().headers().frameOptions().disable()
            .and()
            .httpBasic();

    }

    /**
     * Returns an instance of a PasswordEncoder that's used for the registration and authentication of the users.
     *
     * @return {@link org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder} instance.
     */
    @Bean
    public PasswordEncoder getEncoder() {
        return new BCryptPasswordEncoder();
    }
}

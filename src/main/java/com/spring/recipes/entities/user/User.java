package com.spring.recipes.entities.user;

import com.spring.recipes.entities.Recipe;
import com.spring.recipes.utils.Default;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Alex Giazitzis
 */
@Entity
@Table(indexes = @Index(name = "user_id_index", columnList = "id"))
@NoArgsConstructor
@AllArgsConstructor(onConstructor_ = @Default)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_gen")
    @SequenceGenerator(name = "user_id_gen", sequenceName = "user_id_seq")
    Long id;

    @NaturalId(mutable = true)
    @Column(nullable = false, unique = true)
    String email;

    @Column(nullable = false)
    String password;

    @Column(nullable = false)
    String role;

    @OneToMany
    List<Recipe> recipes = new ArrayList<>();

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return email.equals(user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}

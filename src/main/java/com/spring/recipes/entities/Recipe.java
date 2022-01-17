package com.spring.recipes.entities;

import com.spring.recipes.entities.user.User;
import com.spring.recipes.utils.Default;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Alex Giazitzis
 */
@Entity
@Table(indexes = @Index(name = "recipe_id_index", columnList = "id"))
@NoArgsConstructor
@AllArgsConstructor(onConstructor_ = @Default)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "recipe_id_gen")
    @SequenceGenerator(name = "recipe_id_gen", sequenceName = "recipe_id_seq")
    Long id;

    @ManyToOne
    User author;

    String        name;
    String        category;
    LocalDateTime date;
    String        description;

    @ElementCollection
    @CollectionTable(name = "recipe_ingredients", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "ingredients")
    List<String> ingredients = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "recipe_directions", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "directions")
    List<String> directions = new ArrayList<>();

}

package com.abn.challenge.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Recipe {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String title;
    boolean vegetarian;
    int servings;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "recipe_ingredients", joinColumns = @JoinColumn(name = "recipe_id"))
    @Column(name = "ingredients")
    List<String> ingredients;
    String instructions;

}

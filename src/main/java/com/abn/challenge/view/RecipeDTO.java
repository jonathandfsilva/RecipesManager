package com.abn.challenge.view;

import com.abn.challenge.model.Recipe;
import lombok.Data;

import java.util.List;

@Data
public class RecipeDTO {

    private String title;
    boolean vegetarian;
    int servings;
    List<String> ingredients;
    String instructions;

    public Recipe toPersistent(Long id){
        return  Recipe.builder().id(id).title(this.title).vegetarian(this.vegetarian)
                .servings(this.servings).ingredients(this.ingredients)
                .instructions(this.instructions).build();
    }

}

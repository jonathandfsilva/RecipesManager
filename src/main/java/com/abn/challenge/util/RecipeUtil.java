package com.abn.challenge.util;

import com.abn.challenge.model.Recipe;
import com.abn.challenge.view.RecipeDTO;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

//TODO: MIGRATE TO MAPSTRUCT
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RecipeUtil {

    public static Recipe toPersistent(RecipeDTO recipe, Long id){
        return Recipe.builder().id(id).title(recipe.title()).vegetarian(recipe.vegetarian()).servings(recipe.servings()).ingredients(recipe.ingredients()).instructions(recipe.instructions()).build();
    }
}

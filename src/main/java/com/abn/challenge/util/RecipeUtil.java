package com.abn.challenge.util;

import com.abn.challenge.model.Recipe;
import com.abn.challenge.view.RecipeDTO;
import com.abn.challenge.view.UpdateRecipeDTO;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

//TODO: MIGRATE TO MAPSTRUCT
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RecipeUtil {

    public static Recipe toPersistent(RecipeDTO recipe, Long id){
        return Recipe.builder().id(id).title(recipe.title()).vegetarian(recipe.vegetarian()).servings(recipe.servings()).ingredients(recipe.ingredients()).instructions(recipe.instructions()).build();
    }

    public static Recipe toPersistent(UpdateRecipeDTO recipe, Recipe old, Long id){
        return Recipe.builder().id(id)
                .title(recipe.title() !=null ? recipe.title() : old.getTitle())
                .vegetarian(recipe.vegetarian() !=null ? recipe.vegetarian() : old.isVegetarian())
                .servings(recipe.servings() !=null ? recipe.servings() : old.getServings())
                .ingredients(recipe.ingredients() !=null ? recipe.ingredients() : old.getIngredients())
                .instructions(recipe.instructions() !=null ? recipe.instructions() : old.getInstructions())
                .build();
    }
}

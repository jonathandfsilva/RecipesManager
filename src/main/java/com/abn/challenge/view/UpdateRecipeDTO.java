package com.abn.challenge.view;

import java.util.List;

public record UpdateRecipeDTO(
        String title,
        Boolean vegetarian,
        Integer servings,
        List<String> ingredients,
        String instructions) {}

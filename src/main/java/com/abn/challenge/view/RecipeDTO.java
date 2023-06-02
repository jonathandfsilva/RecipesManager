package com.abn.challenge.view;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record RecipeDTO(
        @NotBlank String title, boolean vegetarian,
        @Min(1) int servings,
        @NotEmpty List<String> ingredients,
        @NotEmpty String instructions) {}

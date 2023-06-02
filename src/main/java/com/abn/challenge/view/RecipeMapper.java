package com.abn.challenge.view;

import com.abn.challenge.model.Recipe;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RecipeMapper {

    RecipeDTO entityToDTO(Recipe recipe);
    Recipe dtoToEntity(RecipeDTO recipe);

}

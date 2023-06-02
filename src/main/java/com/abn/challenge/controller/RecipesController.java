package com.abn.challenge.controller;

import com.abn.challenge.model.Recipe;
import com.abn.challenge.service.RecipesService;
import com.abn.challenge.view.RecipeDTO;
import com.abn.challenge.view.UpdateRecipeDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.IsMember;
import net.kaczmarzyk.spring.data.jpa.domain.IsNotMember;
import net.kaczmarzyk.spring.data.jpa.domain.LikeIgnoreCase;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/recipes/")
public class RecipesController {

    @Autowired
    private RecipesService service;

    @GetMapping("{id}")
    public Recipe findById(@PathVariable("id") @Valid @Min(1) long id){
        return service.fetch(id);
    }

    @PostMapping
    public Recipe save(@RequestBody @Valid RecipeDTO recipe){
        return service.create(recipe);
    }

    @PutMapping("{id}")
    public Recipe edit(@PathVariable("id") @Min(1) long id, @RequestBody @Valid UpdateRecipeDTO recipe){
        return service.update(recipe, id);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void remove(@PathVariable("id") @Min(1) Long id){
        service.delete(id);
    }

    @GetMapping("filter")
    public Page<Recipe> filter(@And({
            @Spec(path="title", spec = LikeIgnoreCase.class),
            @Spec(path="vegetarian", spec = Equal.class),
            @Spec(path="servings", spec = Equal.class),
            @Spec(path="ingredients", params = "ingredients-include", paramSeparator = ',', spec = IsMember.class),
            @Spec(path="ingredients", params = "ingredients-exclude", paramSeparator = ',', spec = IsNotMember.class),
            @Spec(path="instructions", spec = LikeIgnoreCase.class)
    }) @Valid Specification<Recipe> specs, @RequestParam("page") @Min(0) int page,
                               @RequestParam("size") @Min(1) int size) {
        return service.filter(specs, page, size);
    }

}

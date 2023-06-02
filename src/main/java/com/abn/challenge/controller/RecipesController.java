package com.abn.challenge.controller;

import com.abn.challenge.dao.RecipeRepository;
import com.abn.challenge.model.Recipe;
import com.abn.challenge.util.RecipeUtil;
import com.abn.challenge.view.RecipeDTO;
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
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/recipes/")
public class RecipesController {

    @Autowired
    private RecipeRepository repository;

    @GetMapping("{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public Recipe findById(@PathVariable("id") @Valid @Min(1) long id){
        Optional<Recipe> recipe = repository.findById(id);
        return recipe.orElseThrow(() -> new ResourceNotFoundException(String.format("ID = %s", id)));
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.OK)
    public Recipe save(@RequestBody @Valid RecipeDTO recipe){
        return repository.save(RecipeUtil.toPersistent(recipe, null));
    }

    @PutMapping("{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public Recipe edit(@PathVariable("id") @Min(1) long id, @RequestBody @Valid RecipeDTO recipe){
        if(repository.findById(id).isPresent())
            return repository.save(RecipeUtil.toPersistent(recipe, id));
        else
            throw new ResourceNotFoundException();
    }

    @DeleteMapping("{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void remove(@PathVariable("id") @Min(1) Long id){
        repository.deleteById(id);
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
        Page<Recipe> fullPage = repository.findAll(specs, Pageable.ofSize(size).withPage(page));
        if(fullPage.getTotalElements() > 0)
            return fullPage;
        else
            throw new ResourceNotFoundException();
    }

}

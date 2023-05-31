package com.abn.challenge.controller;

import com.abn.challenge.dao.RecipeRepository;
import com.abn.challenge.model.Recipe;
import com.abn.challenge.view.RecipeDTO;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.In;
import net.kaczmarzyk.spring.data.jpa.domain.LikeIgnoreCase;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/recipes/")
public class RecipesController {

    @Autowired
    private RecipeRepository repository;

    @GetMapping
    public List<Recipe> getAll(){
        return repository.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<Recipe> findById(@RequestParam(value = "id") Long id){
        Optional<Recipe> recipe = repository.findById(id);
        return recipe.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Recipe> save(@RequestBody RecipeDTO recipe){
        return ResponseEntity.ok(repository.save(recipe.toPersistent(null)));
    }

    @PutMapping("{id}")
    public ResponseEntity<Recipe> edit(@RequestParam(value = "id") Long id, @RequestBody RecipeDTO recipe){
        return ResponseEntity.ok(repository.save(recipe.toPersistent(id)));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> remove(@RequestParam(value = "id") Long id){
        repository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("filter")
    @Parameter(in = ParameterIn.QUERY, name = "title", content = @Content(schema = @Schema(type = "string")))
    @Parameter(in = ParameterIn.QUERY, name = "vegetarian", content = @Content(schema = @Schema(type = "boolean", defaultValue = "false")))
    @Parameter(in = ParameterIn.QUERY, name = "servings", content = @Content(schema = @Schema(type = "integer", defaultValue = "0")))
    @Parameter(in = ParameterIn.QUERY, name = "ingredients", content = @Content(schema = @Schema(type = "string")))
    @Parameter(in = ParameterIn.QUERY, name = "instructions", content = @Content(schema = @Schema(type = "string")))
    public List<Recipe> filterByParent(@And({
            @Spec(path="title", pathVars="title", spec = LikeIgnoreCase.class),
            @Spec(path="vegetarian", pathVars="vegetarian", spec = Equal.class),
            @Spec(path="servings", pathVars="servings", spec = Equal.class),
            @Spec(path="ingredients", pathVars="ingredients", spec = In.class),
            @Spec(path="instructions", pathVars="instructions", spec = LikeIgnoreCase.class)
    }) Specification<Recipe> specs) {
        return repository.findAll(specs);
    }

}

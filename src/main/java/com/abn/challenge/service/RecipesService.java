package com.abn.challenge.service;

import com.abn.challenge.dao.RecipeRepository;
import com.abn.challenge.model.Recipe;
import com.abn.challenge.util.RecipeUtil;
import com.abn.challenge.view.RecipeDTO;
import com.abn.challenge.view.UpdateRecipeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RecipesService {

    @Autowired
    private RecipeRepository repository;

    public Recipe fetch(Long id){
        Optional<Recipe> recipe = repository.findById(id);
        return recipe.orElseThrow(() -> new ResourceNotFoundException(String.format("ID = %s", id)));
    }

    public Recipe create(RecipeDTO recipe){
        return repository.save(RecipeUtil.toPersistent(recipe, null));
    }

    public Recipe update(UpdateRecipeDTO recipe, Long id){
        Recipe current = repository.findById(id).orElseThrow(ResourceNotFoundException::new);
        return repository.save(RecipeUtil.toPersistent(recipe, current, current.getId()));
    }

    public void delete(Long id){
        repository.deleteById(id);
    }

    public Page<Recipe> filter(Specification<Recipe> specs, int page, int size){
        Page<Recipe> fullPage = repository.findAll(specs, Pageable.ofSize(size).withPage(page));
        if(fullPage.getTotalElements() > 0)
            return fullPage;
        else
            throw new ResourceNotFoundException();
    }

}

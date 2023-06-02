package com.abn.challenge.controller;

import com.abn.challenge.dao.RecipeRepository;
import com.abn.challenge.model.Recipe;
import com.abn.challenge.view.RecipeDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.instancio.Instancio;
import org.instancio.settings.Keys;
import org.instancio.settings.Settings;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-integration-tests.properties")
class RecipesControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private RecipeRepository repository;

    private static final String BASE_PATH = "/recipes/";

    Settings settings = Settings.create().set(Keys.BEAN_VALIDATION_ENABLED, true);

    @Test
    void getFound() throws Exception {
        Recipe recipe = repository.save(Instancio.of(Recipe.class).withSettings(settings).create());
        MockHttpServletResponse response = performGet(recipe.getId().toString());
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    void getNotFound() throws Exception {
        MockHttpServletResponse response = performGet("999999999");
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    }

    @Test
    void saveSuccess() throws Exception {
        RecipeDTO recipe = Instancio.of(RecipeDTO.class).withSettings(settings).create();
        MockHttpServletResponse response = performPost(recipe);
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    void editSuccess() throws Exception {
        Recipe recipe = repository.save(Instancio.of(Recipe.class).withSettings(settings).create());
        RecipeDTO newRecipe = Instancio.of(RecipeDTO.class).create();
        MockHttpServletResponse response = performPut(recipe.getId().toString(), newRecipe);
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    void editNotFound() throws Exception {
        RecipeDTO newRecipe = Instancio.of(RecipeDTO.class).withSettings(settings).create();
        MockHttpServletResponse response = performPut("99999999", newRecipe);
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    }

    @Test
    void paginatedSearch() throws Exception {
        String query = String.format("filter?page=%s&size=%s", 0, 5);
        repository.saveAll(Instancio.ofList(Recipe.class).size(5).withSettings(settings).create());
        MockHttpServletResponse response = performGet(query);
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    void filterNotFound() throws Exception {
        String query = String.format("filter?page=%s&size=%s&instructions=LOREM-IPSUM", 0, 5);
        repository.saveAll(Instancio.ofList(Recipe.class).size(3).withSettings(settings).create());
        MockHttpServletResponse response = performGet(query);
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    }

    @Test
    void deleteSuccess() throws Exception {
        Recipe recipe = repository.save(Instancio.of(Recipe.class).withSettings(settings).create());
        MockHttpServletResponse response = performDelete(recipe.getId().toString());
        assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());
    }

    private MockHttpServletResponse performGet(String subPath) throws Exception {
        return mvc.perform(get(BASE_PATH.concat(subPath)).contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse();
    }

    private MockHttpServletResponse performDelete(String subPath) throws Exception {
        return mvc.perform(delete(BASE_PATH.concat(subPath))).andReturn().getResponse();
    }

    private MockHttpServletResponse performPost(RecipeDTO request) throws Exception {
        return mvc.perform(post(BASE_PATH)
                        .content(new ObjectMapper().writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
    }

    private MockHttpServletResponse performPut(String subPath, RecipeDTO request) throws Exception {
        return mvc.perform(put(BASE_PATH.concat(subPath))
                        .content(new ObjectMapper().writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
    }

}
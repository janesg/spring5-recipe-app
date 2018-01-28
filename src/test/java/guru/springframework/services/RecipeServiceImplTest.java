package guru.springframework.services;

import guru.springframework.converters.RecipeCommandToRecipe;
import guru.springframework.converters.RecipeToRecipeCommand;
import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RecipeServiceImplTest {

    private RecipeServiceImpl recipeService;

    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    RecipeToRecipeCommand recipeToRecipeCommand;

    @Mock
    RecipeCommandToRecipe recipeCommandToRecipe;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        recipeService =
                new RecipeServiceImpl(recipeRepository, recipeCommandToRecipe, recipeToRecipeCommand);
    }

    @Test
    public void getRecipeById() {

        Recipe recipe = new Recipe();
        recipe.setId(1L);

        Optional<Recipe> recipeOpt = Optional.of(recipe);

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOpt);

        Recipe recipeRet = recipeService.findById(1L);

        assertNotNull("Null Recipe returned", recipeRet);
        verify(recipeRepository, times(1)).findById(anyLong());
    }

    @Test
    public void getRecipes() {

        Recipe recipe = new Recipe();
        Set<Recipe> data = new HashSet<>();
        data.add(recipe);

        when(recipeService.getRecipes()).thenReturn(data);

        Set<Recipe> recipes = recipeService.getRecipes();

        assertEquals(1, recipes.size());
        verify(recipeRepository, times(1)).findAll();
    }

    @Test
    public void deleteRecipeById() {

        // No 'when' mock setup, since service method returns void

        recipeService.deleteById(Long.valueOf(1L));

        verify(recipeRepository, times(1)).deleteById(anyLong());
    }

}
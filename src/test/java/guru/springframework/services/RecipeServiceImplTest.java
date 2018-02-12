package guru.springframework.services;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.converters.RecipeCommandToRecipe;
import guru.springframework.converters.RecipeToRecipeCommand;
import guru.springframework.domain.Recipe;
import guru.springframework.exceptions.NotFoundException;
import guru.springframework.repositories.RecipeRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RecipeServiceImplTest {

    private RecipeServiceImpl recipeService;

    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    private RecipeToRecipeCommand recipeToRecipeCommand;

    @Mock
    private RecipeCommandToRecipe recipeCommandToRecipe;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        recipeService =
                new RecipeServiceImpl(recipeRepository, recipeCommandToRecipe, recipeToRecipeCommand);
    }

    @Test
    public void getRecipeById() {

        Recipe recipe = new Recipe();
        recipe.setId("1");
        RecipeCommand recipeCmd = new RecipeCommand();
        recipeCmd.setId("1");

        Optional<Recipe> recipeOpt = Optional.of(recipe);

        when(recipeRepository.findById(anyString())).thenReturn(recipeOpt);
        when(recipeToRecipeCommand.convert(recipeOpt.get())).thenReturn(recipeCmd);

        RecipeCommand recipeRet = recipeService.findById("1");

        assertNotNull("Null Recipe returned", recipeRet);
        verify(recipeRepository, times(1)).findById(anyString());
    }

    @Test(expected = NotFoundException.class)
    public void getRecipeByIdNotFound() {

        Optional<Recipe> recipeOptional = Optional.empty();

        when(recipeRepository.findById(anyString())).thenReturn(recipeOptional);

        recipeService.findById("1");
    }

    @Test
    public void getRecipes() {

        Recipe recipe = new Recipe();
        Set<Recipe> data = new HashSet<>();
        data.add(recipe);

        RecipeCommand recipeCmd = new RecipeCommand();

        when(recipeRepository.findAll()).thenReturn(data);
        when(recipeToRecipeCommand.convert(recipe)).thenReturn(recipeCmd);

        Set<RecipeCommand> recipes = recipeService.getRecipes();

        assertEquals(1, recipes.size());
        verify(recipeRepository, times(1)).findAll();
    }

    @Test
    public void deleteRecipeById() {

        // No 'when' mock setup, since service method returns void

        recipeService.deleteById("1");

        verify(recipeRepository, times(1)).deleteById(anyString());
    }

}
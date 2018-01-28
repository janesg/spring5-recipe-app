package guru.springframework.services;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.Recipe;

import java.util.Set;

public interface RecipeService {

    Set<RecipeCommand> getRecipes();

    RecipeCommand findById(Long id);

    RecipeCommand saveRecipe(RecipeCommand command);

    void deleteById(Long id);

}

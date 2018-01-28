package guru.springframework.services;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.converters.IngredientToIngredientCommand;
import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class IngredientServiceImpl implements IngredientService {

    private final RecipeRepository recipeRepository;
    private final IngredientToIngredientCommand ingredientToIngredientCommand;

    @Override
    public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {

        Optional<Recipe> recipeOpt = recipeRepository.findById(recipeId);

        if (recipeOpt.isPresent()) {
            Recipe recipe = recipeOpt.get();

            Optional<IngredientCommand> ingredientOpt = recipe.getIngredients().stream()
                    .filter(ingredient -> ingredientId.equals(ingredient.getId()))
                    .map(ingredientToIngredientCommand::convert)
                    .findFirst();

            if (ingredientOpt.isPresent()) {
                return ingredientOpt.get();
            } else {
                log.error("Ingredient not found (id = " + ingredientId + ") for Recipe (id = " + recipeId + ")");
            }
        } else {
            log.error("Recipe not found (id = " + recipeId + ")");
        }

        return null;
    }
}

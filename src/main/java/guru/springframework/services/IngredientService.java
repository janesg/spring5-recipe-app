package guru.springframework.services;

import guru.springframework.commands.IngredientCommand;
import org.springframework.transaction.annotation.Transactional;

public interface IngredientService {

    IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId);

    IngredientCommand saveIngredient(IngredientCommand command);

    void deleteById(Long recipeId, Long ingredientId);

}

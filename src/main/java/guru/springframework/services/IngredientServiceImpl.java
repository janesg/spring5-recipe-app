package guru.springframework.services;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.converters.IngredientCommandToIngredient;
import guru.springframework.converters.IngredientToIngredientCommand;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.repositories.UnitOfMeasureRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class IngredientServiceImpl implements IngredientService {

    private final RecipeRepository recipeRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;

    @Override
    public IngredientCommand findByRecipeIdAndIngredientId(String recipeId, String ingredientId) {

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

    @Override
    @Transactional
    public IngredientCommand saveIngredient(IngredientCommand ingredientCommand) {

        Optional<Recipe> recipeOptional = recipeRepository.findById(ingredientCommand.getRecipeId());

        if(!recipeOptional.isPresent()){
            return new IngredientCommand();
        } else {
            Recipe recipe = recipeOptional.get();

            Optional<Ingredient> ingredientOptional = recipe.getIngredients().stream()
                    .filter(ingredient -> ingredient.getId().equals(ingredientCommand.getId()))
                    .findFirst();

            if (ingredientOptional.isPresent()) {
                Ingredient ingredientFound = ingredientOptional.get();
                ingredientFound.setDescription(ingredientCommand.getDescription());
                ingredientFound.setAmount(ingredientCommand.getAmount());
                ingredientFound.setUom(unitOfMeasureRepository
                        .findById(ingredientCommand.getUom().getId())
                        .orElseThrow(() -> new RuntimeException("UOM NOT FOUND")));
            } else {
                // add new Ingredient
                recipe.addIngredient(ingredientCommandToIngredient.convert(ingredientCommand));
            }

            Recipe savedRecipe = recipeRepository.save(recipe);

            // Need to pass back the saved ingredient, but can't match on id it wasn't known in case of 'new'
            // Instead, we use a 'best guess' at a combination of attributes although not guaranteed
            Optional<Ingredient> ingredientOpt = savedRecipe.getIngredients().stream()
                    .filter(ingredient -> ingredient.getDescription().equals(ingredientCommand.getDescription()) &&
                                          ingredient.getAmount().equals(ingredientCommand.getAmount()) &&
                                          ingredient.getUom().getId().equals(ingredientCommand.getUom().getId()))
                    .findFirst();

            if (ingredientOpt.isPresent()) {
                return ingredientToIngredientCommand.convert(ingredientOpt.get());
            } else {
                throw new RuntimeException("Failed to save ingredient for recipe (id : " + ingredientCommand.getRecipeId() + ")");
            }
        }
    }

    @Override
    public void deleteById(String recipeId, String ingredientId) {

        log.debug("Deleting Ingredient (id = " + ingredientId + ") from Recipe (id = " + recipeId + ")");

        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

        if (recipeOptional.isPresent()) {
            Recipe recipe = recipeOptional.get();

            Optional<Ingredient> ingredientOptional = recipe.getIngredients().stream()
                    .filter(ingredient -> ingredient.getId().equals(ingredientId))
                    .findFirst();

            if (ingredientOptional.isPresent()) {
                // Have to set the recipe reference to null to get Hibernate to physically delete record
                Ingredient ingredient = ingredientOptional.get();
                ingredient.setRecipe(null);

                recipe.getIngredients().remove(ingredientOptional.get());
                recipeRepository.save(recipe);
            } else {
                throw new RuntimeException("Failed to find ingredient (id : " + ingredientId + ") for recipe (id : " + recipeId + ")");
            }
        } else {
            throw new RuntimeException("Failed to find recipe (id : " + recipeId + ")");
        }
    }
}

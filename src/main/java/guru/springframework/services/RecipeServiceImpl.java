package guru.springframework.services;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.converters.RecipeCommandToRecipe;
import guru.springframework.converters.RecipeToRecipeCommand;
import guru.springframework.domain.Recipe;
import guru.springframework.exceptions.NotFoundException;
import guru.springframework.repositories.RecipeRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Service
@AllArgsConstructor
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeCommandToRecipe recipeCommandToRecipe;
    private final RecipeToRecipeCommand recipeToRecipeCommand;

    @Override
    public Set<RecipeCommand> getRecipes() {
        log.info("Get those recipes...");

        return StreamSupport.stream(recipeRepository.findAll().spliterator(), false)
                .map(recipeToRecipeCommand::convert)
                .collect(Collectors.toSet());
    }

    @Override
    public RecipeCommand findById(Long id) {

        Optional<Recipe> recipeOpt = recipeRepository.findById(id);

        if (recipeOpt.isPresent()) {
            return recipeToRecipeCommand.convert(recipeOpt.get());
        } else {
            throw new NotFoundException("Recipe not found with ID = " + id);
        }
    }

    @Override
    @Transactional
    public RecipeCommand saveRecipe(RecipeCommand command) {
        Recipe detachedRecipe = recipeCommandToRecipe.convert(command);

        Recipe savedRecipe = recipeRepository.save(detachedRecipe);
        log.debug("Saved Recipe Id: " + savedRecipe.getId());

        return recipeToRecipeCommand.convert(savedRecipe);
    }

    @Override
    public void deleteById(Long id) {

        recipeRepository.deleteById(id);
    }

}

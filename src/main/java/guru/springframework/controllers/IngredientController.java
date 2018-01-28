package guru.springframework.controllers;

import guru.springframework.services.IngredientService;
import guru.springframework.services.RecipeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Slf4j
@Controller
@AllArgsConstructor
public class IngredientController {

    private final RecipeService recipeService;
    private final IngredientService ingredientService;

    @GetMapping("/recipe/{recipeId}/ingredients")
    public String getIngredientsForRecipe(@PathVariable String recipeId, Model model) {

        log.debug("Getting ingredients for recipe id : " + recipeId);

        model.addAttribute("recipe", recipeService.findById(Long.valueOf(recipeId)));

        return "recipe/ingredient/list";
    }

    @GetMapping("/recipe/{recipeId}/ingredient/{ingredientId}")
    public String getIngredientForRecipe(@PathVariable String recipeId,
                                         @PathVariable String ingredientId,
                                         Model model) {

        log.debug("Getting ingredient : " + ingredientId + " for recipe id : " + recipeId);

        model.addAttribute("ingredient",
                ingredientService.findByRecipeIdAndIngredientId(Long.valueOf(recipeId),
                                                                Long.valueOf(ingredientId)));

        return "recipe/ingredient/show";
    }
}

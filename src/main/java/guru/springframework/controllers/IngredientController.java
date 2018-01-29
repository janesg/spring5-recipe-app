package guru.springframework.controllers;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.services.IngredientService;
import guru.springframework.services.RecipeService;
import guru.springframework.services.UnitOfMeasureService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
@AllArgsConstructor
public class IngredientController {

    private final RecipeService recipeService;
    private final IngredientService ingredientService;
    private final UnitOfMeasureService unitOfMeasureService;

    @GetMapping("/recipe/{recipeId}/ingredients")
    public String getIngredientsForRecipe(@PathVariable String recipeId, Model model) {

        log.debug("Getting ingredients for recipe id : " + recipeId);

        model.addAttribute("recipe", recipeService.findById(Long.valueOf(recipeId)));

        return "recipe/ingredient/list";
    }

    @GetMapping("/recipe/{recipeId}/ingredient/{ingredientId}/update")
    public String updateIngredientForRecipe(@PathVariable String recipeId,
                                            @PathVariable String ingredientId,
                                            Model model) {

        log.debug("Updating ingredient : " + ingredientId + " for recipe id : " + recipeId);

        model.addAttribute("ingredient",
                ingredientService.findByRecipeIdAndIngredientId(Long.valueOf(recipeId),
                        Long.valueOf(ingredientId)));

        model.addAttribute("uomList", unitOfMeasureService.getAllUoms());

        return "recipe/ingredient/ingredientform";
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

    @PostMapping("recipe/{recipeId}/ingredient")
    public String saveOrUpdate(@PathVariable String recipeId,
                               @ModelAttribute IngredientCommand command) {

        log.debug("Saving ingredient for recipe with id = " + recipeId);

        IngredientCommand savedCommand = ingredientService.saveIngredient(command);

        log.debug("Saved recipe id : " + savedCommand.getRecipeId());
        log.debug("Saved ingredient id : " + savedCommand.getId());

        return "redirect:/recipe/" + savedCommand.getRecipeId() + "/ingredient/" + savedCommand.getId();
    }
}

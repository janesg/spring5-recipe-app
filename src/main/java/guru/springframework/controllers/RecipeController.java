package guru.springframework.controllers;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.converters.RecipeToRecipeCommand;
import guru.springframework.services.RecipeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
public class RecipeController {

    private final RecipeService recipeService;
    private final RecipeToRecipeCommand recipeToRecipeCommand;

    /*
        Display a single recipe
     */
    @GetMapping("/recipe/{id}")
    public String showById(@PathVariable String id, Model model) {

        model.addAttribute("recipe", recipeService.findById(Long.parseLong(id)));

        return "recipe/show";
    }

    /*
        Display the recipe form for creating a new recipe
     */
    @GetMapping("/recipe/new")
    public String newRecipe(Model model) {
        model.addAttribute("recipe", new RecipeCommand());

        return "recipe/recipeform";
    }

    /*
        Display the recipe form for updating an existing recipe
     */
    @GetMapping("/recipe/{id}/update")
    public String updateRecipe(@PathVariable String id, Model model) {
        model.addAttribute("recipe", recipeToRecipeCommand.convert(recipeService.findById(Long.valueOf(id))));

        return "recipe/recipeform";
    }

    @PostMapping("recipe")
    public String saveOrUpdate(@ModelAttribute RecipeCommand recipeCmd) {

        RecipeCommand savedCmd = recipeService.saveRecipeCommand(recipeCmd);

        return "redirect:/recipe/" + savedCmd.getId();
    }

    @GetMapping("/recipe/{id}/delete")
    public String deleteRecipe(@PathVariable String id) {
        recipeService.deleteById(Long.valueOf(id));

        return "redirect:/";
    }

}

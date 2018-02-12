package guru.springframework.domain;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class Recipe {

    private String id;

    private String description;
    private Integer prepTime;
    private Integer cookTime;
    private Integer servings;
    private String source;
    private String url;

    private String directions;

    private Difficulty difficulty;

    private Byte[] image;

    private RecipeNotes notes;

    private Set<Ingredient> ingredients = new HashSet<>();

    private Set<Category> categories = new HashSet<>();

    public Recipe setNotes(RecipeNotes notes) {
        if (notes != null) {
            this.notes = notes;
            // Explicit setting of the bi-directional relationship
            this.notes.setRecipe(this);
        }

        return this;
    }

    public void setIngredients(Set<Ingredient> ingredients) {
        this.ingredients = new HashSet<>();
        ingredients.forEach(this::addIngredient);
    }

    public Recipe addIngredient(Ingredient ingredient) {
        if (ingredient != null) {
            // Explicit setting of the bi-directional relationship
            ingredient.setRecipe(this);
            this.ingredients.add(ingredient);
        }

        return this;
    }
}

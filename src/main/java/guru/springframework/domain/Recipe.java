package guru.springframework.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Data
@Document
public class Recipe {

    @Id
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

    @DBRef
    private Set<Category> categories = new HashSet<>();

    public Recipe setNotes(RecipeNotes notes) {
        if (notes != null) {
            this.notes = notes;
        }

        return this;
    }

    public void setIngredients(Set<Ingredient> ingredients) {
        this.ingredients = new HashSet<>();
        ingredients.forEach(this::addIngredient);
    }

    public Recipe addIngredient(Ingredient ingredient) {
        if (ingredient != null) {
            this.ingredients.add(ingredient);
        }

        return this;
    }
}

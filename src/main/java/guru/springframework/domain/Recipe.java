package guru.springframework.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private Integer prepTime;
    private Integer cookTime;
    private Integer servings;
    private String source;
    private String url;

    @Lob
    private String directions;

    // Using String instead of Ordinal means that inserting new enum
    // value has no impact on stored values but strings take more space
    @Enumerated(value = EnumType.STRING)
    private Difficulty difficulty;

    @Lob
    private Byte[] image;

    // Recipe is 'owner' of the relationship with RecipeNotes
    // --> therefore it specifies the cascade property
    @OneToOne(cascade = CascadeType.ALL)
    private RecipeNotes notes;

    // Recipe is 'owner' of the relationship with Ingredient
    // --> therefore it specifies the cascade property
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recipe")
    private Set<Ingredient> ingredients = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "recipe_category",
        joinColumns = @JoinColumn(name = "recipe_id"),
        inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Category> categories = new HashSet<>();

    public void setNotes(RecipeNotes notes) {
        this.notes = notes;
        // Explicit setting of the bi-directional relationship
        this.notes.setRecipe(this);
    }

    public void setIngredients(Set<Ingredient> ingredients) {
        this.ingredients = ingredients;
        // Explicit setting of the bi-directional relationship
        this.ingredients.forEach(i -> i.setRecipe(this));
    }

}

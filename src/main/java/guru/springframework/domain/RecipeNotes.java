package guru.springframework.domain;

import lombok.*;

@Data
@EqualsAndHashCode(exclude = "recipe")
@ToString(exclude = "recipe")
public class RecipeNotes {

    private String id;
    private Recipe recipe;
    private String notes;

}

package guru.springframework.domain;

import lombok.*;

import java.util.Set;

@Data
@EqualsAndHashCode(exclude = "recipes")
@ToString(exclude = "recipes")
public class Category {

    private String id;
    private String name;
    private Set<Recipe> recipes;

}

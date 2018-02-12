package guru.springframework.domain;

import lombok.*;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(exclude = "recipe")
@ToString(exclude = "recipe")
@NoArgsConstructor
public class Ingredient {

    private String id;
    private String description;
    private BigDecimal amount;
    private UnitOfMeasure uom;

    private Recipe recipe;

    public Ingredient(String description, BigDecimal amount, UnitOfMeasure uom) {
        this.description = description;
        this.amount = amount;
        this.uom = uom;
    }

}

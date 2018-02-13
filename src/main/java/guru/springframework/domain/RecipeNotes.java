package guru.springframework.domain;

import lombok.*;
import org.springframework.data.annotation.Id;

@Data
public class RecipeNotes {

    @Id
    private String id;
    private String notes;

}

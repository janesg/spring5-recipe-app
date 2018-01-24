package guru.springframework.converters;

import guru.springframework.commands.RecipeNotesCommand;
import guru.springframework.domain.RecipeNotes;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class RecipeNotesCommandToRecipeNotes implements Converter<RecipeNotesCommand, RecipeNotes> {

    @Synchronized
    @Override
    public RecipeNotes convert(RecipeNotesCommand recipeNotesCommand) {

        if (recipeNotesCommand == null) {
            return null;
        }

        RecipeNotes notes = new RecipeNotes();
        notes.setId(recipeNotesCommand.getId());
        notes.setNotes(recipeNotesCommand.getNotes());

        return notes;
    }
}

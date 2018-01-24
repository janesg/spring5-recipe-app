package guru.springframework.converters;

import guru.springframework.commands.RecipeNotesCommand;
import guru.springframework.domain.RecipeNotes;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class RecipeNotesToRecipeNotesCommand implements Converter<RecipeNotes, RecipeNotesCommand> {

    @Synchronized
    @Override
    public RecipeNotesCommand convert(RecipeNotes recipeNotes) {

        if (recipeNotes == null) {
            return null;
        }

        RecipeNotesCommand notesCommand = new RecipeNotesCommand();
        notesCommand.setId(recipeNotes.getId());
        notesCommand.setNotes(recipeNotes.getNotes());

        return notesCommand;
    }
}

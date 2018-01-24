package guru.springframework.converters;

import guru.springframework.commands.RecipeNotesCommand;
import guru.springframework.domain.RecipeNotes;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RecipeNotesCommandToRecipeNotesTest {

    private static final Long ID_VALUE = new Long(1L);
    private static final String RECIPE_NOTES = "Notes";

    RecipeNotesCommandToRecipeNotes converter;

    @Before
    public void setUp() {

        converter = new RecipeNotesCommandToRecipeNotes();
    }

    @Test
    public void testNullParameter() {

        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() {

        assertNotNull(converter.convert(new RecipeNotesCommand()));
    }

    @Test
    public void convert() {
        // given
        RecipeNotesCommand notesCommand = new RecipeNotesCommand();
        notesCommand.setId(ID_VALUE);
        notesCommand.setNotes(RECIPE_NOTES);

        // when
        RecipeNotes notes = converter.convert(notesCommand);

        // then
        assertNotNull(notes);
        assertEquals(ID_VALUE, notes.getId());
        assertEquals(RECIPE_NOTES, notes.getNotes());
    }
}
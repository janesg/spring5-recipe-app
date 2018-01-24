package guru.springframework.converters;

import guru.springframework.commands.RecipeNotesCommand;
import guru.springframework.domain.RecipeNotes;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RecipeNotesToRecipeNotesCommandTest {

    private static final Long ID_VALUE = new Long(1L);
    private static final String RECIPE_NOTES = "Notes";

    RecipeNotesToRecipeNotesCommand converter;

    @Before
    public void setUp() {
        converter = new RecipeNotesToRecipeNotesCommand();
    }

    @Test
    public void testNull() {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() {
        assertNotNull(converter.convert(new RecipeNotes()));
    }

    @Test
    public void convert() {
        //given
        RecipeNotes notes = new RecipeNotes();
        notes.setId(ID_VALUE);
        notes.setNotes(RECIPE_NOTES);

        //when
        RecipeNotesCommand notesCommand = converter.convert(notes);

        //then
        assertEquals(ID_VALUE, notesCommand.getId());
        assertEquals(RECIPE_NOTES, notesCommand.getNotes());
    }
}
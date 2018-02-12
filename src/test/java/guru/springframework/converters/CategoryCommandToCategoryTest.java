package guru.springframework.converters;

import guru.springframework.commands.CategoryCommand;
import guru.springframework.domain.Category;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CategoryCommandToCategoryTest {

    private static final String ID_VALUE = "1";
    private static final String NAME = "name";
    CategoryCommandToCategory converter;

    @Before
    public void setUp() {
        converter = new CategoryCommandToCategory();
    }

    @Test
    public void testNullObject() {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() {
        assertNotNull(converter.convert(new CategoryCommand()));
    }

    @Test
    public void convert() {
        // given
        CategoryCommand categoryCommand = new CategoryCommand();
        categoryCommand.setId(ID_VALUE);
        categoryCommand.setName(NAME);

        // when
        Category category = converter.convert(categoryCommand);

        // then
        assertEquals(ID_VALUE, category.getId());
        assertEquals(NAME, category.getName());
    }
}
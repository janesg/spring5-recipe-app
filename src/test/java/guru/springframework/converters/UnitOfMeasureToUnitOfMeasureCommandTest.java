package guru.springframework.converters;

import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.domain.UnitOfMeasure;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UnitOfMeasureToUnitOfMeasureCommandTest {

    private static String ID = "1";
    private static String DESC = "description";

    UnitOfMeasureToUnitOfMeasureCommand converter;

    @Before
    public void setUp() {
        converter = new UnitOfMeasureToUnitOfMeasureCommand();
    }

    @Test
    public void nullParameter() {

        assertNull(converter.convert(null));
    }

    @Test
    public void emptyObjectParameter() {

        assertNotNull(converter.convert(new UnitOfMeasure()));
    }

    @Test
    public void convert() {
        // given
        UnitOfMeasure uom = new UnitOfMeasure();
        uom.setId(ID);
        uom.setDescription(DESC);

        // when
        UnitOfMeasureCommand uomc = converter.convert(uom);

        // then
        assertNotNull(uomc);
        assertEquals(ID, uomc.getId());
        assertEquals(DESC, uomc.getDescription());
    }
}
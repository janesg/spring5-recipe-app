package guru.springframework.converters;

import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.domain.UnitOfMeasure;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UnitOfMeasureCommandToUnitOfMeasureTest {

    private static Long ID = 1L;
    private static String DESC = "description";

    UnitOfMeasureCommandToUnitOfMeasure converter;

    @Before
    public void setUp() {
        converter = new UnitOfMeasureCommandToUnitOfMeasure();
    }

    @Test
    public void nullParameter() {

        assertNull(converter.convert(null));
    }

    @Test
    public void emptyObjectParameter() {

        assertNotNull(converter.convert(new UnitOfMeasureCommand()));
    }

    @Test
    public void convert() {
        // given
        UnitOfMeasureCommand uomc = new UnitOfMeasureCommand();
        uomc.setId(ID);
        uomc.setDescription(DESC);

        // when
        UnitOfMeasure uom = converter.convert(uomc);

        // then
        assertNotNull(uom);
        assertEquals(ID, uom.getId());
        assertEquals(DESC, uom.getDescription());
    }

}
package guru.springframework.services;

import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.converters.UnitOfMeasureToUnitOfMeasureCommand;
import guru.springframework.domain.UnitOfMeasure;
import guru.springframework.repositories.UnitOfMeasureRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UnitOfMeasureServiceImplTest {

    private UnitOfMeasureServiceImpl uomService;

    @Mock
    private UnitOfMeasureRepository uomRepository;

    @Mock
    private UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        uomService =
                new UnitOfMeasureServiceImpl(uomRepository, unitOfMeasureToUnitOfMeasureCommand);
    }

    @Test
    public void getAllUoms() {
        UnitOfMeasure uom = new UnitOfMeasure();
        Set<UnitOfMeasure> data = new HashSet<>();
        data.add(uom);

        UnitOfMeasureCommand uomCmd = new UnitOfMeasureCommand();

        when(uomRepository.findAll()).thenReturn(data);
        when(unitOfMeasureToUnitOfMeasureCommand.convert(uom)).thenReturn(uomCmd);

        Set<UnitOfMeasureCommand> uoms = uomService.getAllUoms();

        assertEquals(1, uoms.size());
        verify(uomRepository, times(1)).findAll();
    }
}
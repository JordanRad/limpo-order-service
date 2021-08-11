package limpo.orderservice.limpounit;

import limpo.orderservice.limpounit.dto.LimpoUnit;
import limpo.orderservice.limpounit.repository.LimpoUnitRepository;
import limpo.orderservice.limpounit.service.LimpoUnitService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LimpoUnitServiceTests {

    @Mock
    private LimpoUnitRepository repository;

    @Autowired
    @InjectMocks
    private LimpoUnitService service;
    private LimpoUnit unitOne;
    private LimpoUnit unitTwo;
    private List<LimpoUnit> units;

    @BeforeEach
    public void setUp() {
        units = new ArrayList<>();
        unitOne = new LimpoUnit(1,"Test1", "Description1");
        unitTwo = new LimpoUnit(2,"Test2", "Description2");
    }
    @AfterEach
    public void tearDown() {
        unitOne = unitTwo = null;
        units= null;
    }

    @Test
    void shouldCreateLimpoUnit() {
        when(repository.save(any())).thenReturn(unitOne);
        service.createLimpoUnit(unitOne);
        verify(repository,times(1)).save(any());
    }


    @Test
    void shouldGetSingleLimpoUnitByName() {
        when(repository.findByName(unitOne.getName())).thenReturn(Optional.of(unitOne));
        assertThat(service.getLimpoUnitByName("Test1")).isEqualTo(unitOne);
    }

    @Test
    void shouldGetSingleLimpoUnitById() {
        when(repository.findById(unitTwo.getId())).thenReturn(Optional.of(unitTwo));
        assertThat(service.getLimpoUnitById(2L)).isEqualTo(unitTwo);
    }

    @Test
    void shouldGetAllLimpoUnits() {
        repository.save(unitOne);
        repository.save(unitTwo);
        when(repository.findAll()).thenReturn(units);
        List<LimpoUnit> list =service.getAllLimpoUnits();
        assertEquals(list,units);
        verify(repository,times(1)).save(unitOne);
        verify(repository,times(1)).save(unitTwo);
        verify(repository,times(1)).findAll();
    }

}

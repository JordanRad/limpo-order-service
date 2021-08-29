package limpo.orderservice.limpounit;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import limpo.orderservice.limpounit.dto.LimpoUnit;
import limpo.orderservice.limpounit.repository.LimpoUnitRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("tests")
public class LimpoUnitControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private LimpoUnitRepository repository;

    @Autowired
    private ObjectMapper mapper;

    long limpoUnitId;

    private final String URL = "/api/v1/order-service/limpoUnits/";

    @BeforeEach
    public void setup() {
        LimpoUnit limpoUnitOne = new LimpoUnit();
        limpoUnitOne.setName("Test1");
        limpoUnitOne.setDescription("Description1");

        LimpoUnit s1 = repository.save(limpoUnitOne);
        limpoUnitId = s1.getId();

        LimpoUnit limpoUnitTwo = new LimpoUnit();
        limpoUnitTwo.setName("Test2");
        limpoUnitTwo.setDescription("Description2");

        repository.save(limpoUnitTwo);
    }


    @AfterEach
    public void delete() {
        repository.deleteAll();
    }

    public String toJSONString(LimpoUnit limpoUnit) throws JsonProcessingException {
        return mapper.writeValueAsString(limpoUnit);
    }

    @Test
    public void shouldReturnAllLimpoUnitsAndReturnStatus200() throws Exception {
        this.mockMvc.perform(get(URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)));

    }

    @Test
    public void shouldReturnALimpoUnitAndReturnStatus200() throws Exception {
        this.mockMvc.perform(get(URL + limpoUnitId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is("Test1")))
                .andExpect(jsonPath("$.description", is("Description1")));
    }

    @Test
    public void shouldNotReturnALimpoUnitAndReturnStatus404() throws Exception {
        this.mockMvc.perform(get(URL + 42242))
                .andDo(print())
                .andExpect(status().isNotFound());

    }

    @Test
    public void shouldCreateNewLimpoUnitAndReturnStatus200() throws Exception {
        LimpoUnit limpoUnit = new LimpoUnit();
        limpoUnit.setName("New LimpoUnit");
        limpoUnit.setDescription("New Description");

        this.mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJSONString(limpoUnit)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is(limpoUnit.getName())))
                .andExpect(jsonPath("$.description", is(limpoUnit.getDescription())));
    }

    @Test
    public void shouldNotCreateNewLimpoUnitAndReturnStatus409() throws Exception {
        LimpoUnit limpoUnit = new LimpoUnit();
        limpoUnit.setName("Test1");
        limpoUnit.setDescription("New Description");

        this.mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJSONString(limpoUnit)))
                .andDo(print())
                .andExpect(status().isConflict());
    }

    @Test
    public void shouldUpdateLimpoUnitAndReturnStatus200() throws Exception {
        LimpoUnit limpoUnit = new LimpoUnit();
        limpoUnit.setId(limpoUnitId);
        limpoUnit.setName("Updated LimpoUnit");
        limpoUnit.setDescription("Updated Description");

        this.mockMvc.perform(put(URL + limpoUnitId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJSONString(limpoUnit)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is(limpoUnit.getName())))
                .andExpect(jsonPath("$.description", is(limpoUnit.getDescription())));
    }

    @Test
    public void shouldNotUpdateLimpoUnitAndReturnStatus409() throws Exception {
        LimpoUnit limpoUnit = new LimpoUnit();
        limpoUnit.setId(limpoUnitId);
        limpoUnit.setName("Test2");
        limpoUnit.setDescription("Updated Description");

        this.mockMvc.perform(put(URL + limpoUnitId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJSONString(limpoUnit)))
                .andDo(print())
                .andExpect(status().isConflict());
    }

    @Test
    public void shouldNotUpdateLimpoUnitAndReturnStatus404() throws Exception {
        LimpoUnit limpoUnit = new LimpoUnit();
        limpoUnit.setId(limpoUnitId);
        limpoUnit.setName("Test2");
        limpoUnit.setDescription("Updated Description");

        this.mockMvc.perform(put(URL + 57929)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJSONString(limpoUnit)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldDeleteALimpoUnitAndReturnStatus200() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.delete(URL+limpoUnitId).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void shouldDeleteALimpoUnitAndReturnStatus404() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.delete(URL+2462382).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }
}

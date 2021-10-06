package limpo.orderservice.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import limpo.orderservice.client.dto.Client;
import limpo.orderservice.client.repository.ClientRepository;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("tests")
public class ClientControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ClientRepository repository;

    @Autowired
    private ObjectMapper mapper;

    long clientId;

    private final String URL = "/api/v1/clients/";

    @BeforeEach
    public void setup() {
        Client clientOne = new Client();
        clientOne.setBulstat(312418L);
        clientOne.setEmail("client@mail.com");
        clientOne.setAddress("Address");
        clientOne.setLastName("Client");
        clientOne.setFirstName("Client");
        clientOne.setPhone("0888888888");

        Client c1 = repository.save(clientOne);
        clientId = c1.getId();

        Client clientTwo = new Client();
        clientTwo.setBulstat(312415L);
        clientTwo.setEmail("client2@mail.com");
        clientTwo.setAddress("Address2");
        clientTwo.setLastName("Client2");
        clientTwo.setFirstName("Client2");
        clientTwo.setPhone("0888888887");

        repository.save(clientTwo);
    }


    @AfterEach
    public void delete() {
        repository.deleteAll();
    }

    public String toJSONString(Client client) throws JsonProcessingException {
        return mapper.writeValueAsString(client);
    }

    @Test
    public void shouldReturnAllClientsAndReturnStatus200() throws Exception {
        this.mockMvc.perform(get(URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void shouldReturnAClientAndReturnStatus200() throws Exception {
        this.mockMvc.perform(get(URL + clientId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.firstName", is("Client")))
                .andExpect(jsonPath("$.lastName", is("Client")))
                .andExpect(jsonPath("$.email", is("client@mail.com")));
    }

    @Test
    public void shouldNotReturnAClientAndReturnStatus404() throws Exception {
        this.mockMvc.perform(get(URL + 687334))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldNotCreateNewClientAndReturnStatus409() throws Exception {
        Client client = new Client();
        client.setBulstat(312418L);
        client.setEmail("client@mail.com");
        client.setAddress("Address");
        client.setLastName("Client");
        client.setFirstName("Client");
        client.setPhone("0888888888");

        this.mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJSONString(client)))
                .andDo(print())
                .andExpect(status().isConflict());
    }

    @Test
    public void shouldCreateNewClientAndReturnStatus200() throws Exception {
        Client client = new Client();
        client.setBulstat(312416L);
        client.setEmail("newclient@mail.com");
        client.setAddress("New Address");
        client.setLastName("NewClient");
        client.setFirstName("NewClient");
        client.setPhone("0777777777");

        this.mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJSONString(client)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.firstName", is(client.getFirstName())))
                .andExpect(jsonPath("$.email", is(client.getEmail())))
                .andExpect(jsonPath("$.lastName", is(client.getLastName())))
                .andExpect(jsonPath("$.address", is(client.getAddress())));
    }

    @Test
    public void shouldNotUpdateClientAndReturnStatus409ExistingEmail() throws Exception {
        Client client = new Client();
        client.setAddress("Updated Address");
        client.setLastName("UpdateClient");
        client.setFirstName("UpdateClient");
        client.setPhone("066666666");
        client.setBulstat(712416L);
        client.setEmail("client@mail.com");

        this.mockMvc.perform(put(URL + clientId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJSONString(client)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.firstName", is(client.getFirstName())))
                .andExpect(jsonPath("$.email", is(client.getEmail())))
                .andExpect(jsonPath("$.lastName", is(client.getLastName())))
                .andExpect(jsonPath("$.address", is(client.getAddress())));
    }

    @Test
    public void shouldNotUpdateClientAndReturnStatus409ExistingPhone() throws Exception {
        Client client = new Client();
        client.setAddress("Updated Address");
        client.setLastName("UpdateClient");
        client.setFirstName("UpdateClient");
        client.setPhone("0888888888");
        client.setBulstat(712416L);
        client.setEmail("hello@mail.com");

        this.mockMvc.perform(put(URL + clientId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJSONString(client)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.firstName", is(client.getFirstName())))
                .andExpect(jsonPath("$.email", is(client.getEmail())))
                .andExpect(jsonPath("$.lastName", is(client.getLastName())))
                .andExpect(jsonPath("$.address", is(client.getAddress())));
    }

    @Test
    public void shouldUpdateClientAndReturnStatus200() throws Exception {
        Client client = new Client();
        client.setAddress("Updated Address");
        client.setLastName("UpdateClient");
        client.setFirstName("UpdateClient");
        client.setPhone("066666666");
        client.setBulstat(712416L);
        client.setEmail("updatedlient@mail.com");

        this.mockMvc.perform(put(URL + clientId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJSONString(client)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.firstName", is(client.getFirstName())))
                .andExpect(jsonPath("$.email", is(client.getEmail())))
                .andExpect(jsonPath("$.lastName", is(client.getLastName())))
                .andExpect(jsonPath("$.address", is(client.getAddress())));
    }

    @Test
    public void shouldNotUpdateClientAndReturnStatus404() throws Exception {
        Client client = new Client();
        client.setAddress("Updated Address");
        client.setLastName("UpdateClient");
        client.setFirstName("UpdateClient");
        client.setPhone("066666666");
        client.setBulstat(712416L);
        client.setEmail("updatedlient@mail.com");

        this.mockMvc.perform(put(URL + 198986)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJSONString(client)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldDeleteClientAndReturnStatus200() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.delete(URL + clientId)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void shouldNotDeleteClientAndReturnStatus404() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.delete(URL + 8593427)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}

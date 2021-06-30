package limpo.orderservice.controller;

import limpo.orderservice.model.Client;
import limpo.orderservice.repository.ClientRepository;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

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

    long clientId;

    @BeforeEach
    public void setup(){
        Client clientOne = new Client();
        clientOne.setBulstat(312418L);
        clientOne.setEmail("client@mail.com");
        clientOne.setAddress("Address");
        clientOne.setLastName("Client");
        clientOne.setFirstName("Client");
        clientOne.setPhone("0888888888");

        Client c1 = repository.save(clientOne);
        clientId=c1.getId();

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
    public void delete(){
        repository.deleteAll();
    }

    public String toJSONString(Client client){
        Map<String, String> map = new HashMap<>();
        //map.put("id",Long.toString(client.getId()>0?client.getId():-1L));
        map.put("firstName", client.getFirstName());
        map.put("lastName", client.getLastName());
        map.put("email", client.getEmail());
        map.put("bulstat", Long.toString(client.getBulstat()));
        map.put("address", client.getAddress());
        map.put("phone", client.getPhone());

        JSONObject object = new JSONObject(map);
        return object.toJSONString();
    }

    @Test
    public void Should_Return_All_Clients_And_Status_200() throws Exception {
        this.mockMvc.perform(get("/api/clients/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void Should_Return_A_Client_And_Status_200() throws Exception {
        this.mockMvc.perform(get("/api/clients/"+clientId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.firstName", is("Client")))
                .andExpect(jsonPath("$.lastName", is("Client")))
                .andExpect(jsonPath("$.email", is("client@mail.com")));
    }

    @Test
    public void Should_Create_New_Client_And_Status_200() throws Exception {
        Client client = new Client();
        client.setBulstat(312416L);
        client.setEmail("newclient@mail.com");
        client.setAddress("New Address");
        client.setLastName("NewClient");
        client.setFirstName("NewClient");
        client.setPhone("0777777777");

        this.mockMvc.perform(post("/api/clients/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJSONString(client)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.firstName", is(client.getFirstName())))
                .andExpect(jsonPath("$.email", is(client.getEmail())))
                .andExpect(jsonPath("$.lastName",is(client.getLastName())))
                .andExpect(jsonPath("$.address",is(client.getAddress())));
    }

    @Test
    public void Should_Update_Client_And_Status_200() throws Exception {
        Client client = new Client();
        client.setAddress("Updated Address");
        client.setLastName("UpdateClient");
        client.setFirstName("UpdateClient");
        client.setPhone("066666666");
        client.setBulstat(712416L);
        client.setEmail("updatedlient@mail.com");

        this.mockMvc.perform(put("/api/clients/"+clientId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJSONString(client)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.firstName", is(client.getFirstName())))
                .andExpect(jsonPath("$.email", is(client.getEmail())))
                .andExpect(jsonPath("$.lastName",is(client.getLastName())))
                .andExpect(jsonPath("$.address",is(client.getAddress())));
    }
}

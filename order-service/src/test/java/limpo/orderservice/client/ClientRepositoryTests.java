package limpo.orderservice.client;

import limpo.orderservice.client.dto.Client;
import limpo.orderservice.client.repository.ClientRepository;
import limpo.orderservice.client.service.ClientService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import java.util.ArrayList;
import java.util.NoSuchElementException;

@DataJpaTest
@ActiveProfiles("tests")
public class ClientRepositoryTests {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ClientRepository repository;

    @MockBean
    private ClientService service;

    private Client clientOne;
    private Client clientTwo;

    @BeforeEach
    public void init(){
        clientOne = new Client();
        clientOne.setBulstat(312414L);
        clientOne.setEmail("moni@moni.moni");
        clientOne.setAddress("Toltoy");
        clientOne.setLastName("Manolov");
        clientOne.setFirstName("Moni");
        clientOne.setPhone("0886");

        clientTwo = new Client();
        clientTwo.setBulstat(33131L);
        clientTwo.setEmail("tino@tino.tino");
        clientTwo.setAddress("Lozenets");
        clientTwo.setLastName("Hadzhiyankov");
        clientTwo.setFirstName("Constantin");

        entityManager.persist(clientOne);
        entityManager.persist(clientTwo);
    }

    @Test
    public void shouldFindClientByEmail(){
        Client client = repository.findByEmail(clientOne.getEmail()).orElseThrow(NoSuchElementException::new);

        Assertions.assertEquals(client.getAddress(), clientOne.getAddress());
        Assertions.assertEquals(client.getBulstat(), clientOne.getBulstat());
        Assertions.assertEquals(client.getEmail(), clientOne.getEmail());
        Assertions.assertEquals(client.getFirstName(), clientOne.getFirstName());
        Assertions.assertEquals(client.getLastName(), clientOne.getLastName());
    }

    @Test
    public void shouldFindClientByEmailAndPhone(){
        Client client = repository.findByEmailAndPhone(clientOne.getEmail(),clientOne.getPhone()).orElseThrow(NoSuchElementException::new);

        Assertions.assertEquals(client.getAddress(), clientOne.getAddress());
        Assertions.assertEquals(client.getBulstat(), clientOne.getBulstat());
        Assertions.assertEquals(client.getEmail(), clientOne.getEmail());
        Assertions.assertEquals(client.getFirstName(), clientOne.getFirstName());
        Assertions.assertEquals(client.getLastName(), clientOne.getLastName());
        Assertions.assertEquals(client.getPhone(), clientOne.getPhone());
    }

    @Test
    public void shouldNotFindClientByEmail(){
        Client client = repository.findByEmail("wrongEmail").orElse(null);
        Assertions.assertNull(client);
    }

    @Test
    public void shouldNotGetUnknownClient(){
        Client client = service.getClientById(976L);
        Assertions.assertNull(client);
    }

    @Test
    public void shouldGetAllClients(){
        ArrayList<Client> clients = (ArrayList<Client>)repository.findAll();
        Assertions.assertEquals(clients.size(),2);
        Assertions.assertEquals(clientOne, clients.get(0));
        Assertions.assertEquals(clientTwo, clients.get(1));
    }

    @Test
    public void shouldCreateNewClient(){
        Client client = new Client();
        client.setFirstName("Yordan");
        client.setLastName("Radushev");
        client.setAddress("Lozenets");
        client.setEmail("dani.radushev@dani.dani");
        client.setBulstat(312124L);
        Client createdClient = repository.save(client);

        Assertions.assertEquals(client.getAddress(), createdClient.getAddress());
        Assertions.assertEquals(client.getBulstat(), createdClient.getBulstat());
        Assertions.assertEquals(client.getEmail(), createdClient.getEmail());
        Assertions.assertEquals(client.getFirstName(), createdClient.getFirstName());
        Assertions.assertEquals(client.getLastName(), createdClient.getLastName());
    }

    @Test
    public void shouldUpdateAClient(){
        Long idOne = (Long) entityManager.getId(clientOne);
        var client = repository.findById(idOne).orElse(null);

        assert client != null;
        client.setLastName("Update");
        var updatedClient = repository.save(client);
        Assertions.assertEquals(client.getLastName(),updatedClient.getLastName());

    }

    @Test
    public void shouldDeleteAClient(){
        Long idOne = (Long) entityManager.getId(clientOne);
        var client = repository.findById(idOne).get();

        repository.delete(clientOne);

        ArrayList<Client> result = (ArrayList<Client>)repository.findAll();

        Assertions.assertEquals(result.size(), 1);
        Assertions.assertEquals(result.get(0).getEmail(),clientTwo.getEmail());
    }

}

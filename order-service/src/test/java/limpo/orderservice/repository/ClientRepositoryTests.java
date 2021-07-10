package limpo.orderservice.repository;

import limpo.orderservice.model.Client;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import java.util.ArrayList;

@DataJpaTest
@ActiveProfiles("tests")
public class ClientRepositoryTests {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ClientRepository repository;

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
    public void Should_Get_A_Single_Client(){
        Long idOne = (Long) entityManager.getId(clientOne);
        Client client = repository.findById(idOne).get();

        Assertions.assertEquals(client.getAddress(), clientOne.getAddress());
        Assertions.assertEquals(client.getBulstat(), clientOne.getBulstat());
        Assertions.assertEquals(client.getEmail(), clientOne.getEmail());
        Assertions.assertEquals(client.getFirstName(), clientOne.getFirstName());
        Assertions.assertEquals(client.getLastName(), clientOne.getLastName());
    }

    @Test
    public void Should_Not_Get_Unknown_Client(){
        var client = repository.findById(222L);

        Assertions.assertFalse(client.isPresent());
    }

    @Test
    public void Should_Get_All_Products(){
        ArrayList<Client> clients = (ArrayList<Client>)repository.findAll();

        Assertions.assertEquals(clients.size(),2);
        Assertions.assertTrue(clients.get(0).equals(clientOne));
        Assertions.assertTrue(clients.get(1).equals(clientTwo));
    }

    @Test
    public void Should_Create_New_Product(){
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
    public void Should_Update_A_Product(){
        Long idOne = (Long) entityManager.getId(clientOne);
        var client = repository.findById(idOne).get();

        client.setLastName("Update");
        var updatedClient = repository.save(client);
        Assertions.assertEquals(client.getLastName(),updatedClient.getLastName());

    }

    @Test
    public void Should_Delete_A_Product(){
        Long idOne = (Long) entityManager.getId(clientOne);
        var client = repository.findById(idOne).get();

        repository.delete(clientOne);

        ArrayList<Client> result = (ArrayList<Client>)repository.findAll();

        Assertions.assertTrue(result.size() == 1);
        Assertions.assertEquals(result.get(0).getEmail(),clientTwo.getEmail());
    }

    @Test
    public void Should_Delete_All_Products(){
        repository.deleteAll();
        ArrayList<Client> result = (ArrayList<Client>)repository.findAll();

        Assertions.assertTrue(result.isEmpty());
    }
}

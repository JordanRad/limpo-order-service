package limpo.orderservice.client;

import limpo.orderservice.client.model.Client;
import limpo.orderservice.client.repository.ClientRepository;
import limpo.orderservice.client.service.ClientService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;


@DataJpaTest
public class ClientServiceTests {

    @Mock
    private ClientRepository repository;

    @InjectMocks
    private ClientService service;

    @Test
    public void shouldGetAClientById() {
        Client expectedClient = new Client();
        expectedClient.setEmail("client@client.com");
        expectedClient.setId(1L);

        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(expectedClient));

        Client result = service.getClientById(1L);

        Assertions.assertEquals(expectedClient, result);
    }

    @Test
    public void shouldGetAnEmptyClient() {
        Client expectedClient = new Client();
        expectedClient.setEmail("client@client.com");
        expectedClient.setId(1L);

        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(expectedClient));

        Client result = service.getClientById(2L);

        Assertions.assertNull(result);
    }

    @Test
    public void shouldGetAllClients() {
        Client expectedClient1 = new Client();
        expectedClient1.setEmail("client1@client.com");
        expectedClient1.setId(1L);

        Client expectedClient2 = new Client();
        expectedClient2.setEmail("client2@client.com");
        expectedClient2.setId(2L);

        ArrayList<Client> expectedClients = new ArrayList<>();
        expectedClients.add(expectedClient1);
        expectedClients.add(expectedClient2);

        Mockito.when(repository.findAll()).thenReturn(expectedClients);

        ArrayList<Client> result = service.getAllClients();

        Assertions.assertEquals(2, result.size());
    }

    @Test
    public void shouldCreateNewClient() {
        Client expectedClient = new Client();
        expectedClient.setEmail("client@client.com");

        Mockito.when(repository.save(expectedClient)).thenReturn(expectedClient);

        Client result = service.createClient(expectedClient);

        Assertions.assertEquals(expectedClient, result);
    }


}

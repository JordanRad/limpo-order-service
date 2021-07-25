package limpo.orderservice.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    public List<Client> getAllClients() {
        List<Client> result = new ArrayList<Client>();
        Iterable<Client> clients = clientRepository.findAll();
        clients.forEach(result::add);

        return result;
    }

    public Client getClientByEmail(String email) {
        Client client = clientRepository.findByEmail(email).orElse(null);
        return client;
    }

    public Client getClientById(Long id) {
        Client client = clientRepository.findById(id).orElse(null);
        return client;

    }

    public Client addNewClient(Client client) {
        Client result;
        try {
            result = clientRepository.save(client);
        } catch (Exception e) {
            return null;
        }

        return result;
    }

    public Long deleteClient(Long id) {
        Client client = clientRepository.findById(id).orElse(null);
        if (client != null) {
            clientRepository.delete(client);
            return id;
        } else {
            return null;
        }
    }

    public Client updateClient(Long id, Client client) {
        Client client1 = clientRepository.findById(id).orElse(null);
        if (client1 == null) {
            return null;
        }
        client1.setFirstName(client.getFirstName());
        client1.setLastName(client.getLastName());
        client1.setAddress(client.getAddress());
        client1.setEmail(client.getEmail());
        client1.setBulstat(client.getBulstat());
        client1.setType(client.getType());
        client1.setVATNumber(client.getVATNumber());

        try {
            clientRepository.save(client1);
        } catch (Exception e) {
            return new Client();
        }
        return client1;
    }
}
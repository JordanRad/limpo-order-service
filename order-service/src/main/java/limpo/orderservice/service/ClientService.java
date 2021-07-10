package limpo.orderservice.service;
import limpo.orderservice.model.Client;
import limpo.orderservice.repository.ClientRepository;
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

    public Client getClientById(Long id) {
        Client client = clientRepository.findById(id).orElse(null);
        return client;

    }

    public Client addNewClient(Client client) {
        return clientRepository.save(client);
    }

    public Long deleteClient(Long id) {
        Client client = clientRepository.findById(id).orElse(null);
        if(client != null){
            clientRepository.delete(client);
            return id;
        }
        else{
            return null;
        }
    }

    public Client updateClient(Long id, Client client) {
        Client client1 = clientRepository.findById(id).orElse(null);
        if(client1 == null){
            return null;
        }
        client1.setFirstName(client.getFirstName());
        client1.setLastName(client.getLastName());
        client1.setAddress(client.getAddress());
        client1.setEmail(client.getEmail());
        client1.setBulstat(client.getBulstat());
        return clientRepository.save(client1);
    }
}
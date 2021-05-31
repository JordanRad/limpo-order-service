package limpo.orderservice.service;
import limpo.orderservice.model.Client;
import limpo.orderservice.repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public List<Client> getAllClients() {
        List<Client> result = new ArrayList<Client>();
        Iterable<Client> clients = clientRepository.findAll();
        clients.forEach(result::add);

        return result;
    }

    @Override
    public Client getClientById(Long id) {
        Client client = clientRepository.findById(id).orElse(null);
        return client;

    }

    @Override
    public Client addNewClient(Client client) {
       return clientRepository.save(client);
    }

    @Override
    public Long deleteClient(Long id) {
        Client client = clientRepository.findById(id).get();
        if(client != null){
            clientRepository.delete(client);
            return id;
        }
        else{
            return null;
        }
    }

    @Override
    public Client updateClient(Long id, Client client) {
        Client client1 = clientRepository.findById(id).get();
        client1.setFirstName(client.getFirstName());
        client1.setFamilyName(client.getFamilyName());
        client1.setAddress(client.getAddress());
        client1.setEmail(client.getEmail());
        client1.setBulstat(client.getBulstat());
        return clientRepository.save(client1);
    }
}

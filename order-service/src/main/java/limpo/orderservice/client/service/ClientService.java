package limpo.orderservice.client.service;

import limpo.orderservice.client.dto.Client;
import limpo.orderservice.client.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    /**
     * Get all clients
     *
     * @return ArrayList of Client objects
     */
    public ArrayList<Client> getAllClients() {
        return (ArrayList<Client>) clientRepository.findAll();
    }

    /**
     * Get a client by email
     *
     * @return Client
     */
    public Client getClientByEmail(String email) {
        return clientRepository.findByEmail(email).orElse(null);
    }

    /**
     * Get a client by id
     *
     * @return Client
     */
    public Client getClientById(Long id) {
        return clientRepository.findById(id).orElse(null);
    }

    /**
     * Create new client
     *
     * @return Created client
     */
    public Client createClient(Client client) {
        Client result;

        try {
            result = clientRepository.save(client);
        } catch (Exception e) {
            return null;
        }

        return result;
    }

    /**
     * Update a client
     *
     * @return Updated client
     */
    public Client updateClient(Long id, Client client) {
        Client updatedClient = clientRepository.findById(id).orElse(null);
        if (updatedClient == null) {
            return null;
        }
        updatedClient.setFirstName(client.getFirstName());
        updatedClient.setLastName(client.getLastName());
        updatedClient.setAddress(client.getAddress());
        updatedClient.setEmail(client.getEmail());
        updatedClient.setBulstat(client.getBulstat());
        updatedClient.setType(client.getType());
        updatedClient.setVatNumber(client.getVatNumber());
        updatedClient.setPhone(client.getPhone());

        try {
            clientRepository.save(updatedClient);
        } catch (Exception e) {
            return new Client();
        }
        return updatedClient;
    }

    /**
     * Delete a client
     *
     * @return Deleted client
     */
    public Long deleteClient(Long id) {
        Client client = clientRepository.findById(id).orElse(null);

        if (client != null) {
            clientRepository.delete(client);
            return id;
        } else {
            return null;
        }
    }
}
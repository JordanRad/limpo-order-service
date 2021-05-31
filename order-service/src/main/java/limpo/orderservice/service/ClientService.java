package limpo.orderservice.service;

import limpo.orderservice.model.Client;
import limpo.orderservice.model.User;

import java.util.List;

public interface ClientService {
    public List<Client> getAllClients();
    public Client getClientById(Long id);
    public Client addNewClient(Client client);
    public Long deleteClient(Long id);
    public Client updateClient(Long id, Client client);

}

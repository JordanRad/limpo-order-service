package limpo.orderservice.controller;

import limpo.orderservice.model.Client;
import limpo.orderservice.service.ClientService;
import org.apache.juli.logging.Log;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ClientController.BASE_URL)
public class ClientController{

    public static final String BASE_URL = "/clients";

    public final ClientService clientService;
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    @ResponseBody
    public List<Client> getAllClients(){
        return clientService.getAllClients();
    }

    @GetMapping("/{id}")
    @ResponseBody
    public Client getClient(@PathVariable("id") Long id){
        return clientService.getClientById(id);
    }

    @PostMapping
    public  Client addNewClient(@RequestBody Client client){
        return clientService.addNewClient(client);
    }

    @DeleteMapping("/{id}")
    public Long deleteClient(@PathVariable Long id){
        return clientService.deleteClient(id);
    }

    @PostMapping("/{id}")
    public Client updateClient(@PathVariable Long id, @RequestBody Client client){
        return clientService.updateClient(id,client);
    }

}
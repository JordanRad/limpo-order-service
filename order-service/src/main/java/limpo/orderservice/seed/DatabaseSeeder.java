package limpo.orderservice.seed;

import limpo.orderservice.model.Client;
import limpo.orderservice.model.Order;
import limpo.orderservice.model.Product;
import limpo.orderservice.model.ProductItem;
import limpo.orderservice.repository.ClientRepository;
import limpo.orderservice.repository.OrderRepository;
import limpo.orderservice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    Product productOne = new Product();
    public void saveProducts(){
        productOne.setName("Product1");
        productOne.setDescription("Description1");
        productOne.setPrice(14.99);

        Product productTwo = new Product();
        productTwo.setName("Product2");
        productTwo.setDescription("Description2");
        productTwo.setPrice(22.22);


        productRepository.saveAll(List.of(productOne,productTwo));
        System.out.println("INFO 200200 --- [           main] Database seeder: Products added...");
    }

    Client clientOne = new Client();
    public void saveClients(){

        clientOne.setBulstat(312414L);
        clientOne.setEmail("moni@moni.moni");
        clientOne.setAddress("'s Gravesandestraat 66");
        clientOne.setLastName("Manolov");
        clientOne.setFirstName("Moni");

        Client clientTwo = new Client();
        clientTwo.setBulstat(33131L);
        clientTwo.setEmail("tino@tino.tino");
        clientTwo.setAddress("Johannes van der Waalsweg 64");
        clientTwo.setLastName("Hadzhiyankov");
        clientTwo.setFirstName("Constantin");

        clientRepository.saveAll(List.of(clientOne,clientTwo));
        System.out.println("INFO 200200 --- [           main] Database seeder: Clients added...");
    }

    public void saveOrder(){
        Order order = new Order();
        order.setClient(clientOne);
        order.setOrderNumber("11111111");

        ProductItem pi = new ProductItem();
        pi.setPrice(productOne.getPrice());
        pi.setProduct(productOne);
        pi.setQuantity(2);

        order.setProductItems(Set.of(pi));
        orderRepository.save(order);

        System.out.println("INFO 200200 --- [           main] Database seeder: Order added...");
    }
    @Override
    public void run(String... args) throws Exception {
        System.out.println("INFO 200200 --- [           main] Database seeder: Initializing data...");
        saveProducts();
        saveClients();
        saveOrder();
    }
}


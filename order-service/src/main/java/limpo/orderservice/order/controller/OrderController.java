package limpo.orderservice.order.controller;

import limpo.orderservice.client.dto.Client;
import limpo.orderservice.client.service.ClientService;
import limpo.orderservice.limpounit.service.LimpoUnitService;
import limpo.orderservice.order.dto.Order;
import limpo.orderservice.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(OrderController.BASE_URL)
public class OrderController {

    public static final String BASE_URL = "/api/v1/orders";

    @Autowired
    private OrderService orderService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private LimpoUnitService limpoUnitService;


    @GetMapping("/all")
    public ResponseEntity<?> getAllOrders(@RequestParam int startIndex) {
        ArrayList<Order> orders = orderService.getAllOrders("ALL",startIndex);

        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllOrdersByStatus(@RequestParam int startIndex,@RequestParam String status) {
        ArrayList<Order> orders = orderService.getAllOrders(status,startIndex);

        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/count")
    public ResponseEntity<?> getOrdersCount(@RequestParam String status) {
        int count = orderService.getOrdersCount(status);

        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    @GetMapping("/{orderNumber}")
    public ResponseEntity<?> getOrderByOrderNumber(@PathVariable String orderNumber) {
        Order order = orderService.getOrderByNumber(orderNumber);

        if (order == null) {
            return new ResponseEntity<>("Such order does not exist in the database.", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<?> getOrderBySearchInput(@RequestParam String searchInput, @RequestParam String status) {
        List<Order> orders = orderService.getOrdersBySearchInput(searchInput,status);

        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<?> createOrder(@RequestBody Order order) {
        // Check if client exists
        Client client = clientService.getClientByUniquesFields(order.getClient().getEmail(),order.getClient().getPhone());

        // If such client does not exist, we add new client
        if (client == null) {
            client = clientService.createClient(order.getClient());
        }

        // If the client cannot be created, return status CONFLICT
        if(client==null){
            return new ResponseEntity<>("Client with this email/phone already exists", HttpStatus.CONFLICT);
        }
        order.setClient(client);

        Order createdOrder = orderService.createOrder(order);

        if (createdOrder == null) {
            return new ResponseEntity<>("", HttpStatus.CONFLICT);
        }

        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
    }

    @PutMapping("/{orderNumber}")
    public ResponseEntity<Order> updateOrder(@PathVariable String orderNumber, @RequestParam String status) {
        Order updatedOrder = orderService.updateOrder(orderNumber, status);

        if (updatedOrder == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(updatedOrder, HttpStatus.OK);

    }

    @DeleteMapping("/{orderNumber}")
    public ResponseEntity<Order> deleteOrder(@PathVariable String orderNumber) {
        Order deletedOrder = orderService.deleteOrder(orderNumber);

        if (deletedOrder == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(deletedOrder, HttpStatus.OK);
    }
}

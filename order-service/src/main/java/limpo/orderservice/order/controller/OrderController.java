package limpo.orderservice.order.controller;

import limpo.orderservice.client.dto.Client;
import limpo.orderservice.client.service.ClientService;
import limpo.orderservice.limpounit.service.LimpoUnitService;
import limpo.orderservice.order.dto.Order;
import limpo.orderservice.order.dto.OrdersPage;
import limpo.orderservice.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

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
        ArrayList<Order> orders = orderService.getAllOrders("ALL", startIndex);

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

    @GetMapping("/")
    public ResponseEntity<?> getOrderBySearchInput(@RequestParam String searchInput, @RequestParam String status, @RequestParam int pageNumber, @RequestParam int pageSize) {
        Page<Order> orderPage = orderService.getOrdersBySearchInput(searchInput, status, pageNumber, pageSize);

        int total = orderService.getOrdersCount(status);

        int to = (pageNumber+1)*pageSize;

        OrdersPage page = new OrdersPage().toBuilder()
                .page(pageNumber + 1)
                .from((pageNumber * pageSize) + 1)
                .to(to)
                .orders(orderPage.getContent())
                .total(total)
                .build();

        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<?> createOrder(@RequestBody Order order) {
        // Check if client exists
        Client client = clientService.getClientByUniquesFields(order.getClient().getEmail(), order.getClient().getPhone());

        // If such client does not exist, we add new client
        if (client == null) {
            client = clientService.createClient(order.getClient());
        }

        // If the client cannot be created, return status CONFLICT
        if (client == null) {
            return new ResponseEntity<>("Client with this email/phone already exists", HttpStatus.CONFLICT);
        }
        order.setClient(client);

        // Set the timestamp
        Timestamp scheduledAt = new Timestamp(order.getTimestamp());
        order.setScheduledAt(scheduledAt);

        // Set scheduledAtString property
        String scheduledAtString = new SimpleDateFormat("dd-MM-yyyy HH:mm").format(scheduledAt);
        order.setScheduledAtString(scheduledAtString);

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

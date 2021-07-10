package limpo.orderservice.controller;

import limpo.orderservice.model.Order;
import limpo.orderservice.repository.OrderRepository;
import limpo.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping(OrderController.BASE_URL)
public class OrderController {
    public static final String BASE_URL = "/api/orders";

    @Autowired
    private OrderRepository repository;

    @Autowired
    private OrderService orderService;

    @GetMapping("/all")
    public ResponseEntity<ArrayList<Order>> getAllOrders() {
        ArrayList<Order> orders = orderService.getAllOrders();

        return new ResponseEntity(orders, HttpStatus.OK);

    }

    @GetMapping("/")
    public ResponseEntity<Order> getAllOrdersByStatus(@RequestParam String status) {
    ArrayList<Order> orders = orderService.getAllOrders(status);

        return new ResponseEntity(orders, HttpStatus.OK);
    }

    @GetMapping("/{orderNumber}")
    public ResponseEntity<Order> getOrderByOrderNumber(@PathVariable String orderNumber) {
        Order order = orderService.getOrderByNumber(orderNumber);

        if (order.equals(null)){
            return new ResponseEntity(null, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity(order, HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        Order createdOrder = orderService.createOrder(order);

        if(createdOrder.equals(null)){
            return new ResponseEntity(createdOrder, HttpStatus.CONFLICT);
        }

        return new ResponseEntity(createdOrder, HttpStatus.CREATED);
    }

    @PutMapping("/{orderNumber}")
    public ResponseEntity<Order> updateOrder(@RequestBody Order order) {
        Order updatedOrder = orderService.updateOrder(order);

        if(updatedOrder.equals(null)){
            return new ResponseEntity(null, HttpStatus.NOT_FOUND);
        }

        if (updatedOrder.getOrderNumber()==null) {

            return new ResponseEntity(null, HttpStatus.CONFLICT);
        }

        return new ResponseEntity(updatedOrder, HttpStatus.OK);

    }

    @DeleteMapping("/{orderNumber}")
    public ResponseEntity<Order> deleteOrder(@PathVariable String orderNumber) {
        Order deletedOrder = orderService.deleteOrder(orderNumber);

        if (deletedOrder.equals(null)){
            return new ResponseEntity(null, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity(deletedOrder, HttpStatus.OK);
    }
}

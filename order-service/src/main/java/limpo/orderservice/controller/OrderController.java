package limpo.orderservice.controller;

import limpo.orderservice.repository.OrderRepository;
import limpo.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(OrderController.BASE_URL)
public class OrderController {
    public static final String BASE_URL = "/api/orders";

    @Autowired
    private OrderRepository repository;

    @Autowired
    private OrderService orderService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllOrders() {

        // TODO: 01/07/2021
        return new ResponseEntity("", HttpStatus.OK);

    }

    @GetMapping("/")
    public ResponseEntity<?> getAllOrdersByStatus(@RequestParam String status) {

        // TODO: 01/07/2021
        return new ResponseEntity("", HttpStatus.OK);

    }

    @GetMapping("/{orderNumber}")
    public ResponseEntity<?> getOrderByOrderNumber() {

        // TODO: 01/07/2021
        return new ResponseEntity("", HttpStatus.OK);

    }

    @PostMapping("/")
    public ResponseEntity<?> createOrder() {

        // TODO: 01/07/2021
        return new ResponseEntity("", HttpStatus.OK);

    }

    @PutMapping("/{orderNumber}")
    public ResponseEntity<?> updateOrder() {

        // TODO: 01/07/2021
        return new ResponseEntity("", HttpStatus.OK);

    }

    @DeleteMapping("/{orderNumber}")
    public ResponseEntity<?> deleteOrder() {

        // TODO: 01/07/2021
        return new ResponseEntity("", HttpStatus.OK);

    }
}

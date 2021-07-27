package limpo.orderservice.order.controller;

import limpo.orderservice.client.model.Client;
import limpo.orderservice.client.service.ClientService;
import limpo.orderservice.order.model.Order;
import limpo.orderservice.order.model.ProductItem;
import limpo.orderservice.order.service.OrderService;
import limpo.orderservice.product.model.Product;
import limpo.orderservice.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping(OrderController.BASE_URL)
public class OrderController {

    public static final String BASE_URL = "/api/v1/order-service/orders";

    @Autowired
    private OrderService orderService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private ProductService productService;

    /**
     * The method creates an ProductItem objects out of Product object
     *
     * @param order An order with products
     * @return List of product items
     */
    private ArrayList<ProductItem> getItems(Order order) {
        // Enter product items
        ArrayList<ProductItem> items = new ArrayList<ProductItem>();
        for (ProductItem p : order.getProductItems()) {
            Product product = productService.getProductById(p.getProduct().getId());
            ProductItem item = new ProductItem();
            item.setProduct(product);
            item.setQuantity(p.getQuantity());
            item.setPrice(p.getPrice());
            items.add(item);
        }
        return items;
    }

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

    @PostMapping("/")
    public ResponseEntity<?> createOrder(@RequestBody Order order) {
        // Check if client exists
        Client client = clientService.getClientByEmail(order.getClient().getEmail());

        // If such client does not exist, we add new client
        if (client == null) {
            client = clientService.createClient(order.getClient());
        }

        // Check the product items in the database
        // The unknown ones will be added as null
        ArrayList<ProductItem> items = this.getItems(order);

        // Check if unknown item(s) is/are in the list
        ArrayList<Long> unknownItemIds = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getProduct() == null) {
                unknownItemIds.add(order.getProductItems().get(i).getProduct().getId());
            }
        }

        if (unknownItemIds.size() != 0) {
            // Return 404 - Not Found with the unknown ids
            return new ResponseEntity<>(unknownItemIds, HttpStatus.NOT_FOUND);
        }

        order.setClient(client);
        order.setProductItems(items);

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

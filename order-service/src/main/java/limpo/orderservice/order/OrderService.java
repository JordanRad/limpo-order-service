package limpo.orderservice.order;

import limpo.orderservice.client.Client;
import limpo.orderservice.client.ClientService;
import limpo.orderservice.product.Product;
import limpo.orderservice.product.ProductService;
import limpo.orderservice.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ClientService clientService;

    @Autowired
    private ProductService productService;

    private Status getStatus(String statusFilter) {
        Status status;
        switch (statusFilter) {
            case "PENDING":
                status = Status.PENDING;
                break;
            case "APPROVED":
                status = Status.APPROVED;
                break;
            case "COMPLETED":
                status = Status.COMPLETED;
            default:
                status = Status.NEW;
        }
        return status;
    }

    private ArrayList<ProductItem> getItems(Order order) {
        //Enter product items
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

    /**
     * Get all orders
     */
    public ArrayList<Order> getAllOrders() {
        return (ArrayList<Order>) orderRepository.findAll();
    }

    /**
     * Get all orders based on a status filter - example (statusFilter = "PENDING")
     *
     * @param statusFilter status criteria
     */
    public ArrayList<Order> getAllOrders(String statusFilter) {

        Status status = getStatus(statusFilter);

        return (ArrayList<Order>) orderRepository.findAllOrdersByStatus(status);
    }


    public Order getOrderByNumber(String orderNumber) {
        return orderRepository.findByOrderNumber(orderNumber).orElse(null);
    }

    public Order createOrder(Order order) {
        String createdAt = new SimpleDateFormat("dd.MM.yyyy/HH:mm").format(new Date());

        order.setStatus(Status.NEW);
        order.setCreatedAt(createdAt);

        // Check if client exists
        Client client = clientService.getClientByEmail(order.getClient().getEmail());

        // If such client does not exist, we add new client
        if (client == null) {
            client = clientService.addNewClient(order.getClient());
        }

        // Extract items
        ArrayList<ProductItem> items = getItems(order);

        order.setClient(client);
        order.setProductItems(items);

        Order createdOrder =  orderRepository.save(order);
        createdOrder.setOrderNumber(new SimpleDateFormat("dd.MM.yyyy/HH:mm").format(new Date())+"L"+createdOrder.getId());
        return orderRepository.save(createdOrder);
    }


    public Order updateOrder(String orderNumber, String status) {
        Order order = orderRepository.findByOrderNumber(orderNumber).orElse(null);

        if (order != null) {
            Status updatedStatus = getStatus(status);
            order.setStatus(updatedStatus);
        }

        return new Order();
    }

    public Order deleteOrder(String orderNumber) {
        Order orderToDelete = orderRepository.findByOrderNumber(orderNumber).orElse(null);

        if (orderToDelete != null) {
            orderRepository.deleteById(orderToDelete.getId());
        }
        return orderToDelete;
    }
}

package limpo.orderservice.service;

import limpo.orderservice.model.Order;
import limpo.orderservice.model.Product;
import limpo.orderservice.model.ProductItem;
import limpo.orderservice.model.Status;
import limpo.orderservice.repository.OrderRepository;
import limpo.orderservice.repository.ProductRepository;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    /**
     * Get all orders
     */
    public ArrayList<Order> getAllOrders() {
        ArrayList<Order> orders = (ArrayList<Order>) orderRepository.findAll();
        return orders;
    }

    /**
     * Get all orders based on a status filter - example (statusFilter = "PENDING")
     *
     * @param statusFilter
     */
    public ArrayList<Order> getAllOrders(String statusFilter) {
        Status status = Status.NEW;

        switch (statusFilter) {
            case "PENDING":
                status = Status.PENDING;
                break;
            case "APPROVED":
                status = Status.APPROVED;
                break;
            case "COMPLETED":
                status = Status.COMPLETED;


        }

        ArrayList<Order> orders = (ArrayList<Order>) orderRepository.findAllOrdersByStatus(status);

        return orders;
    }


    public Order getOrderByNumber(String orderNumber) {
        Order order = new Order();

        order = orderRepository.findByOrderNumber(orderNumber).get();

        return order;
    }

    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }


    public Order updateOrder(Order updatedOrder) {
        Order order = orderRepository.findByOrderNumber(updatedOrder.getOrderNumber()).get();

        order.setStatus(updatedOrder.getStatus());
        order.setClient(updatedOrder.getClient());
        order.setProductItems(updatedOrder.getProductItems());

        return new Order();
    }

    public Order deleteOrder(String orderNumber) {
        Order deletedOrder = orderRepository.deleteByOrderNumber(orderNumber).get();
        return deletedOrder;
    }
}

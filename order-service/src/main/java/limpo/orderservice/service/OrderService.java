package limpo.orderservice.service;

import limpo.orderservice.model.Order;
import limpo.orderservice.model.Product;
import limpo.orderservice.repository.OrderRepository;
import limpo.orderservice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public ArrayList<Order> getAllNewOrders(){
        // TODO: 01/07/2021
        return new ArrayList<>();
    }

    public ArrayList<Order> getAllPendingOrders(){
        // TODO: 01/07/2021
        return new ArrayList<>();
    }

    public ArrayList<Order> getAllApprovedOrders(){
        // TODO: 01/07/2021
        return new ArrayList<>();
    }

    public ArrayList<Order> getAllCompletedOrders(){
        // TODO: 01/07/2021
        return new ArrayList<>();
    }
    public Order getOrderByNumber(){
        // TODO: 01/07/2021
        return new Order();
    }

    public Order createOrder(){
        // TODO: 01/07/2021
        return new Order();
    }

    public Order updateOrder(){
        // TODO: 01/07/2021
        return new Order();
    }

    public Order deleteOrder(){
        // TODO: 01/07/2021
        return new Order();
    }
}

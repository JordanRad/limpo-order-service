package limpo.orderservice.controller;

import limpo.orderservice.repository.ClientRepository;
import limpo.orderservice.repository.OrderRepository;
import limpo.orderservice.service.ClientService;
import limpo.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OrderController.BASE_URL)
public class OrderController {
    public static final String BASE_URL = "/api/orders";

    @Autowired
    private OrderRepository repository;

    @Autowired
    private OrderService orderService;
}

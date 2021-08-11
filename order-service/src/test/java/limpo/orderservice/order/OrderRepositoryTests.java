package limpo.orderservice.order;

import limpo.orderservice.client.dto.Client;
import limpo.orderservice.order.dto.Order;
import limpo.orderservice.order.dto.OrderItem;
import limpo.orderservice.order.dto.Status;
import limpo.orderservice.order.repository.OrderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

@DataJpaTest
@ActiveProfiles("tests")
public class OrderRepositoryTests {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private OrderRepository repository;

    private Order orderOne;

    @BeforeEach
    public void init() {
        OrderItem orderItemOne = new OrderItem();
        orderItemOne.setName("OrderItem1");
        orderItemOne.setDescription("Description1");
        orderItemOne.setPrice(14.99);
        orderItemOne.setQuantity(1);


        Client clientOne = new Client();
        clientOne.setBulstat(312414L);
        clientOne.setEmail("client@client.com");
        clientOne.setAddress("Client's Address");
        clientOne.setLastName("Client");
        clientOne.setFirstName("Client");



        orderOne = new Order();
        orderOne.setClient(clientOne);
        orderOne.setOrderNumber("11111111");
        ArrayList<OrderItem> items = new ArrayList<OrderItem>();
        items.add(orderItemOne);
        orderOne.setOrderItems(items);
        entityManager.persist(new Order());
        entityManager.persist(new Order());
        entityManager.persist(orderOne);
        entityManager.persist(clientOne);
        entityManager.persist(orderOne);
        entityManager.persist(orderItemOne);
    }

    @Test
    public void shouldGetASingleOrder() {
        Long idOne = (Long) entityManager.getId(orderOne);
        Order order = repository.findById(idOne).get();

        Assertions.assertEquals(order.getClient().getEmail(),orderOne.getClient().getEmail());
        Assertions.assertEquals(order.getOrderNumber(),orderOne.getOrderNumber());
        Assertions.assertEquals(order.getOrderItems().size(), 1);
        Assertions.assertEquals(order.getOrderItems().get(0),orderOne.getOrderItems().get(0));
    }

    @Test
    public void shouldNotGetUnknownOrder() {
        var order = repository.findById(222L);

        Assertions.assertFalse(order.isPresent());
    }

    @Test
    public void shouldGetAllOrders() {
        ArrayList<Order> orders = (ArrayList<Order>)repository.findAll();

        Assertions.assertEquals(orders.size(),3);
        Assertions.assertEquals(orderOne, orders.get(2));
    }

    @Test
    public void shouldGetAllOrdersCount(){
        int count = repository.findAllOrdersCount();
        Assertions.assertEquals(3,count);
    }

    @Test
    public void shouldCreateNewOrder() {
        OrderItem orderItem = new OrderItem();
        orderItem.setName("OrderItem2");
        orderItem.setDescription("Description2");
        orderItem.setPrice(5.99);


        Client client = new Client();
        client.setBulstat(612414L);
        client.setEmail("client2@client.com");
        client.setAddress("Client2's Address");
        client.setLastName("Client2");
        client.setFirstName("Client2");


        Order order= new Order();
        order.setOrderItems(List.of(orderItem));
        order.setClient(client);
        order.setOrderNumber("123254L1");


        Order createdOrder = repository.save(order);

        Assertions.assertEquals(createdOrder.getClient().getEmail(),client.getEmail());
        Assertions.assertEquals(createdOrder.getOrderNumber(),order.getOrderNumber());
        Assertions.assertEquals(createdOrder.getOrderItems().size(), 1);
        Assertions.assertEquals(createdOrder.getOrderItems().get(0),order.getOrderItems().get(0));

    }

    @Test
    public void shouldUpdateAnOrder() {
        Long idOne = (Long) entityManager.getId(orderOne);
        var order = repository.findById(idOne).get();

        order.setStatus(Status.PENDING);
        var updatedOrder = repository.save(order);

        Assertions.assertEquals(updatedOrder.getStatus(),Status.PENDING);
    }

    @Test
    public void shouldDeleteAnOrder() {
        Long idOne = (Long) entityManager.getId(orderOne);
        var order = repository.findById(idOne).get();

        repository.delete(order);

        ArrayList<Order> result = (ArrayList<Order>)repository.findAll();

        Assertions.assertEquals(orderOne.getOrderNumber(), order.getOrderNumber());
    }

    @Test
    public  void shouldGetAnOrderByOrderNumber(){
        var order = repository.findByOrderNumber(orderOne.getOrderNumber()).get();

        Assertions.assertEquals(order.getClient(), orderOne.getClient());
    }

}

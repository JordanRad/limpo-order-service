package limpo.orderservice.order;

import limpo.orderservice.client.model.Client;
import limpo.orderservice.order.model.Order;
import limpo.orderservice.order.model.ProductItem;
import limpo.orderservice.order.model.Status;
import limpo.orderservice.order.repository.OrderRepository;
import limpo.orderservice.product.model.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;

@DataJpaTest
@ActiveProfiles("tests")
public class OrderRepositoryTests {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private OrderRepository repository;

    private Order orderOne;

    private Client clientOne;

    private Product productOne;

    private ProductItem productItemOne;

    @BeforeEach
    public void init() {
        productOne = new Product();
        productOne.setName("Product1");
        productOne.setDescription("Description1");
        productOne.setPrice(14.99);


        clientOne = new Client();
        clientOne.setBulstat(312414L);
        clientOne.setEmail("client@client.com");
        clientOne.setAddress("Client's Address");
        clientOne.setLastName("Client");
        clientOne.setFirstName("Client");


        productItemOne = new ProductItem();
        productItemOne.setPrice(productOne.getPrice());
        productItemOne.setProduct(productOne);
        productItemOne.setQuantity(2);

        orderOne = new Order();
        orderOne.setClient(clientOne);
        orderOne.setOrderNumber("11111111");
        ArrayList<ProductItem> items = new ArrayList<ProductItem>();
        items.add(productItemOne);
        orderOne.setProductItems(items);
        entityManager.persist(new Order());
        entityManager.persist(new Order());
        entityManager.persist(orderOne);
        entityManager.persist(clientOne);
        entityManager.persist(productOne);
        entityManager.persist(productItemOne);
    }

    @Test
    public void shouldGetASingleOrder() {
        Long idOne = (Long) entityManager.getId(orderOne);
        Order order = repository.findById(idOne).get();

        Assertions.assertEquals(order.getClient().getEmail(),orderOne.getClient().getEmail());
        Assertions.assertEquals(order.getOrderNumber(),orderOne.getOrderNumber());
        Assertions.assertEquals(order.getProductItems().size(), 1);
        Assertions.assertEquals(order.getProductItems().get(0),orderOne.getProductItems().get(0));
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
        Product product = new Product();
        product.setName("Product2");
        product.setDescription("Description2");
        product.setPrice(5.99);


        Client client = new Client();
        client.setBulstat(612414L);
        client.setEmail("client2@client.com");
        client.setAddress("Client2's Address");
        client.setLastName("Client2");
        client.setFirstName("Client2");


        ProductItem productItem = new ProductItem();
        productItem.setPrice(product.getPrice());
        productItem.setProduct(product);
        productItem.setQuantity(2);


        Order order = new Order();
        order.setClient(client);
        order.setOrderNumber("11111111");
        order.setStatus(Status.NEW);
        ArrayList<ProductItem> items = new ArrayList();
        items.add(productItemOne);
        items.add(productItem);
        order.setProductItems(items);

        Order createdOrder = repository.save(order);

        Assertions.assertEquals(createdOrder.getClient().getEmail(),client.getEmail());
        Assertions.assertEquals(createdOrder.getOrderNumber(),order.getOrderNumber());
        Assertions.assertEquals(createdOrder.getProductItems().size(), 2);
        Assertions.assertEquals(createdOrder.getProductItems().get(0),order.getProductItems().get(0));
        Assertions.assertEquals(createdOrder.getProductItems().get(1),order.getProductItems().get(1));

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

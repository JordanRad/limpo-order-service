package limpo.orderservice.order;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import limpo.orderservice.client.dto.Client;
import limpo.orderservice.client.repository.ClientRepository;
import limpo.orderservice.order.dto.Order;
import limpo.orderservice.order.dto.OrderItem;
import limpo.orderservice.order.dto.Status;
import limpo.orderservice.order.repository.OrderRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("tests")
public class OrderControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ObjectMapper mapper;

    long orderId;

    Order orderOne;

    List<OrderItem> orderItems;

    Client client;

    private final String URL = "/api/v1/order-service/orders/";

    @BeforeEach
    public void setup() {
        Client clientOne = new Client();
        clientOne.setBulstat(312418L);
        clientOne.setEmail("client@mail.com");
        clientOne.setAddress("Address");
        clientOne.setLastName("Client");
        clientOne.setFirstName("Client");
        clientOne.setPhone("0888888888");

        clientRepository.save(clientOne);

        Client clientTwo = new Client();
        clientTwo.setBulstat(2212L);
        clientTwo.setEmail("client2@mail.com");
        clientTwo.setAddress("Address2");
        clientTwo.setLastName("Client2");
        clientTwo.setFirstName("Client2");
        clientTwo.setPhone("0888888899");

        client = clientRepository.save(clientTwo);

        OrderItem limpoUnitOne = new OrderItem();
        limpoUnitOne.setName("LimpoUnit1");
        limpoUnitOne.setDescription("Description1");
        limpoUnitOne.setQuantity(1);
        limpoUnitOne.setPrice(9.99);
        OrderItem limpoUnitTwo = new OrderItem();
        limpoUnitTwo.setName("LimpoUnit2");
        limpoUnitTwo.setDescription("Description2");
        limpoUnitTwo.setQuantity(3);
        limpoUnitTwo.setPrice(19.99);
        orderItems = List.of(limpoUnitOne, limpoUnitTwo);


        Order order = new Order();
        order.setOrderNumber("1L2021");
        order.setClient(clientOne);
        order.setOrderItems(orderItems);
        order.setCreatedAt("23.07.2021T17:28");
        order.setStatus(Status.NEW);
        orderOne = orderRepository.save(order);
    }


    @AfterEach
    public void delete() {
        orderRepository.deleteAll();
        clientRepository.deleteAll();
    }

    public String toJSONString(Order order) throws JsonProcessingException {
        return mapper.writeValueAsString(order);
    }

    @Test
    public void shouldReturnAllOrdersAndReturnStatus200() throws Exception {
        this.mockMvc.perform(get(URL + "all?startIndex=0"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)));

    }

    @Test
    public void shouldReturnAllNewOrdersAndReturnStatus200() throws Exception {
        this.mockMvc.perform(get(URL + "?startIndex=0&status=NEW"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)));

    }

    @Test
    public void shouldReturnSingleOrderAndReturnStatus200() throws Exception {
        this.mockMvc.perform(get(URL + orderOne.getOrderNumber()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.client.email", is(orderOne.getClient().getEmail())))
                .andExpect(jsonPath("$.orderItems", hasSize(2)))
                .andExpect(jsonPath("$.status",is("NEW")))
                .andExpect(jsonPath("$.orderItems[0].name",is(orderOne.getOrderItems().get(0).getName())))
                .andExpect(jsonPath("$.createdAt", is(orderOne.getCreatedAt())));

    }

    @Test
    public void shouldNotReturnSingleOrderAndReturnStatus404() throws Exception {
        this.mockMvc.perform(get(URL + 57972))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldCreateNewOrderAndReturnStatus200() throws Exception {

        OrderItem limpoUnitOne = new OrderItem();
        limpoUnitOne.setName("LimpoUnit1");
        limpoUnitOne.setDescription("Description1");
        limpoUnitOne.setQuantity(1);
        limpoUnitOne.setPrice(9.99);

        Order order = new Order();
        order.setStatus(Status.NEW);
        order.setOrderItems(List.of(limpoUnitOne));
        order.setClient(client);
        this.mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJSONString(order)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.client.email", is(order.getClient().getEmail())))
                .andExpect(jsonPath("$.orderItems", hasSize(1)))
                .andExpect(jsonPath("$.orderItems[0].name",is(order.getOrderItems().get(0).getName())));
    }

    @Test
    public void shouldUpdateOrderAndReturnStatus200OrderStatusPending() throws Exception {

        this.mockMvc.perform(put(URL+orderOne.getOrderNumber()+"?status=PENDING")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.client.email", is(orderOne.getClient().getEmail())))
                .andExpect(jsonPath("$.orderItems", hasSize(2)))
                .andExpect(jsonPath("$.status",is("PENDING")))
                .andExpect(jsonPath("$.orderItems[0].name",is(orderOne.getOrderItems().get(0).getName())))
                .andExpect(jsonPath("$.createdAt", is(orderOne.getCreatedAt())));
    }

    @Test
    public void shouldUpdateOrderAndReturnStatus200OrderStatusApproved() throws Exception {

        this.mockMvc.perform(put(URL+orderOne.getOrderNumber()+"?status=APPROVED")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.client.email", is(orderOne.getClient().getEmail())))
                .andExpect(jsonPath("$.orderItems", hasSize(2)))
                .andExpect(jsonPath("$.status",is("APPROVED")))
                .andExpect(jsonPath("$.orderItems[0].name",is(orderOne.getOrderItems().get(0).getName())))
                .andExpect(jsonPath("$.createdAt", is(orderOne.getCreatedAt())));
    }

    @Test
    public void shouldUpdateOrderAndReturnStatus200OrderStatusCompleted() throws Exception {

        this.mockMvc.perform(put(URL+orderOne.getOrderNumber()+"?status=COMPLETED")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.client.email", is(orderOne.getClient().getEmail())))
                .andExpect(jsonPath("$.orderItems", hasSize(2)))
                .andExpect(jsonPath("$.status",is("COMPLETED")))
                .andExpect(jsonPath("$.orderItems[0].name",is(orderOne.getOrderItems().get(0).getName())))
                .andExpect(jsonPath("$.createdAt", is(orderOne.getCreatedAt())));
    }

    @Test
    public void shouldNotUpdateOrderAndReturnStatus404() throws Exception {

        this.mockMvc.perform(put(URL+75389475+"?status=APPROVED")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldDeleteOrderAndReturnStatus200() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.delete(URL+orderOne.getOrderNumber())
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        this.mockMvc.perform(get(URL + "all?startIndex=0"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void shouldNotDeleteOrderAndReturnStatus404() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.delete(URL+363656)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());

        this.mockMvc.perform(get(URL + "all?startIndex=0"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)));
    }
}

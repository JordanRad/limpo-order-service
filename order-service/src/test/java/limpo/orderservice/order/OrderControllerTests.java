package limpo.orderservice.order;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import limpo.orderservice.client.dto.Client;
import limpo.orderservice.client.repository.ClientRepository;
import limpo.orderservice.limpounit.dto.LimpoUnit;
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

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
        orderItems = List.of(limpoUnitOne,limpoUnitTwo);


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

    public String toJSONString(LimpoUnit limpoUnit) throws JsonProcessingException {
        return mapper.writeValueAsString(limpoUnit);
    }

    @Test
    public void shouldReturnAllOrdersAndReturnStatus200() throws Exception {
        this.mockMvc.perform(get(URL+"all?startIndex=0"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)));

    }
}

package limpo.orderservice;

import limpo.orderservice.client.controller.ClientController;
import limpo.orderservice.limpounit.controller.LimpoUnitController;
import limpo.orderservice.order.controller.OrderController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("tests")
class OrderServiceApplicationTests {

	@Autowired
	private ClientController clientController;

	@Autowired
	private OrderController orderController;

	@Autowired
	private LimpoUnitController limpoUnitController;

	@Test
	void contextLoads() {
//		Assertions.assertTrue(clientController !=null);
//		Assertions.assertTrue(orderController !=null);
//		Assertions.assertTrue(productController !=null);
	}

}

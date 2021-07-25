package limpo.orderservice;

import limpo.orderservice.client.ClientController;
import limpo.orderservice.order.OrderController;
import limpo.orderservice.product.ProductController;
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
	private ProductController productController;

	@Test
	void contextLoads() {
//		Assertions.assertTrue(clientController !=null);
//		Assertions.assertTrue(orderController !=null);
//		Assertions.assertTrue(productController !=null);
	}

}

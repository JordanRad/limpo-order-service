package limpo.orderservice;

import limpo.orderservice.controller.ClientController;
import limpo.orderservice.controller.OrderController;
import limpo.orderservice.controller.ProductController;
import limpo.orderservice.repository.OrderRepository;
import limpo.orderservice.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
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

package limpo.orderservice.repository;

import limpo.orderservice.model.Product;
import org.hibernate.exception.ConstraintViolationException;
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
public class ProductRepositoryTests {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ProductRepository repository;

    private Product productOne;
    private Product productTwo;

    @BeforeEach
    public void init() {
        productOne = new Product();
        productOne.setName("Product1");
        productOne.setDescription("Description1");
        productOne.setPrice(14.99);

        productTwo = new Product();
        productTwo.setName("Product2");
        productTwo.setDescription("Description2");
        productTwo.setPrice(22.22);

        entityManager.persist(productOne);
        entityManager.persist(productTwo);
    }
    @Test
    public void Should_Get_A_Single_Product() {
        Long idOne = (Long) entityManager.getId(productOne);
        Product product = repository.findById(idOne).get();
        Assertions.assertEquals(product.getName(),productOne.getName());
        Assertions.assertEquals(product.getDescription(),productOne.getDescription());
        Assertions.assertEquals(product.getPrice(),product.getPrice());
    }

    @Test
    public void Should_Not_Get_Unknown_Product() {
        var product = repository.findById(222L);
        Assertions.assertFalse(product.isPresent());

    }

    @Test
    public void Should_Get_All_Products() {
        ArrayList<Product> products = (ArrayList)repository.findAll();
        Assertions.assertEquals(products.size(),2);
        Assertions.assertTrue(products.get(0).equals(productOne));
        Assertions.assertTrue(products.get(1).equals(productTwo));
    }

    @Test
    public void Should_Create_New_Product() {
        Product product = new Product();
        product.setName("Product3");
        product.setDescription("Description3");
        product.setPrice(33.33);
        Product createdProduct = repository.save(product);
        Assertions.assertEquals(product.getName(),createdProduct.getName());
        Assertions.assertEquals(product.getDescription(),createdProduct.getDescription());
        Assertions.assertEquals(product.getPrice(),createdProduct.getPrice());
    }




    @Test()
    public void Should_Not_Create_Existing_Product()  {
        Product product = new Product();
        product.setName("Product1");
        product.setDescription("Description3");
        product.setPrice(33.33);

        repository.save(product);



    }

    @Test
    public void Should_Update_A_Product() {
        Long idOne = (Long) entityManager.getId(productOne);
        var product = repository.findById(idOne).get();


        product.setName("Updated");
        var updatedProduct = repository.save(product);
        Assertions.assertEquals(product.getName(),updatedProduct.getName());

    }


    @Test
    public void Should_Delete_A_Product() {
        Long idOne = (Long) entityManager.getId(productOne);
        var product = repository.findById(idOne).get();

        repository.delete(productOne);

        ArrayList<Product> result = (ArrayList<Product>)repository.findAll();
        Assertions.assertTrue(result.size()==1);
        Assertions.assertEquals(result.get(0).getName(),productTwo.getName());
        Assertions.assertEquals(result.get(0).getDescription(),productTwo.getDescription());
        Assertions.assertEquals(result.get(0).getPrice(),productTwo.getPrice());
    }

    @Test
    public void Should_Delete_All_Products() {

        repository.deleteAll();
        ArrayList<Product> result = (ArrayList<Product>)repository.findAll();
        Assertions.assertTrue(result.isEmpty());
    }



}

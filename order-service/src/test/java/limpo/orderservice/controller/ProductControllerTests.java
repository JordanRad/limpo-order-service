package limpo.orderservice.controller;


import limpo.orderservice.model.Product;
import limpo.orderservice.repository.ProductRepository;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("tests")
public class ProductControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository repository;

    long productId;

    @BeforeEach
    public void setup(){
        Product productOne = new Product();
        productOne.setName("Test1");
        productOne.setDescription("Description1");
        productOne.setPrice(14.99);

        Product s1 = repository.save(productOne);
        productId=s1.getId();

        Product productTwo = new Product();
        productTwo.setName("Test2");
        productTwo.setDescription("Description2");
        productTwo.setPrice(10.99);

        repository.save(productTwo);
    }


    @AfterEach
    public void delete(){
        repository.deleteAll();
    }

    public String toJSONString(Product product){
        Map<String, String> map = new HashMap<>();
        map.put("id",Long.toString(product.getId()>0?product.getId():-1L));
        map.put("name", product.getName());
        map.put("description", product.getDescription());
        map.put("price", Double.toString(product.getPrice()));

        JSONObject object = new JSONObject(map);
        return object.toJSONString();
    }

    @Test
    public void Should_Return_All_Products_And_Status_200() throws Exception {
        this.mockMvc.perform(get("/api/products/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)));

    }

    @Test
    public void Should_Return_A_Product_And_Status_200() throws Exception {
        this.mockMvc.perform(get("/api/products/"+productId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is("Test1")))
                .andExpect(jsonPath("$.description", is("Description1")));
    }

    @Test
    public void Should_Create_New_Product_And_Status_200() throws Exception {
        Product product  = new Product();
        product.setName("New Product");
        product.setDescription("New Description");
        product.setPrice(9.99);

        this.mockMvc.perform(post("/api/products/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJSONString(product)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is(product.getName())))
                .andExpect(jsonPath("$.description", is(product.getDescription())));
    }

    @Test
    public void Should_Update_Product_And_Status_200() throws Exception {
        Product product  = new Product();
        product.setId(productId);
        product.setName("Updated Product");
        product.setDescription("Updated Description");
        product.setPrice(5.55);

        this.mockMvc.perform(put("/api/products/"+productId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJSONString(product)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is(product.getName())))
                .andExpect(jsonPath("$.description", is(product.getDescription())));
    }
}


package limpo.orderservice.controller;

import limpo.orderservice.model.Product;
import limpo.orderservice.repository.ProductRepository;
import limpo.orderservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(ProductController.BASE_URL)
public class ProductController {


    public static final String BASE_URL = "api/products";

    @Autowired
    private ProductRepository repository;

    @Autowired
    private ProductService productService;

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllProducts() {

        List<Product> result = productService.getAllProducts();

        return new ResponseEntity(result, HttpStatus.OK);

    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable long id) {

        Product product = productService.getProductById(id);

        if(product.getId()==-1L){
            return new ResponseEntity(product.getDescription(),HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(product, HttpStatus.OK);

    }

    @PostMapping("/")
    public ResponseEntity<String> createNewProduct(@RequestBody Product product) {

        Product createdProduct = productService.createProduct(product);
        if(createdProduct.getId()==-2L){
            return new ResponseEntity(createdProduct.getDescription(),HttpStatus.CONFLICT);
        }
            return new ResponseEntity(createdProduct,HttpStatus.OK);


    }

    @PutMapping("/")
    public ResponseEntity<?> updateProduct(@RequestBody Product product) {

        Product updatedProduct = productService.updateProduct(product);
        if(updatedProduct.getId()==-1L){
            new ResponseEntity(updatedProduct.getDescription(), HttpStatus.NOT_FOUND);
        }else if(updatedProduct.getId()==-2L){
            new ResponseEntity(updatedProduct.getDescription(), HttpStatus.CONFLICT);
        }
        return
                new ResponseEntity(updatedProduct, HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable long id) {
        Product deletedProduct = productService.deleteProduct(id);

        if (deletedProduct.getId()==-1L) {
            new ResponseEntity(deletedProduct.getDescription(), HttpStatus.NOT_FOUND);
        }
            return new ResponseEntity<>(deletedProduct, HttpStatus.OK);

    }

}


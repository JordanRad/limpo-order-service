package limpo.orderservice.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(ProductController.BASE_URL)
public class ProductController {

    public static final String BASE_URL = "api/v1/order-service/products";

    @Autowired
    private ProductRepository repository;

    @Autowired
    private ProductService productService;

    @GetMapping("/")
    public ResponseEntity<?> getAllProducts() {

        List<Product> result = productService.getAllProducts();

        return new ResponseEntity(result, HttpStatus.OK);

    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable long id) {

        Product product = productService.getProductById(id);

        if (product == null) {
            return new ResponseEntity("Product cannot be found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(product, HttpStatus.OK);

    }

    @PostMapping("/")
    public ResponseEntity<String> createNewProduct(@RequestBody Product product) {
        Product createdProduct = productService.createProduct(product);

        if (createdProduct == null) {
            return new ResponseEntity("Product with this name already exists", HttpStatus.CONFLICT);
        }

        return new ResponseEntity(createdProduct, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestBody Product product) {

        Product updatedProduct = productService.updateProduct(id, product);

        if (updatedProduct == null) {
            return new ResponseEntity("Product cannot be found", HttpStatus.NOT_FOUND);
        }
        if (updatedProduct.getName() == null) {

            return new ResponseEntity("Product with this name already exists", HttpStatus.CONFLICT);
        }
        return
                new ResponseEntity(updatedProduct, HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable long id) {
        Product deletedProduct = productService.deleteProduct(id);

        if (deletedProduct == null) {
            new ResponseEntity("Product cannot be found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(deletedProduct, HttpStatus.OK);

    }

}


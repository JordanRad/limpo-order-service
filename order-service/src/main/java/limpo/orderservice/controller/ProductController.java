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
@RequestMapping("api/products")
public class ProductController {

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

        return product.getId() > 0 ?
                new ResponseEntity(product, HttpStatus.OK) :
                new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

    }

    @PostMapping("/")
    public ResponseEntity<?> createNewProduct(@RequestBody Product product) {

        Product p = productService.createProduct(product);
        return new ResponseEntity<String>(String.format("Product %s",product.getName()),HttpStatus.OK);
    }

    @PutMapping("/")
    public ResponseEntity<?> updateProduct(@RequestBody Product product) {

        Product updatedProduct = productService.updateProduct(product);

        return
                new ResponseEntity(String.format("Product with id %d has been successfully updated", updatedProduct.getId()), HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable long id) {
        boolean isDeleted = productService.deleteProduct(id);

        if (isDeleted) {
            return new ResponseEntity<>(String.format("Product with name %d has just been deleted.", id), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Product cannot be deleted.", HttpStatus.NOT_FOUND);
        }
    }

}


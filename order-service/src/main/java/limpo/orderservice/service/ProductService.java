package limpo.orderservice.service;

import limpo.orderservice.model.Product;
import limpo.orderservice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public ArrayList<Product> getAllProducts() {
        ArrayList result = (ArrayList<Product>) productRepository.findAll();
        return result;
    }

    public Product getProductByName(String name) {
        Product result = productRepository.findByName(name).orElse(null);
        return result;
    }

    public Product getProductById(Long id) {
        Product result = productRepository.findById(id).orElse(null);
        return result;
    }

    public Product createProduct(Product product) {
        Product result;
        try {
            result = productRepository.save(product);
        } catch (Exception e) {
            return null;
        }

        return result;

    }

    public Product updateProduct(long id, Product obj) {
        var updatedProduct = productRepository.findById(id).orElse(null);
        if (updatedProduct == null) {
            return null;
        }
        updatedProduct.setName(obj.getName());
        updatedProduct.setDescription(obj.getDescription());
        updatedProduct.setPrice(obj.getPrice());
        try {
            productRepository.save(updatedProduct);
        } catch (Exception e) {
            return new Product();
        }
        return updatedProduct;


    }

    public Product deleteProduct(Long id) {
        Product product = productRepository.findById(id).orElse(null);
        if (product != null) {
            productRepository.deleteById(id);
            return product;
        }
        return null;

    }
}

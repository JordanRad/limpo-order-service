package limpo.orderservice.service;

import limpo.orderservice.model.Product;
import limpo.orderservice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.InvalidAlgorithmParameterException;
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
        Product result = productRepository.findByName(name);
        return result;
    }

    public Product getProductById(Long id) {
        Product result = productRepository.findById(id).get();
        return result;
    }

    public Product createProduct(Product product) {

           Product result =  productRepository.save(product);
           return result;

    }

    public Product updateProduct(Product obj) {
        var updatedProduct = productRepository.findById(obj.getId()).get();
        updatedProduct.setName(obj.getName());
        updatedProduct.setDescription(obj.getDescription());
        updatedProduct.setPrice(obj.getPrice());
        productRepository.save(updatedProduct);
        return updatedProduct;


    }

    public boolean deleteProduct(Long id) {
        var p = productRepository.findById(id);
        if (p.isPresent()) {
            productRepository.deleteById(id);
            return true;

        }
        return false;
    }
}

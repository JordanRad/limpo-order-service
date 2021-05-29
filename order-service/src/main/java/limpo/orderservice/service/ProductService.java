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

    private boolean doesProductNameExist(String name){
        Product result = productRepository.findByName(name);
        return result.equals(new Product());
    }
    public ArrayList<Product> getAllProducts(){
        ArrayList result = (ArrayList<Product>) productRepository.findAll();
        return  result;
    }
    public Product getProductByName(String name){
        Product result = productRepository.findByName(name);
        return result;
    }
    public Product getProductById(Long id){
        Product result = productRepository.findById(id).get();
        return result;
    }
    public boolean createProduct(Product product){
        return false;
    }
}

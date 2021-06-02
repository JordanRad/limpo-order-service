package limpo.orderservice.service;

import limpo.orderservice.model.Product;
import limpo.orderservice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.InvalidAlgorithmParameterException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
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
        if(result.equals(new Product())){
            return new Product(-1L,"Invalid","Invalid id",0.00);
        }
        return result;
    }

    public Product getProductById(Long id) {
        Product result = productRepository.findById(id).orElse(new Product());
        if(result.equals(new Product())){
            return new Product(-1L,"Invalid","Invalid id",0.00);
        }
        return result;
    }

    public Product  createProduct(Product product) {
        Product result;
        try {
            result=  productRepository.save(product);
        }
        catch (Exception e){
            return new Product(-2L,"Invalid","This name already exists in the database",0.00);
        }

        return result;

    }

    public Product updateProduct(Product obj) {
        var updatedProduct = productRepository.findById(obj.getId()).orElse(new Product());
        if(updatedProduct.equals(new Product())){
            return new Product(-1L,"Invalid","Invalid id",0.00);
        }
        updatedProduct.setName(obj.getName());
        updatedProduct.setDescription(obj.getDescription());
        updatedProduct.setPrice(obj.getPrice());
        try {
            productRepository.save(updatedProduct);
        }catch (Exception e){
            return new Product(-2L,"Invalid","This name already exists in the database",0.00);
        }
        return updatedProduct;


    }

    public Product deleteProduct(Long id) {
        Product product = productRepository.findById(id).orElse(new Product());
        if(product.equals(new Product())){
            return new Product(-1L,"Invalid","Invalid id",0.00);
        }
        return product;
    }
}

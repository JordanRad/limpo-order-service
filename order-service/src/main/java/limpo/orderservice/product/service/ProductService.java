package limpo.orderservice.product.service;

import limpo.orderservice.product.model.Product;
import limpo.orderservice.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    /**
     * Get all products
     *
     * @return ArrayList of products
     */
    public ArrayList<Product> getAllProducts() {
        return (ArrayList<Product>) productRepository.findAll();
    }

    /**
     * Get a product by name
     *
     * @return Product
     */
    public Product getProductByName(String name) {
        return productRepository.findByName(name).orElse(null);
    }

    /**
     * Get a product by id
     *
     * @return Product
     */
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    /**
     * Create a product
     *
     * @return Created product
     */
    public Product createProduct(Product product) {
        Product result;

        try {
            result = productRepository.save(product);
        } catch (Exception e) {
            return null;
        }

        return result;
    }

    /**
     * Update a product
     *
     * @return Updated product
     */
    public Product updateProduct(long id, Product obj) {
        Product updatedProduct = productRepository.findById(id).orElse(null);

        if (updatedProduct == null) {
            return null;
        }
        updatedProduct.setName(obj.getName());
        updatedProduct.setDescription(obj.getDescription());
        updatedProduct.setPrice(obj.getPrice());
        updatedProduct.setType(obj.getType());

        try {
            productRepository.save(updatedProduct);
        } catch (Exception e) {
            return new Product();
        }

        return updatedProduct;
    }

    /**
     * Delete a product
     *
     * @return Deleted product
     */
    public Product deleteProduct(Long id) {
        Product product = productRepository.findById(id).orElse(null);

        if (product != null) {
            productRepository.deleteById(id);
            return product;
        }

        return null;
    }
}

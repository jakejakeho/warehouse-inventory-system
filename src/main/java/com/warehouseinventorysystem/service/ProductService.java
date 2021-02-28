package com.warehouseinventorysystem.service;

import com.warehouseinventorysystem.entity.Product;
import com.warehouseinventorysystem.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository repository;

    public Product saveProduct(Product product) {
        if (repository.existsProductByCode(product.getCode()))
            // Not allow duplicate code
            return null;
        return repository.save(product);
    }

    public List<Product> saveProducts(List<Product> products) {
        return repository.saveAll(products);
    }

    public List<Product> getProducts() {
        return repository.findAll();
    }

    public Product getProductById(int id) {
        return repository.findById(id).orElse(null);
    }

    public Product getProductByCode(String code) {
        return repository.findByCode(code);
    }

    public boolean existsProductByCode(String code) {
        return repository.existsProductByCode(code);
    }

    public String deleteProduct(int id) {
        repository.deleteById(id);
        return String.format("Product (id: %d) removed!", id);
    }

    public Product updateProduct(Product product) {
        Product existingProduct = repository.findById(product.getId()).orElse(null);
        existingProduct.setName(product.getName());
        existingProduct.setCode(product.getCode());
        existingProduct.setWeight(product.getWeight());
        return repository.save(existingProduct);
    }

}

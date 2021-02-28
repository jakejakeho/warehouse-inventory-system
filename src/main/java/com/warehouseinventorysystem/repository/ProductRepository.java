package com.warehouseinventorysystem.repository;

import com.warehouseinventorysystem.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    Product findByCode(String code);
    boolean existsProductByCode(String code);
}

package com.warehouseinventorysystem.repository;

import com.warehouseinventorysystem.entity.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WarehouseRepository extends JpaRepository<Warehouse, Integer> {
    Warehouse findByCode(String code);
    boolean existsWarehouseByCode(String Code);
}

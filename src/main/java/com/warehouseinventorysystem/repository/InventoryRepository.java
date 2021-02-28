package com.warehouseinventorysystem.repository;

import com.warehouseinventorysystem.entity.Inventory;
import com.warehouseinventorysystem.entity.InventoryKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Inventory, InventoryKey> {
    boolean existsInventoryById(InventoryKey id);

}

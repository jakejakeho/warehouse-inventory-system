package com.warehouseinventorysystem.service;

import com.warehouseinventorysystem.entity.Inventory;
import com.warehouseinventorysystem.entity.InventoryKey;
import com.warehouseinventorysystem.entity.Product;
import com.warehouseinventorysystem.entity.Warehouse;
import com.warehouseinventorysystem.repository.InventoryRepository;
import com.warehouseinventorysystem.repository.ProductRepository;
import com.warehouseinventorysystem.repository.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryService {
    @Autowired
    private InventoryRepository repository;

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private WarehouseRepository warehouseRepository;

    public Inventory saveInventory(Inventory inventory) {
        if (repository.existsInventoryById(inventory.getId()))
            // Not allow duplicate code
            return null;
        return repository.save(inventory);
    }

    public Inventory saveInventoryByProductCodeWarehouseCodeQuantity(String productCode, String warehouseCode, int quantity) {
        // Find product
        Product product = null;
        if (productCode != null && !productCode.isEmpty()) {
            product = productRepository.findByCode(productCode);
        }

        // Find warehouse
        Warehouse warehouse = null;
        if (warehouseCode != null && !warehouseCode.isEmpty()) {
            warehouse = warehouseRepository.findByCode(warehouseCode);
        }

        Inventory inventory = new Inventory();
        InventoryKey inventoryKey = new InventoryKey();
        inventoryKey.setProductId(product.getId());
        inventory.setProduct(product);
        inventoryKey.setWarehouseId(warehouse.getId());
        inventory.setWarehouse(warehouse);
        inventory.setId(inventoryKey);
        inventory.setQuantity(quantity);
        return this.saveInventory(inventory);
    }
}

package com.warehouseinventorysystem.service;

import com.warehouseinventorysystem.entity.Product;
import com.warehouseinventorysystem.entity.Warehouse;
import com.warehouseinventorysystem.repository.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WarehouseService {
    @Autowired
    private WarehouseRepository repository;

    public Warehouse saveWarehouse(Warehouse warehouse) {
        if (repository.existsWarehouseByCode(warehouse.getCode()))
            // Not allow duplicate code
            return null;
        return repository.save(warehouse);
    }

    public List<Warehouse> saveWarehouses(List<Warehouse> warehouses) {
        return repository.saveAll(warehouses);
    }

    public List<Warehouse> getWarehouses() {
        return repository.findAll();
    }

    public Warehouse getWarehouseById(int id) {
        return repository.findById(id).orElse(null);
    }

    public Warehouse getWarehouseByCode(String code) { return repository.findByCode(code); }

    public boolean existsWarehouseByCode(String code) { return repository.existsWarehouseByCode(code); }

    public String deleteWarehouse(int id) {
        repository.deleteById(id);
        return String.format("Warehouse (id: %d) removed!", id);
    }

    public Warehouse updateWarehouse(Warehouse warehouse) {
        Warehouse existingWarehouse = repository.findById(warehouse.getId()).orElse(null);
        existingWarehouse.setName(warehouse.getName());
        existingWarehouse.setCode(warehouse.getCode());
        existingWarehouse.setCapacity(warehouse.getCapacity());
        return repository.save(existingWarehouse);
    }
}

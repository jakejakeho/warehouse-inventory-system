package com.warehouseinventorysystem.controller;

import com.warehouseinventorysystem.entity.Inventory;
import com.warehouseinventorysystem.entity.InventoryKey;
import com.warehouseinventorysystem.entity.Product;
import com.warehouseinventorysystem.entity.Warehouse;
import com.warehouseinventorysystem.service.InventoryService;
import com.warehouseinventorysystem.service.ProductService;
import com.warehouseinventorysystem.service.WarehouseService;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class InventoryController {

    @Autowired
    private InventoryService service;
    @Autowired
    private ProductService productService;
    @Autowired
    private WarehouseService warehouseService;

    @PostMapping("/addInventory")
    public ResponseEntity<Object> addInventory(@RequestBody Map<String, Object> payload) {
        String productCode = (String) payload.get("product_code");
        String warehouseCode = (String) payload.get("warehouse_code");
        int quantity = (int) payload.get("quantity");

        return ResponseEntity.status(HttpStatus.OK).body(service.saveInventoryByProductCodeWarehouseCodeQuantity(productCode, warehouseCode, quantity).toString());
    }

    @PostMapping(value = "/importInventories", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> importInventories(@RequestParam("file") MultipartFile file) {
        boolean isValidFile = false;
        int succeed = 0;
        int numOfRows = 0;
        try {
            CSVParser csvParser = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(new InputStreamReader(file.getInputStream()));
            for (CSVRecord record : csvParser) {
                // Check if 3 columns
                if(record.size() != 3)
                    break;

                // Save product
                String warehouseCode = record.get("\uFEFFwarehouse_code");
                String productCode = record.get("product_code");
                int quantity = Integer.valueOf(record.get("quantity"));
                service.saveInventoryByProductCodeWarehouseCodeQuantity(productCode, warehouseCode, quantity);
                succeed++;
                numOfRows++;
                isValidFile = true;
            }
        } catch (IOException e) {
            e.printStackTrace();
            isValidFile = false;
        }
        if(!isValidFile)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("failed_to_load_csv_file");
        return ResponseEntity.status(HttpStatus.OK).body("success:" + succeed + " failed:" + (numOfRows - succeed));
    }
//
//    @GetMapping("/warehouses")
//    public List<Warehouse> findAllWarehouses() {
//        return service.getWarehouses();
//    }
//
//    @GetMapping("/warehouse/id/{id}")
//    public Warehouse findWarehouseById(@PathVariable int id) {
//        return service.getWarehouseById(id);
//    }
//
//    @GetMapping("/warehouse/code/{code}")
//    public Warehouse findWarehouseByCode(@PathVariable String code) {
//        return service.getWarehouseByCode(code);
//    }
//
//    @PutMapping("/updateWarehouse")
//    public Warehouse updateWarehouse(@RequestBody Warehouse warehouse) {
//        return service.updateWarehouse(warehouse);
//    }
//
//    @DeleteMapping("/warehouse/id/{id}")
//    public String deleteWarehouse(@PathVariable int id) {
//        return service.deleteWarehouse(id);
//    }
}

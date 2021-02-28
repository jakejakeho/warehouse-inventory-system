package com.warehouseinventorysystem.controller;

import com.warehouseinventorysystem.entity.Product;
import com.warehouseinventorysystem.entity.Warehouse;
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

@RestController
public class WarehouseController {

    @Autowired
    private WarehouseService service;

    @PostMapping("/addWarehouse")
    public Warehouse addWarehouse(@RequestBody Warehouse warehouse) {
        return service.saveWarehouse(warehouse);
    }

    @PostMapping(value = "/importWarehouses", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> addWareHouses(@RequestParam("file") MultipartFile file) {
        boolean isValidFile = false;
        int succeed = 0;
        int numOfRows = 0;
        try {
            CSVParser csvParser = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(new InputStreamReader(file.getInputStream()));
            List<Warehouse> warehouses = new ArrayList<Warehouse>();
            for (CSVRecord record : csvParser) {
                // Check if 3 columns
                if(record.size() != 3)
                    break;

                // Save product
                String code = record.get("\uFEFFCode");
                String name = record.get("Name");
                Double capacity = Double.valueOf(record.get("Capacity"));
                Warehouse warehouse = new Warehouse();
                warehouse.setCode(code);
                warehouse.setName(name);
                warehouse.setCapacity(capacity);
                System.out.println(warehouse);
                if(!service.existsWarehouseByCode(code)) {
                    succeed++;
                    warehouses.add(warehouse);
                }
                numOfRows++;
                isValidFile = true;
            }
            if(!warehouses.isEmpty())
                service.saveWarehouses(warehouses);
        } catch (IOException e) {
            e.printStackTrace();
            isValidFile = false;
        }
        if(!isValidFile)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("failed_to_load_csv_file");
        return ResponseEntity.status(HttpStatus.OK).body("success:" + succeed + " failed:" + (numOfRows - succeed));
    }

    @GetMapping("/warehouses")
    public List<Warehouse> findAllWarehouses() {
        return service.getWarehouses();
    }

    @GetMapping("/warehouse/id/{id}")
    public Warehouse findWarehouseById(@PathVariable int id) {
        return service.getWarehouseById(id);
    }

    @GetMapping("/warehouse/code/{code}")
    public Warehouse findWarehouseByCode(@PathVariable String code) {
        return service.getWarehouseByCode(code);
    }

    @PutMapping("/updateWarehouse")
    public Warehouse updateWarehouse(@RequestBody Warehouse warehouse) {
        return service.updateWarehouse(warehouse);
    }

    @DeleteMapping("/warehouse/id/{id}")
    public String deleteWarehouse(@PathVariable int id) {
        return service.deleteWarehouse(id);
    }
}

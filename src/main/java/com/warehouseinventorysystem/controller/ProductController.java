package com.warehouseinventorysystem.controller;

import com.warehouseinventorysystem.entity.Product;
import com.warehouseinventorysystem.service.ProductService;
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
public class ProductController {

    @Autowired
    private ProductService service;

    @PostMapping("/addProduct")
    public Product addProduct(@RequestBody Product product) { return service.saveProduct(product); }

    @PostMapping(value = "/importProducts", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> addProducts(@RequestParam("file") MultipartFile file) {
        boolean isValidFile = false;
        int succeed = 0;
        int numOfRows = 0;
        try {
            CSVParser csvParser = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(new InputStreamReader(file.getInputStream()));
            List<Product> products = new ArrayList<Product>();
            for (CSVRecord record : csvParser) {
                // Check if 3 columns
                if(record.size() != 3)
                    break;

                // Save product
                String code = record.get("\uFEFFCode");
                String name = record.get("Name");
                Double weight = Double.valueOf(record.get("Weight"));
                Product product = new Product();
                product.setCode(code);
                product.setName(name);
                product.setWeight(weight);
                System.out.println(product);
                if(!service.existsProductByCode(code)) {
                    succeed++;
                    products.add(product);
                }
                numOfRows++;
                isValidFile = true;
            }
            if(!products.isEmpty())
                service.saveProducts(products);
        } catch (IOException e) {
            e.printStackTrace();
            isValidFile = false;
        }
        if(!isValidFile)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("failed_to_load_csv_file");
        return ResponseEntity.status(HttpStatus.OK).body("success:" + succeed + " failed:" + (numOfRows - succeed));
    }

    @GetMapping("/products")
    public List<Product> findAllProducts() {
        return service.getProducts();
    }

    @GetMapping("/product/id/{id}")
    public Product findProductById(@PathVariable int id) {
        return service.getProductById(id);
    }

    @GetMapping("/product/code/{code}")
    public Product findProductByCode(@PathVariable String code) {
        return service.getProductByCode(code);
    }

    @PutMapping("/updateProduct")
    public Product updateProduct(@RequestBody Product product) {
        return service.updateProduct(product);
    }

    @DeleteMapping("/product/id/{id}")
    public String deleteProduct(@PathVariable int id) {
        return service.deleteProduct(id);
    }
}

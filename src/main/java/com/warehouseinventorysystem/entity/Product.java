package com.warehouseinventorysystem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
        name = "PRODUCT",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "CODE")
        }
)
public class Product {

    @Id
    @GeneratedValue
    private int id;
    private String name;
    private String code;
    private double weight;

    @OneToMany(mappedBy = "product")
    private Set<Inventory> inventories;

}

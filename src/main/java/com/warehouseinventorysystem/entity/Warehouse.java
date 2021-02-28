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
        name = "WAREHOUSE",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "CODE")
        }
)
public class Warehouse {

    @Id
    @GeneratedValue
    private int id;
    private String code;
    private String name;
    private double capacity;

    @OneToMany(mappedBy = "warehouse")
    private Set<Inventory> inventories;

}

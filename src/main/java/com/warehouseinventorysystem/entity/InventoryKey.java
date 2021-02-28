package com.warehouseinventorysystem.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public
class InventoryKey implements Serializable {

    @Column(name = "warehouse_id")
    private int warehouseId;

    @Column(name = "product_id")
    private int productId;

    private int getWarehouseId() {
        return warehouseId;
    }

    private int getProductId() {
        return productId;
    }

    public void setWarehouseId(int warehouseId) {
        this.warehouseId = warehouseId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }
}
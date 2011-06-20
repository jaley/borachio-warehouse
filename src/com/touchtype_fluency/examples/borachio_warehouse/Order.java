package com.touchtype_fluency.examples.borachio_warehouse;

/**
 * POJO for order details. Has a product name and quantity, 
 * plus a Warehouse to fill the order.
 */
public class Order {
    
    private Warehouse mWarehouse;
    
    private String mProduct;
    private int mQuantity;
    
    public Order(Warehouse warehouse) {
        mWarehouse = warehouse;
    }
    
    public Order(Warehouse warehouse, String name, int quantity) {
        mWarehouse = warehouse;
        mProduct = name;
        mQuantity = quantity;
    }

    public String getProductName() {
        return mProduct;
    }

    public void setProductName(String product) {
        mProduct = product;
    }

    public int getQuantity() {
        return mQuantity;
    }

    public void setQuantity(int quantity) {
        mQuantity = quantity;
    }
    
    public boolean fill() {
        if(mWarehouse.hasInventory(mProduct, mQuantity)) {
            mWarehouse.remove(mProduct, mQuantity);
            return true;
        }
        
        return false;
    }
}

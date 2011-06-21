package com.touchtype_fluency.examples.borachio_warehouse;

import com.google.inject.Inject;

/**
 * POJO for order details. Has a product name and quantity, 
 * plus a Warehouse to fill the order.
 */
public class OrderImpl implements Order {
    
    // Dependencies injected by Guice
    private Warehouse mWarehouse;
    
    // Order state
    private String mProduct;
    private int mQuantity;
    
    @Inject
    public OrderImpl(Warehouse warehouse) {
        mWarehouse = warehouse;
    }
    
    /* (non-Javadoc)
     * @see com.touchtype_fluency.examples.borachio_warehouse.Order#update(java.lang.String, int)
     */
    public void update(String product, int quantity) {
        mProduct = product;
        mQuantity = quantity;
    }
    
    /* (non-Javadoc)
     * @see com.touchtype_fluency.examples.borachio_warehouse.Order#fill()
     */
    public boolean fill() {
        if(mWarehouse.hasInventory(mProduct, mQuantity)) {
            mWarehouse.remove(mProduct, mQuantity);
            return true;
        }
        
        return false;
    }
}

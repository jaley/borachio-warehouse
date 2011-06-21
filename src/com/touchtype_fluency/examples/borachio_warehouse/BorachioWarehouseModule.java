package com.touchtype_fluency.examples.borachio_warehouse;

import roboguice.config.AbstractAndroidModule;

public class BorachioWarehouseModule extends AbstractAndroidModule {
    public BorachioWarehouseModule() {
        super();
    }
    
    protected void configure() {
        bind(Warehouse.class).to(WarehouseImpl.class);
        bind(Order.class).to(OrderImpl.class);
    }
}

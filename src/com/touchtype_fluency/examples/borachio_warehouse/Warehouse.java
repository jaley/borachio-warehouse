package com.touchtype_fluency.examples.borachio_warehouse;

import java.util.Map;

public interface Warehouse {
    void add(String name, int quantity);
    void remove(String name, int quantity);
    
    boolean hasInventory(String name, int quantity);
    Map<String, Integer> getInventory();
}

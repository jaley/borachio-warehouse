package com.touchtype_fluency.examples.borachio_warehouse;

import java.util.ArrayList;

import android.util.Pair;

public interface Warehouse {
    void add(String name, int quantity);
    void remove(String name, int quantity);
    
    boolean hasInventory(String name, int quantity);
    ArrayList<Pair<String, Integer>> getInventory();
}

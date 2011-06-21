package com.touchtype_fluency.examples.borachio_warehouse;

public interface Order {
    void update(String product, int quantity);
    boolean fill();
}

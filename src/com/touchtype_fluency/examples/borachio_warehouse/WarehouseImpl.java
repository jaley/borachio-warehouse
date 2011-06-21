package com.touchtype_fluency.examples.borachio_warehouse;

import java.util.ArrayList;

import android.util.Pair;

public class WarehouseImpl implements Warehouse {

    ArrayList<Pair<String, Integer>> mData = new ArrayList<Pair<String, Integer>>();
    
    public WarehouseImpl() {
        mData.add(new Pair<String, Integer>("Talisker", 10));
        mData.add(new Pair<String, Integer>("Laphroaig", 30));
    }
    
    @Override
    public void add(String name, int quantity) {
        int stockNow = 0;
        for(int i = 0; i < mData.size(); ++i) {
            Pair<String, Integer> stockItem = mData.get(i);
            if(stockItem.first.equals(name)) {
                stockNow = stockItem.second;
                mData.remove(i);
                break;
            }
        }
        
        mData.add(new Pair<String,Integer>(name, stockNow + quantity));
    }

    @Override
    public ArrayList<Pair<String, Integer>> getInventory() {
        return mData;
    }

    @Override
    public boolean hasInventory(String name, int quantity) {
        for(Pair<String, Integer> stockItem : mData) {
            if(stockItem.first.equals(name) && stockItem.second >= quantity) {
                return true;
            }
        }
        
        return false;
    }

    @Override
    public void remove(String name, int quantity) {
        for(int i = 0; i < mData.size(); ++i) {
            Pair<String, Integer> stockItem = mData.get(i);
            if(stockItem.first.equals(name)) {
                mData.remove(i);
                int qtyNow = stockItem.second - quantity;
                if (qtyNow > 0) {
                    mData.add(new Pair<String, Integer>(name, qtyNow));
                }
            }
        }
    }
}

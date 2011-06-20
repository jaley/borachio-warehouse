package com.touchtype_fluency.examples.borachio_warehouse;

import android.app.TabActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TabHost;

public class BorachioWarehouse extends TabActivity {
    
    private BaseAdapter mAdapter;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        TabHost tabHost = getTabHost();
        LayoutInflater.from(this).inflate(R.layout.main, tabHost.getTabContentView(), true);

        tabHost.addTab(tabHost.newTabSpec("inventory")
                .setIndicator("Inventory")
                .setContent(R.id.listpage));
 
        tabHost.addTab(tabHost.newTabSpec("order")
                .setIndicator("Order")
                .setContent(R.id.orderpage));
         
        mAdapter = new BorachioWarehouseAdapter(this);
                
        ListView list = (ListView) findViewById(R.id.inventory);
        list.setAdapter(mAdapter);
        
    }
}
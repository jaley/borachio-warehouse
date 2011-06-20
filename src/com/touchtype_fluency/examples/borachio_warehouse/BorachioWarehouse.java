package com.touchtype_fluency.examples.borachio_warehouse;

import java.util.ArrayList;

import android.app.TabActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TabHost;

public class BorachioWarehouse extends TabActivity {
    
    private ArrayAdapter<String> mAdapter;
    private ArrayList<String> mTestStrings = new ArrayList<String>();
    
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
         
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mTestStrings);
        
        mAdapter.add("Test line 1");
        mAdapter.add("Test line 2");
        
        ListView list = (ListView) findViewById(R.id.inventory);
        list.setAdapter(mAdapter);
        
    }
}
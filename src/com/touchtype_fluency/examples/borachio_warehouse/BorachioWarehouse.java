package com.touchtype_fluency.examples.borachio_warehouse;

import android.app.TabActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.Toast;

public class BorachioWarehouse extends TabActivity {
    
    private BaseAdapter mListAdapter;
    private ArrayAdapter mSpinnerAdapter;
    private Warehouse mWarehouse = new WarehouseImpl();
    
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
         
        mListAdapter = new BorachioWarehouseAdapter(this);                
        ListView list = (ListView) findViewById(R.id.inventory);
        list.setAdapter(mListAdapter);
        
        mSpinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item) {
            
            public String getItem(int position) {
                return mWarehouse.getInventory().get(position).first;
            }
            
            public long getItemId(int position) {
                return position;
            }
            
            public int getCount() {
                return mWarehouse.getInventory().size();
            }
        };
        mSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinner = (Spinner) findViewById(R.id.orderspinner);
        spinner.setAdapter(mSpinnerAdapter);        
    }
    
    public void onClick(View v) {
        String name;
        String qty;
        
        switch(v.getId()) {
            case R.id.addbutton:
                name = ((EditText) findViewById(R.id.name_field)).getText().toString();
                qty = ((EditText) findViewById(R.id.qty_field)).getText().toString();
                mWarehouse.add(name, Integer.parseInt(qty));
                mListAdapter.notifyDataSetChanged();
                break;
                
            case R.id.orderbutton:
                name = (String) ((Spinner) findViewById(R.id.orderspinner)).getSelectedItem();
                qty = ((EditText) findViewById(R.id.order_qty)).getText().toString();
                
                Order o = new Order(mWarehouse, name, Integer.parseInt(qty));
                if(o.fill()) {
                    Toast placed = Toast.makeText(this, "Order placed!", Toast.LENGTH_SHORT);
                    placed.show();
                    
                } else {
                    Toast failed = Toast.makeText(this, "No stock available!", Toast.LENGTH_SHORT);
                    failed.show();
                }
                break;
                
            default:
                break;
        }
        
        mListAdapter.notifyDataSetChanged();
        mSpinnerAdapter.notifyDataSetChanged();
    }    
    
    public Warehouse getWarehouse() {
        return mWarehouse;
    }
}
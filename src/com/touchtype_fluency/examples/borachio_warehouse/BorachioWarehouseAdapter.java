package com.touchtype_fluency.examples.borachio_warehouse;

import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Wrapper around the Warehouse class to convert things for the list view.
 */
public class BorachioWarehouseAdapter extends BaseAdapter {

    private BorachioWarehouse mWarehouseActivity;
    
    public BorachioWarehouseAdapter(BorachioWarehouse w) {
        mWarehouseActivity = w;
    }
    
    @Override
    public int getCount() {
        return mWarehouseActivity.getWarehouse().getInventory().size();
    }

    @Override
    public Object getItem(int index) {
        return mWarehouseActivity.getWarehouse().getInventory().get(index);
    }

    @Override
    public long getItemId(int index) {
        return index;
    }

    @Override
    public View getView(int index, View convertView, ViewGroup parent) {

        // Set up Android's list view component
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mWarehouseActivity).inflate(R.layout.list_item, null);            
            
            holder = new ViewHolder();
            holder.mProduct = (TextView) convertView.findViewById(R.id.product_label);
            holder.mQuantity = (TextView) convertView.findViewById(R.id.qty_label);
            
            convertView.setTag(holder);            
            
        } else {
            holder = (ViewHolder) convertView.getTag();            
        }
        
        // Now read the data and add it to the list view item
        Pair<String, Integer> data = mWarehouseActivity.getWarehouse().getInventory().get(index);
        holder.mProduct.setText(data.first);
        holder.mQuantity.setText(data.second.toString());
        
        return convertView;
    }
    
    static class ViewHolder {
        TextView mProduct;
        TextView mQuantity;
    }

}

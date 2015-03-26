package com.example.obigrocery.adapters;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.obigrocery.POJO.ItemPOJO;
import com.example.obigrocery.activities.R;

public class ItemListAdapterGen extends BaseAdapter implements ListAdapter {
    protected List<ItemPOJO> list = new ArrayList<>();
    protected List<ItemPOJO> display = new ArrayList<>();
    protected Context context;

    /******************************************************************
     * Model logic
     * list - list of all items in the view
     * display - list of current items displayed
     ******************************************************************/
    public ItemListAdapterGen(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return display.size();
    }

    @Override
    public ItemPOJO getItem(int pos) {
        return display.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        // not needed
        return 0;
    }

    public List<ItemPOJO> getList() {
        return list;
    }
    
    public void add(ItemPOJO object) {
        list.add(object);
        display.add(object);
        this.notifyDataSetChanged();
    }
    
    public void remove(ItemPOJO object) {
        list.remove(object);
        display.remove(object);
        this.notifyDataSetChanged();
    }
    

    /******************************************************************
     * Display stuff
     ******************************************************************/
    
    /*
     * Used to change display based on item category
     */
    public void displayCategory(String category) {
        display.clear();
        this.notifyDataSetChanged();
        
        ArrayList<ItemPOJO> temp = new ArrayList<>();
        if(category.equalsIgnoreCase("all")) {
            for(ItemPOJO item : list) {
                temp.add(item);
            }
        } else {
            for(ItemPOJO item : list) {
                if(item.getCategory().equals(category)) {
                    temp.add(item);
                }
            }
        }
        display = temp;
        this.notifyDataSetChanged();
    }

    /*
     * Creates the view for the display
     */
    @SuppressLint("InflateParams")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.layout_item_list, null);
        }

        TextView listItemText = (TextView) view
                .findViewById(R.id.list_item_string);
        listItemText.setText(display.get(position).toString());

        return view;
    }
}

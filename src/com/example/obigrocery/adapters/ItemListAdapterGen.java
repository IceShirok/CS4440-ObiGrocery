package com.example.obigrocery.adapters;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.obigrocery.activities.Populator;
import com.example.obigrocery.activities.R;
import com.example.obigrocery.sqlmodel.ListGrocery;

public class ItemListAdapterGen extends BaseAdapter implements ListAdapter {
    protected List<ListGrocery> list = new ArrayList<>();
    protected List<ListGrocery> display = new ArrayList<>();
    protected Context context;
    protected long shoppingListId;

    /******************************************************************
     * Model logic
     * list - list of all items in the view
     * display - list of current items displayed
     ******************************************************************/
    public ItemListAdapterGen(Context context, long shoppingListId) {
        this.context = context;
        this.shoppingListId = shoppingListId;
    }

    @Override
    public int getCount() {
        return display.size();
    }

    @Override
    public ListGrocery getItem(int pos) {
        return display.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        // not needed
        return 0;
    }
    
    public void checkItem(ListGrocery object, boolean check) {
        list.remove(object);
        object.setIsPurchased(check ? 1 : 0);
        list.add(object);
    }

    public List<ListGrocery> getList() {
        return list;
    }
    
    public void add(ListGrocery object) {
        list.add(object);
        display.add(object);
        this.notifyDataSetChanged();
    }
    
    public void remove(ListGrocery object) {
        list.remove(object);
        display.remove(object);
        this.notifyDataSetChanged();
    }
    

    /******************************************************************
     * Display stuff
     ******************************************************************/

    public void displayCategory(String category) {
        display.clear();
        this.notifyDataSetChanged();
        
        Collections.sort(list);
        
        ArrayList<ListGrocery> temp = new ArrayList<>();
        if(category.equalsIgnoreCase(Populator.ALL_CATEGORY)) {
            for(ListGrocery item : list) {
                temp.add(item);
            }
        } else {
            for(ListGrocery item : list) {
                if(item.getProducts().getCategory().equals(category)) {
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

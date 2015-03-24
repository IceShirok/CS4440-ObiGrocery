package com.example.obigrocery.adapters;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.obigrocery.POJO.ItemPOJO;
import com.example.obigrocery.activities.R;

public class ItemListAdapterGen extends BaseAdapter implements ListAdapter {
    protected List<ItemPOJO> list = new ArrayList<>();
    protected List<ItemPOJO> display = new ArrayList<>();
    protected Context context;

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
        return 0;
        //return list.get(pos).getId();
        // just return 0 if your list items do not have an Id variable.
    }

    public List<ItemPOJO> getList() {
        return list;
    }
    
    public void add(ItemPOJO object) {
        list.add(object);
        display.add(object);
        this.notifyDataSetChanged();
    }
    
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

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.layout_item_list, null);
        }

        // Handle TextView and display string from your list
        TextView listItemText = (TextView) view
                .findViewById(R.id.list_item_string);
        listItemText.setText(display.get(position).toString());

        Button deleteBtn = (Button) view.findViewById(R.id.delete_btn);

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // do something
                ItemPOJO item = display.remove(position);
                list.remove(item); // or some other task
                notifyDataSetChanged();
            }
        });

        return view;
    }
}

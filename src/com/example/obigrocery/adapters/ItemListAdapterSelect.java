package com.example.obigrocery.adapters;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.obigrocery.activities.R;
import com.example.obigrocery.sqlmodel.ListGrocery;

public class ItemListAdapterSelect extends ItemListAdapterGen {

    public ItemListAdapterSelect(Context context, long shoppingListId) {
        super(context, shoppingListId);
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.layout_item_check, null);
        }

        TextView listItemText = (TextView) view
                .findViewById(R.id.list_item_string);
        listItemText.setText(display.get(position).toString());

        final CheckBox selectCheckbox = (CheckBox) view.findViewById(R.id.check);

        selectCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListGrocery item = display.get(position);
                remove(item);
                item.setIsPurchased(selectCheckbox.isChecked() ? 1 : 0);
                add(item);
            }
        });

        return view;
    }
    
    /*
     * note: act like a set - only distinct stuff on display
     */
    public void add(ListGrocery object) {
        if(!list.contains(object)) {
            list.add(object);
            display.add(object);
            this.notifyDataSetChanged();
        }
    }
    
    public List<ListGrocery> getCheckedList() {
        List<ListGrocery> temp = new ArrayList<>();
        for(int i=0; i<list.size(); i++) {
            if(list.get(i).getIsPurchased() != 0) {
                temp.add(list.get(i));
                System.out.println("selected: "+list.get(i));
            }
        }
        return temp;
    }

}

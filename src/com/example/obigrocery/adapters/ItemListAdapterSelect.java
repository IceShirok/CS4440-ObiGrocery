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

import com.example.obigrocery.POJO.ItemPOJO;
import com.example.obigrocery.activities.R;

public class ItemListAdapterSelect extends ItemListAdapterGen {
    
    /*
     * TODO fix because the superclass sorts
     */
    private ArrayList<Boolean> checkedList;
    private boolean isPopulated;

    public ItemListAdapterSelect(Context context, int shoppingListId) {
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
                /*
                 * TODO may not want to save until finishing
                 */
                if(!isPopulated) {
                    checkedList = new ArrayList<>();
                    for(int i=0; i<list.size(); i++) {
                        checkedList.add(false);
                    }
                    isPopulated = true;
                }
                checkedList.add(position, selectCheckbox.isChecked());
            }
        });

        return view;
    }
    
    public List<ItemPOJO> getCheckedList() {
        List<ItemPOJO> temp = new ArrayList<>();
        for(int i=0; i<list.size(); i++) {
            if(checkedList.get(i).booleanValue()) {
                temp.add(list.get(i));
                System.out.println(list.get(i));
            }
        }
        return temp;
    }

}

package com.example.obigrocery.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.obigrocery.activities.R;
import com.example.obigrocery.db.ListGroceryDAO;
import com.example.obigrocery.sqlmodel.ListGrocery;

public class ItemListAdapterPurchase extends ItemListAdapterGen {
    
    private ListGroceryDAO listGroceryDb;

    public ItemListAdapterPurchase(Context context, long shoppingListId) {
        super(context, shoppingListId);
        listGroceryDb = new ListGroceryDAO(context);
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

        final CheckBox purchasedCheckbox = (CheckBox) view.findViewById(R.id.check);
        purchasedCheckbox.setChecked(getItem(position).getIsPurchased() == 1 ? true : false);

        purchasedCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListGrocery item = getItem(position);
                int isChecked = purchasedCheckbox.isChecked() ? 1 : 0;
                checkItem(item, purchasedCheckbox.isChecked());
                listGroceryDb.updateListGrocery(item.getId(), item.getListId(), item.getProductID(),
                        item.getUnits(), item.getAmount(), isChecked);
            }
        });

        return view;
    }
}

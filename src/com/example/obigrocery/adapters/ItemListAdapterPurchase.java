package com.example.obigrocery.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.obigrocery.activities.R;

public class ItemListAdapterPurchase extends ItemListAdapterGen {

    public ItemListAdapterPurchase(Context context, int shoppingListId) {
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

        final CheckBox purchasedCheckbox = (CheckBox) view.findViewById(R.id.check);
        purchasedCheckbox.setChecked(getItem(position).isPurchased());

        purchasedCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkItem(getItem(position), purchasedCheckbox.isChecked());
                /*
                 * TODO save this information to the database
                 * you can use shoppingListId
                 */
            }
        });

        return view;
    }
}

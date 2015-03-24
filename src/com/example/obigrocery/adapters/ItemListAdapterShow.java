package com.example.obigrocery.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.obigrocery.activities.R;

public class ItemListAdapterShow extends ItemListAdapterGen {

    public ItemListAdapterShow(Context context) {
        super(context);
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
        deleteBtn.setVisibility(View.GONE);

        return view;
    }
}

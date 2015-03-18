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

import com.example.obigrocery.activities.R;

public class ItemListAdapter<T> extends BaseAdapter implements ListAdapter {
    private List<T> list = new ArrayList<>();
    private List<String> display = new ArrayList<>();
    private Context context;

    public ItemListAdapter(List<T> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public T getItem(int pos) {
        return list.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return 0;
        //return list.get(pos).getId();
        // just return 0 if your list items do not have an Id variable.
    }

    public List<T> getList() {
        return list;
    }
    
    public void add(T object) {
        list.add(object);
        display.add(object.toString());
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
        listItemText.setText(list.get(position).toString());

        Button deleteBtn = (Button) view.findViewById(R.id.delete_btn);

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // do something
                list.remove(position); // or some other task
                display.remove(position);
                notifyDataSetChanged();
            }
        });

        return view;
    }
}

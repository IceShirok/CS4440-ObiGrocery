package com.example.obigrocery.activities.lists;

import java.math.BigDecimal;
import java.util.List;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.obigrocery.POJO.ItemPOJO;
import com.example.obigrocery.activities.R;
import com.example.obigrocery.activities.global.CategoryPopulator;
import com.example.obigrocery.activities.global.EditGroceryListGen;

public class EditGroceryList extends EditGroceryListGen {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            // TODO use shoppingListId to populate listview with db stuff
            shoppingListId = extras.getInt("SHOPPING_LIST_ID");

            // display stuff starts
            adapter.add(new ItemPOJO("Bread", new BigDecimal(1), shoppingListId, "Baked Goods"));
            adapter.add(new ItemPOJO("Bread", new BigDecimal(1), shoppingListId, "Baked Goods"));
            adapter.add(new ItemPOJO("Bread", new BigDecimal(1), 1, "Baked Goods"));
            adapter.add(new ItemPOJO("Meat", new BigDecimal(1), 1, "Meats"));
            adapter.add(new ItemPOJO("Meats", new BigDecimal(1), 1, "BMeats"));
            adapter.add(new ItemPOJO("Dairy", new BigDecimal(1), 1, "Dairy"));
            // display stuff ends
            
            // TODO the grocery list should be filled by info from the db
            String title = "Obi Grocery - Edit List " + shoppingListId;
            this.setTitle(title);
            listTextbox.setText(title);

            itemsView = (ListView) findViewById(R.id.itemView);
            itemsView.setAdapter(adapter);
            populateCategories();
            
        } else {
            String errorMessage = "List missing from database. Exiting...";

            new AlertDialog.Builder(this)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setTitle("Error")
            .setMessage(errorMessage)
            .setNeutralButton("OK", null)
            .show();
            finish();
        }
    }
    
    protected void editItemAlert(final ItemPOJO item) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("Editing " + item.getName())
                .setMessage("Edit to your heart's desire.");
        final FrameLayout frameView = new FrameLayout(this);
        builder.setView(frameView);
        
        final AlertDialog alertDialog = builder.create();
        LayoutInflater inflater = alertDialog.getLayoutInflater();
        View dialoglayout = inflater.inflate(R.layout.alert_edit_grocery_one, frameView);

        final EditText nText = (EditText) dialoglayout.findViewById(R.id.itemNameTextbox);
        nText.setText(item.getName());
        final EditText qText = (EditText) dialoglayout.findViewById(R.id.quantityTextbox);
        qText.setText(item.getQuantity()+"");
        final EditText pText = (EditText) dialoglayout.findViewById(R.id.priceTextbox);
        pText.setText(item.getPrice().toString());
        final Spinner cText = (Spinner) dialoglayout.findViewById(R.id.categorySpinner);

        final CheckBox purchasedBox = (CheckBox) dialoglayout.findViewById(R.id.purchasedBox);
        purchasedBox.setChecked(item.isPurchased());
        qText.setEnabled(purchasedBox.isChecked());
        pText.setEnabled(purchasedBox.isChecked());
        purchasedBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO save this info to database?
                qText.setEnabled(purchasedBox.isChecked());
                pText.setEnabled(purchasedBox.isChecked());
            }
        });

        List<String> spin = CategoryPopulator.getCategories(false);
        ArrayAdapter<String> cAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, spin);
        cAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cText.setAdapter(cAdapter);

        final Button updateButton = (Button) dialoglayout.findViewById(R.id.updateButton);
        final Button deleteButton = (Button) dialoglayout.findViewById(R.id.deleteButton);
        final Button cancelButton = (Button) dialoglayout.findViewById(R.id.cancelButton);

        updateButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO update display, update db
                String nameText = null;
                String priceText = null;
                String quantityText = null;
                String categoryText = null;
                ItemPOJO itemNew = null;
                
                nameText = nText.getText().toString();
                categoryText = cText.getSelectedItem().toString();
                System.out.println(purchasedBox.isChecked());
                if(purchasedBox.isChecked()) {
                    priceText = pText.getText().toString();
                    quantityText = qText.getText().toString();
                    itemNew = isValidItem(nameText, priceText, quantityText, categoryText);
                } else {
                    itemNew = isValidItem(nameText, categoryText);
                }
                System.out.println(item);
                System.out.println(itemNew);
                System.out.println(itemNew.getQuantity());
                System.out.println(itemNew.getPrice());

                if(itemNew != null) {
                    categoryShift(categoryText);
                    adapter.add(itemNew);
                    adapter.remove(item);
                    itemsView.invalidateViews();
                    itemsView.setAdapter(adapter);
                }
                alertDialog.cancel();
            }
        });

        cancelButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.cancel();
            }
        });

        deleteButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.remove(item);
                itemsView.invalidateViews();
                itemsView.setAdapter(adapter);
                alertDialog.cancel();
            }
        });

        alertDialog.show();
    }
}

package com.example.obigrocery.activities.newgrocery;

import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.obigrocery.POJO.ItemPOJO;
import com.example.obigrocery.activities.R;
import com.example.obigrocery.activities.global.CategoryPopulator;
import com.example.obigrocery.activities.global.EditGroceryListGen;
import com.example.obigrocery.adapters.ItemListAdapterGen;

public class NewGroceryList extends EditGroceryListGen {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_grocery_list);
        
        addGroceryButton = (Button) findViewById(R.id.addGroceryButton);
        addGroceryButton.setEnabled(false);

        finishGroceryButton = (Button) findViewById(R.id.finishGroceryButton);
        finishGroceryButton.setEnabled(false);

        duplicateGroceryButton = (Button) findViewById(R.id.duplicateGroceryButton);
        duplicateGroceryButton.setEnabled(false);

        listTextbox = (EditText) findViewById(R.id.listTextbox);
        listTextbox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                    int count) {
                finishGroceryButton.setEnabled(listTextbox.getText().length() > 0);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
        
        itemNameTextbox = (EditText) findViewById(R.id.itemNameTextbox);
        itemNameTextbox.addTextChangedListener(new AddGroceryWatcher());
        
        quantityTextbox = (EditText) findViewById(R.id.quantityTextbox);
        quantityTextbox.addTextChangedListener(new AddGroceryWatcher());
        quantityTextbox.setVisibility(View.GONE);
        
        priceTextbox = (EditText) findViewById(R.id.priceTextbox);
        priceTextbox.addTextChangedListener(new AddGroceryWatcher());
        priceTextbox.setVisibility(View.GONE);
        
        TextView markup = (TextView) findViewById(R.id.quantityText);
        markup.setVisibility(View.GONE);

        itemsView = (ListView) findViewById(R.id.itemView);

        adapter = new ItemListAdapterGen(this);
        itemsView.setAdapter(adapter);
        itemsView.setClickable(true);
        itemsView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                editItemAlert(adapter.getItem(position));
            }
        });

        populateCategories();
    }
    
    @Override
    public boolean addGroceryItem(View view) {
        System.out.println("adding");
        // hides keyboard
        InputMethodManager imm = (InputMethodManager) getBaseContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(itemNameTextbox.getWindowToken(), 0);

        ItemPOJO formedItem = isValidItem(itemNameTextbox.getText().toString(),
                categorySpinner.getSelectedItem().toString());
        if (formedItem != null) {
            System.out.println(formedItem);
            addItemsToDisplay(formedItem);
            addItemsToDatabase(formedItem);
            itemNameTextbox.setText("");
            return true;
        } else {
            String errorMessage = "Incorrect number format!";

            new AlertDialog.Builder(this)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setTitle("Error")
            .setMessage(errorMessage)
            .setNeutralButton("OK", null)
            .show();
            return false;
        }
    }

    @Override
    protected void addItemsToDatabase(ItemPOJO item) {
        // TODO implement so item can be added to database
    }

    @Override
    protected boolean enableAdd() {
        return itemNameTextbox.getText().length() > 0
                && !categorySpinner.getSelectedItem().toString().equalsIgnoreCase("All");
    }
    
    @Override
    protected void editItemAlert(final ItemPOJO item) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("Editing " + item.getName())
                .setMessage("Edit to your heart's desire.");
        final FrameLayout frameView = new FrameLayout(this);
        builder.setView(frameView);
        
        final AlertDialog alertDialog = builder.create();
        LayoutInflater inflater = alertDialog.getLayoutInflater();
        View dialoglayout = inflater.inflate(R.layout.alert_edit_grocery_one, frameView);

        final CheckBox purchasedBox = (CheckBox) dialoglayout.findViewById(R.id.purchasedBox);
        purchasedBox.setChecked(item.isPurchased());
        purchasedBox.setEnabled(false);

        final EditText nText = (EditText) dialoglayout.findViewById(R.id.itemNameTextbox);
        nText.setText(item.getName());
        final EditText qText = (EditText) dialoglayout.findViewById(R.id.quantityTextbox);
        qText.setVisibility(View.GONE);
        final EditText pText = (EditText) dialoglayout.findViewById(R.id.priceTextbox);
        pText.setVisibility(View.GONE);
        final Spinner cText = (Spinner) dialoglayout.findViewById(R.id.categorySpinner);

        List<String> spin = CategoryPopulator.getCategories(false);
        ArrayAdapter<String> cAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, spin);
        cAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cText.setAdapter(cAdapter);

        final Button updateButton = (Button) dialoglayout.findViewById(R.id.updateButton);
        final Button deleteButton = (Button) dialoglayout.findViewById(R.id.deleteButton);
        final Button cancelButton = (Button) dialoglayout.findViewById(R.id.cancelButton);
        
        TextView markup = (TextView) findViewById(R.id.quantityText);
        markup.setVisibility(View.GONE);

        updateButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO update display, update db
                String nameText = null;
                String categoryText = null;
                ItemPOJO itemNew = null;
                
                nameText = nText.getText().toString();
                categoryText = cText.getSelectedItem().toString();
                itemNew = isValidItem(nameText, categoryText);

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

    /*
     * Done with all grocery list items, add everything to db, go to
     * confirmation page
     */
    @Override
    public void finishGroceryList(View view) {
        String listName = listTextbox.getText().toString();

        // TODO also check database if list already exists
        if (listName != null && listName.length() > 0) {
            List<ItemPOJO> list = adapter.getList();
            System.out.println("Print out finished list");
            System.out.println("Name: " + listName);
            for (ItemPOJO item : list) {
                System.out.println("\tItem: " + item);
            }
            // TODO put everything to database
            // TODO goto list of shopping lists, or main menu with confirmation

            new AlertDialog.Builder(this)
            .setIcon(android.R.drawable.ic_dialog_info)
            .setTitle("Confirmation")
            .setMessage("List saved to database.")
            .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            })
            .show();
        } else {
            String errorMessage = "Not a valid list name.";

            new AlertDialog.Builder(this)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setTitle("Error")
            .setMessage(errorMessage)
            .setNeutralButton("OK", null)
            .show();
        }
    }
}

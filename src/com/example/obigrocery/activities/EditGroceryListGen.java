package com.example.obigrocery.activities;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.obigrocery.POJO.ItemPOJO;
import com.example.obigrocery.activities.newgrocery.NewGroceryAll;
import com.example.obigrocery.adapters.ItemListAdapterGen;

public class EditGroceryListGen extends ActionBarActivity {

    protected int shoppingListId;

    protected EditText listTextbox;
    protected EditText itemNameTextbox;
    protected EditText quantityTextbox;
    protected EditText priceTextbox;
    protected ListView itemsView;
    protected Spinner categorySpinner;

    protected Button addGroceryButton;
    protected Button finishGroceryButton;
    protected Button duplicateGroceryButton;

    protected ItemListAdapterGen adapter;

    /******************************************************************
     * Instantiation of stuff into the app
     ******************************************************************/
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
        
        priceTextbox = (EditText) findViewById(R.id.priceTextbox);
        priceTextbox.addTextChangedListener(new AddGroceryWatcher());

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

    /******************************************************************
     * Options menu (don't worry about this)
     ******************************************************************/
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.new_grocery_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    
    /******************************************************************
     * Selecting between categories
     ******************************************************************/
    protected void categoryShift(String category) {
        adapter.displayCategory(category);
        itemsView.invalidateViews();
        itemsView.setAdapter(adapter);
        addGroceryButton.setEnabled(enableAdd());
    }
    
    protected List<String> getCategories() {
        List<String> spinnerArray = new ArrayList<String>();
        // TODO program to add categories from database
        spinnerArray.add("Baked Goods");
        spinnerArray.add("Dairy");
        spinnerArray.add("Meats");
        spinnerArray.add("Vegetables");
        return spinnerArray;
    }

    protected void populateCategories() {
        List<String> spinnerArray = getCategories();
        // note that "All" must be included
        spinnerArray.add("All");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, spinnerArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner = (Spinner) findViewById(R.id.categorySpinner);
        categorySpinner.setAdapter(adapter);
        this.categoryShift("All");
        // TODO may want to make this a constant, since db won't have this option available
        
        categorySpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                categoryShift(categorySpinner.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {}
        });
    }

    
    /******************************************************************
     * Adding stuff to the list
     ******************************************************************/
    
    public boolean addGroceryItem(View view) {
        // hides keyboard
        InputMethodManager imm = (InputMethodManager) getBaseContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(itemNameTextbox.getWindowToken(), 0);

        ItemPOJO formedItem = isValidItem(itemNameTextbox.getText().toString(),
                priceTextbox.getText().toString(),
                quantityTextbox.getText().toString(),
                categorySpinner.getSelectedItem().toString());
        if (formedItem != null) {
            addItemsToDisplay(formedItem);
            addItemsToDatabase(formedItem);
            quantityTextbox.setText("");
            priceTextbox.setText("");
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
    
    protected ItemPOJO isValidItem(String name, String price, String quantity, String category) {
        String n = null;
        int q = 0;
        BigDecimal p = null;
        String c = null;
        try {
            n = name;
            p = new BigDecimal(Double.parseDouble(price));
            q = Integer.parseInt(quantity);
            c = category;
            return new ItemPOJO(n, p, q, c);
        } catch (NumberFormatException e) {
            System.out.println(" problem: " + e);
            return null;
        }
    }

    protected void addItemsToDisplay(ItemPOJO item) {
        adapter.add(item);
        itemsView.invalidateViews();
        itemsView.setAdapter(adapter);
    }

    protected void addItemsToDatabase(ItemPOJO item) {
        // TODO implement so item can be added to database
    }

    protected boolean enableAdd() {
        return itemNameTextbox.getText().length() > 0
                && quantityTextbox.getText().length() > 0
                && priceTextbox.getText().length() > 0
                && !categorySpinner.getSelectedItem().toString().equalsIgnoreCase("All");
    }

    protected class AddGroceryWatcher implements TextWatcher {
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            addGroceryButton.setEnabled(enableAdd());
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void afterTextChanged(Editable s) {
            addGroceryButton.setEnabled(enableAdd());
        }
    };
    
    
    /******************************************************************
     * DUPLICATE and get stuff from an existing list
     ******************************************************************/
    
    public final static int GET_ITEM_LIST = 1;
    
    public void duplicateList(View view) {
        Intent i = new Intent(getApplicationContext(), NewGroceryAll.class);
        startActivityForResult(i, GET_ITEM_LIST);
        i.setFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
        i.putExtra("SHOPPING_LIST_ID", -1);
        startActivity(i);
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        System.out.println("Activity Results in EditGrocery");
        if (requestCode == GET_ITEM_LIST) {
            System.out.println(resultCode == RESULT_OK);
            if (resultCode == RESULT_OK) {
                Bundle extras = data.getExtras();
                if(extras != null) {
                    int shoppingListId = extras.getInt("SHOPPING_LIST_ID");
                    System.out.println("List selected ID: " + shoppingListId);
                    // TODO use db to get items in list, add stuff to list
                    /*
                    get the list id from the previous activity
                    for (some data structure) {
                        addItemsToDisplay(itemName, quantity, price, category);
                    }
                     */
                    categoryShift("All");
                } else {
                    System.out.println("error occurred when importing list...");
                }
            }
        }
    }
    
    /******************************************************************
     * UPDATING
     ******************************************************************/
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

        List<String> spin = getCategories();
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
                priceText = pText.getText().toString();
                System.out.println("price is " + priceText);
                quantityText = qText.getText().toString();
                categoryText = cText.getSelectedItem().toString();
                itemNew = isValidItem(nameText, priceText, quantityText, categoryText);
                System.out.println(itemNew + " is here");

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
                // TODO make adapter able to remove stuff
                adapter.remove(item);
                itemsView.invalidateViews();
                itemsView.setAdapter(adapter);
                alertDialog.cancel();
            }
        });

        alertDialog.show();
    }

    
    /******************************************************************
     * FINISH, save anything else in db, go to confirmation page
     ******************************************************************/
    
    public void finishGroceryList(View view) {
        // TODO also include a timestamp for the list
        Date date = new Date();
        System.out.println("List saved at " + date.toString());

        String listName = listTextbox.getText().toString();

        if (listName != null && listName.length() > 0) {
            List<ItemPOJO> list = adapter.getList();
            
            // TODO can remove if needed, for testing purposes
            System.out.println("Print out finished list");
            System.out.println("Name: " + listName);
            for (ItemPOJO item : list) {
                System.out.println("\tItem: " + item);
            }

            // TODO put name to database

            new AlertDialog.Builder(this)
            .setIcon(android.R.drawable.ic_dialog_info)
            .setTitle("Confirmation")
            .setMessage("List "+listName+" saved to database.")
            .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            })
            .show();
        } else {
            // TODO make this better
            String errorMessage = "Not a valid list name.";

            new AlertDialog.Builder(this)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setTitle("Error")
            .setMessage(errorMessage)
            .setNeutralButton("OK", null)
            .show();
        }
    }
    

    /******************************************************************
     * Returning to menu
     ******************************************************************/

    public void returnToMenu(View view) {
        cancelListConfirm();
    }
    
    public void onBackPressed() {
        cancelListConfirm();
    }
    
    protected void cancelListConfirm() {
        // TODO change this, since list is saved for every add, delete, update
        if (adapter.getList().size() > 0) {
            new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Returning to Menu")
                .setMessage("Are you sure you want to return? You will lose all changes.")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,
                            int which) {
                        finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
        } else {
            finish();
        }
    }
}

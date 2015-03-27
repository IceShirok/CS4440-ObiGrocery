package com.example.obigrocery.activities;

import java.util.Date;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.obigrocery.POJO.ItemPOJO;
import com.example.obigrocery.adapters.ItemListAdapterGen;

public class EditGroceryListGen extends ActionBarActivity {

    protected int shoppingListId;

    protected EditText listTextbox;
    protected EditText itemNameTextbox;
    protected EditText quantityTextbox;
    protected Spinner unitSpinner;
    protected ListView itemsView;
    protected Spinner categorySpinner;

    protected Button addGroceryButton;
    protected Button finishGroceryButton;
    protected Button chooseButton;

    protected ItemListAdapterGen adapter;
    
    protected int listId;

    /******************************************************************
     * Instantiation of stuff into the app
     ******************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_grocery_list);
        
        this.listId = generateListId();
        
        addGroceryButton = (Button) findViewById(R.id.addGroceryButton);
        addGroceryButton.setEnabled(false);

        finishGroceryButton = (Button) findViewById(R.id.finishGroceryButton);
        finishGroceryButton.setEnabled(false);

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
        
        unitSpinner = (Spinner) findViewById(R.id.unitSpinner);

        itemsView = (ListView) findViewById(R.id.itemView);
        adapter = new ItemListAdapterGen(this, shoppingListId);
        itemsView.setAdapter(adapter);
        itemsView.setClickable(true);
        itemsView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                editItemAlert(adapter.getItem(position));
            }
        });

        populateCategories();
        populateUnits();
        setTitle();
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

    protected void populateCategories() {
        List<String> spinnerArray = Populator.getCategories(true);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, spinnerArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner = (Spinner) findViewById(R.id.categorySpinner);
        categorySpinner.setAdapter(adapter);
        this.categoryShift(Populator.ALL_CATEGORY);
        
        categorySpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                categoryShift(categorySpinner.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {}
        });
    }

    protected void populateUnits() {
        List<String> spinnerArray = Populator.getUnits();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, spinnerArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        unitSpinner = (Spinner) findViewById(R.id.unitSpinner);
        unitSpinner.setAdapter(adapter);
    }

    
    /******************************************************************
     * Adding stuff to the list
     ******************************************************************/
    
    public boolean addGroceryItem(View view) {
        InputMethodManager imm = (InputMethodManager) getBaseContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(itemNameTextbox.getWindowToken(), 0);

        ItemPOJO formedItem = isValidItem(itemNameTextbox.getText().toString(),
                unitSpinner.getSelectedItem().toString(),
                quantityTextbox.getText().toString(),
                categorySpinner.getSelectedItem().toString());
        if (formedItem != null) {
            addItemsToDisplay(formedItem);
            addItemsToDatabase(formedItem);
            quantityTextbox.setText("");
            itemNameTextbox.setText("");
            return true;
        } else {
            String errorMessage = "Input not valid!";
            new AlertDialog.Builder(this)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setTitle("Error")
            .setMessage(errorMessage)
            .setNeutralButton("OK", null)
            .show();
            return false;
        }
    }
    
    protected ItemPOJO isValidItem(String name, String unit, String quantity, String category) {
        // TODO maybe find a way to sanitize values?
        String n = null;
        int q = 0;
        String u = null;
        String c = null;
        try {
            n = name;
            u = unit;
            q = Integer.parseInt(quantity);
            c = category;
            return new ItemPOJO(n, u, q, c);
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

    protected boolean enableAdd() {
        return itemNameTextbox.getText().length() > 0
                && quantityTextbox.getText().length() > 0
                && !categorySpinner.getSelectedItem().toString().equalsIgnoreCase(Populator.ALL_CATEGORY);
    }

    public class AddGroceryWatcher implements TextWatcher {
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
     * Retrieve from previous data
     ******************************************************************/
    
    public void chooseList(View view) {
        /*
         * TODO go to a screen filled with foods from history, user can add
         */
    }

    public void suggestList(View view) {
        /*
         * TODO allow the app to generate items for the user
         * Can go to screen to show suggested options
         */
    }

//    public final static int GET_ITEM_LIST = 1;
    
//    public void duplicateList(View view) {
//        Intent i = new Intent(getApplicationContext(), NewGroceryAll.class);
//        startActivityForResult(i, GET_ITEM_LIST);
//        i.setFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
//        i.putExtra("SHOPPING_LIST_ID", -1);
//        startActivity(i);
//    }
    
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        System.out.println("Activity Results in EditGrocery");
//        if (requestCode == GET_ITEM_LIST) {
//            System.out.println(resultCode == RESULT_OK);
//            if (resultCode == RESULT_OK) {
//                Bundle extras = data.getExtras();
//                if(extras != null) {
//                    int shoppingListId = extras.getInt("SHOPPING_LIST_ID");
//                    System.out.println("List selected ID: " + shoppingListId);
//                    /*
//                    get the list id from the previous activity
//                    for (some data structure) {
//                        addItemsToDisplay(itemName, quantity, price, category);
//                    }
//                     */
//                    categoryShift(Populator.ALL_CATEGORY);
//                } else {
//                    System.out.println("error occurred when importing list...");
//                }
//            }
//        }
//    }
    
    /******************************************************************
     * UPDATING
     ******************************************************************/
    protected void editItemAlert(final ItemPOJO item) {
        getEditItemDialog(item, false).show();
    }
    
    protected AlertDialog getEditItemDialog(final ItemPOJO item, final boolean canNotePurchase) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("Editing " + item.getName());
        final FrameLayout frameView = new FrameLayout(this);
        builder.setView(frameView);
        
        final AlertDialog alertDialog = builder.create();
        LayoutInflater inflater = alertDialog.getLayoutInflater();
        View dialoglayout = inflater.inflate(R.layout.alert_edit_grocery_one, frameView);
        
        final CheckBox purchasedBox = (CheckBox) dialoglayout.findViewById(R.id.purchasedBox);
        purchasedBox.setChecked(item.isPurchased());
        if(!canNotePurchase) {
            purchasedBox.setVisibility(View.GONE);
        }

        final EditText nText = (EditText) dialoglayout.findViewById(R.id.itemNameTextbox);
        nText.setText(item.getName());

        final EditText qText = (EditText) dialoglayout.findViewById(R.id.quantityTextbox);
        qText.setText(item.getQuantity()+"");

        final Spinner uText = (Spinner) dialoglayout.findViewById(R.id.unitSpinner);
        List<String> units = Populator.getUnits();
        ArrayAdapter<String> uAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, units);
        uAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        uText.setAdapter(uAdapter);
        
        int index = 0;
        for (int i=0; i < uText.getCount(); i++){
            if (uText.getItemAtPosition(i).toString().equals(item.getUnit())){
                index = i;
                break;
            }
        }
        uText.setSelection(index);

        final Spinner cText = (Spinner) dialoglayout.findViewById(R.id.categorySpinner);
        List<String> spin = Populator.getCategories(false);
        ArrayAdapter<String> cAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, spin);
        cAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cText.setAdapter(cAdapter);

        for (int i=0; i < cText.getCount(); i++){
            if (cText.getItemAtPosition(i).toString().equals(item.getCategory())){
                index = i;
                break;
            }
        }
        cText.setSelection(index);
        
        final Button updateButton = (Button) dialoglayout.findViewById(R.id.updateButton);
        final Button deleteButton = (Button) dialoglayout.findViewById(R.id.deleteButton);
        final Button cancelButton = (Button) dialoglayout.findViewById(R.id.cancelButton);
        
        updateButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameText = null;
                String unitText = null;
                String quantityText = null;
                String categoryText = null;
                ItemPOJO itemNew = null;
                
                nameText = nText.getText().toString();
                categoryText = cText.getSelectedItem().toString();
                unitText = uText.getSelectedItem().toString();
                quantityText = qText.getText().toString();
                itemNew = isValidItem(nameText, unitText, quantityText, categoryText);
        
                if(itemNew != null) {
                    categoryShift(categoryText);
                    adapter.add(itemNew);
                    adapter.remove(item);
                    updateItemToDatabase(item, itemNew);
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
                deleteFromDatabase(item);
                itemsView.invalidateViews();
                itemsView.setAdapter(adapter);
                alertDialog.cancel();
            }
        });
        return alertDialog;
    }

    
    /******************************************************************
     * Database-specific stuff
     ******************************************************************/

    protected int generateListId() {
        /*
         * TODO implement so can get the list ID
         * This method should create whatever you need to set up the list
         * i.e. name, date, id, etc.
         * Note that listId is an instance variable set upon creation,
         *  so you can use the id value whenever
         */
        Date date = new Date();
        System.out.println("List saved at " + date.toString());
        return 0;
    }

    protected void addItemsToDatabase(ItemPOJO item) {
        /*
         * TODO implement so item is added to database
         * should be an insert
         * ItemPOJO consists of name, unit, quantity, and category
         */
        System.out.println("***** Adding an item to the database. *****");
        System.out.println(item);
        System.out.println("***** Finished adding an item to the database. *****");
    }

    protected void updateItemToDatabase(ItemPOJO oldItem, ItemPOJO newItem) {
        /*
         * TODO implement so item is updated to database
         * oldItem is the original item in the database
         * newItem is the new information to replace the original item
         */
        System.out.println("***** Updating an item to the database. *****");
        System.out.println(oldItem);
        System.out.println(newItem);
        System.out.println("***** Finished updating an item to the database. *****");
    }

    protected void updateListToDatabase(String name) {
        /*
         * TODO implement so list is named
         */
        System.out.println("***** Updating list name to the database. *****");
        System.out.println(name);
        System.out.println("***** Finished updating list name to the database. *****");
    }

    protected void deleteFromDatabase(ItemPOJO item) {
        /*
         * TODO implement so item is deleted from the database
         */
        System.out.println("***** Deleting an item to the database. *****");
        System.out.println(item);
        System.out.println("***** Finished deleting an item to the database. *****");
    }

    protected void setTitle() {
        /*
         * TODO use database to get the name of the shopping list using shoppingListId
         */
        String title = "Obi Grocery - Create New List ";
        this.setTitle(title);
        listTextbox.setText("Obi Grocery List " + shoppingListId);
    }

    
    /******************************************************************
     * FINISH, save anything else in db, go to confirmation page
     ******************************************************************/
    
    public void finishGroceryList(View view) {
        String listName = listTextbox.getText().toString();

        if (listName != null && listName.length() > 0) {
//            List<ItemPOJO> list = adapter.getList();
//            System.out.println("Print out finished list");
//            System.out.println("Name: " + listName);
//            for (ItemPOJO item : list) {
//                System.out.println("\tItem: " + item);
//            }

            updateListToDatabase(listName);

            new AlertDialog.Builder(this)
            .setIcon(android.R.drawable.ic_dialog_info)
            .setTitle("Confirmation")
            .setMessage("List name "+listName+" saved to database.")
            .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            })
            .show();
        } else {
            String errorMessage = "Enter a valid grocery list name.";

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
        finish();
    }
    
    public void onBackPressed() {
        finish();
    }
    
//    protected void cancelListConfirm() {
//        if (adapter.getList().size() > 0) {
//            new AlertDialog.Builder(this)
//                .setIcon(android.R.drawable.ic_dialog_alert)
//                .setTitle("Returning to Menu")
//                .setMessage("Are you sure you want to return? You will lose all changes.")
//                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog,
//                            int which) {
//                        finish();
//                    }
//                })
//                .setNegativeButton("No", null)
//                .show();
//        } else {
//            finish();
//        }
//    }
}

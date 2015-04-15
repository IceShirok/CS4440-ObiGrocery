package com.example.obigrocery.activities;

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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.obigrocery.adapters.ItemListAdapterGen;
import com.example.obigrocery.db.ListGroceryDAO;
import com.example.obigrocery.db.ProductsDAO;
import com.example.obigrocery.db.ShoppingListDAO;
import com.example.obigrocery.sqlmodel.ListGrocery;
import com.example.obigrocery.sqlmodel.Products;
import com.example.obigrocery.sqlmodel.ShoppingList;

public class EditGroceryListGen extends ActionBarActivity {

    protected long shoppingListId;

    protected EditText listTextbox;
    protected EditText itemNameTextbox;
    protected EditText quantityTextbox;
    protected Spinner unitSpinner;
    protected ListView itemsView;
    protected Spinner categorySpinner;

    protected Button addGroceryButton;
    protected Button finishGroceryButton;
    protected Button chooseButton;
    protected Button suggestButton;

    protected ItemListAdapterGen adapter;
    
    protected ShoppingListDAO shoppingListDb;
    protected ShoppingList shoppingListPOJO;
    protected ProductsDAO productDb;
    protected ListGroceryDAO listGroceryDb;

    /******************************************************************
     * Instantiation of stuff into the app
     ******************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_grocery_list);
        
        shoppingListDb = new ShoppingListDAO(this);
        productDb = new ProductsDAO(this);
        listGroceryDb = new ListGroceryDAO(this);
        
        this.shoppingListId = generateListId();
        
        addGroceryButton = (Button) findViewById(R.id.addGroceryButton);
        addGroceryButton.setEnabled(false);

        finishGroceryButton = (Button) findViewById(R.id.finishGroceryButton);

        chooseButton = (Button) findViewById(R.id.chooseButton);
        chooseButton.setEnabled(false);

        suggestButton = (Button) findViewById(R.id.suggestButton);
        suggestButton.setEnabled(false);

        listTextbox = (EditText) findViewById(R.id.listTextbox);
        listTextbox.setVisibility(View.GONE);
        
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
//        setTitle();
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

        float amount = isValidQuantity(quantityTextbox.getText().toString());
        if (amount > 0) {
            ListGrocery item = addItemsToDatabase(itemNameTextbox.getText().toString(),
                    categorySpinner.getSelectedItem().toString(),
                    amount,
                    unitSpinner.getSelectedItem().toString());
            addItemsToDisplay(item);
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
    
    protected float isValidQuantity(String quantity) {
        try {
            return Integer.parseInt(quantity);
        } catch (NumberFormatException e) {
            System.out.println(" problem: " + e);
            return -1;
        }
    }

    protected void addItemsToDisplay(ListGrocery item) {
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
        Intent i = new Intent(getApplicationContext(), ChooseItemsList.class);
        i.putExtra("SHOPPING_LIST_ID", shoppingListId);
        startActivityForResult(i, CHOOSE_ITEM);
    }

    public void suggestList(View view) {
        /*
         * TODO allow the app to generate items for the user
         * Can go to screen to show suggested options
         */
        Intent i = new Intent(getApplicationContext(), SuggestItemsList.class);
        i.putExtra("SHOPPING_LIST_ID", shoppingListId);
        startActivityForResult(i, SUGGEST_ITEM);
    }
    
    public final static int CHOOSE_ITEM = 0;
    public final static int SUGGEST_ITEM = 1;
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        System.out.println("Activity Results in EditGrocery");
        if (requestCode == CHOOSE_ITEM || requestCode == SUGGEST_ITEM) {
            if (resultCode == RESULT_OK) {
                // not implemented
                /*
                System.out.println("Chose some items from a list");
                Bundle extras = data.getExtras();
                if(extras != null) {
                    int shoppingListId = extras.getInt("SHOPPING_LIST_ID");
                    System.out.println("List selected ID: " + shoppingListId);
                    ArrayList<String> newItems = data.getStringArrayListExtra("result");
                    for(String s : newItems) {
                        String[] split = s.split(",");
                        ItemPOJO item = isValidItem(split[0], split[1], split[2],split[3]);
                        addItemsToDisplay(item);
                        addItemsToDatabase(item);
                    }
                    categoryShift(Populator.ALL_CATEGORY);
                } else {
                    System.out.println("error occurred when importing list...");
                }
                */
            }
        }
    }
    
    /******************************************************************
     * UPDATING
     ******************************************************************/
    protected void editItemAlert(final ListGrocery listGrocery) {
        getEditItemDialog(listGrocery, false).show();
    }
    
    protected AlertDialog getEditItemDialog(final ListGrocery listGrocery, final boolean canNotePurchase) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("Editing " + listGrocery.getProducts().getProductName());
        final FrameLayout frameView = new FrameLayout(this);
        builder.setView(frameView);
        
        final AlertDialog alertDialog = builder.create();
        LayoutInflater inflater = alertDialog.getLayoutInflater();
        View dialoglayout = inflater.inflate(R.layout.alert_edit_grocery_one, frameView);
        
        final CheckBox purchasedBox = (CheckBox) dialoglayout.findViewById(R.id.purchasedBox);
        purchasedBox.setChecked(listGrocery.getIsPurchased() == 1 ? true : false);
        if(!canNotePurchase) {
            purchasedBox.setVisibility(View.GONE);
        }

        final EditText nText = (EditText) dialoglayout.findViewById(R.id.itemNameTextbox);
        nText.setText(listGrocery.getProducts().getProductName());

        final EditText qText = (EditText) dialoglayout.findViewById(R.id.quantityTextbox);
        qText.setText(listGrocery.getAmount()+"");

        final Spinner uText = (Spinner) dialoglayout.findViewById(R.id.unitSpinner);
        List<String> units = Populator.getUnits();
        ArrayAdapter<String> uAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, units);
        uAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        uText.setAdapter(uAdapter);
        
        int index = 0;
        for (int i=0; i < uText.getCount(); i++){
            if (uText.getItemAtPosition(i).toString().equals(listGrocery.getUnits())){
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

        List<String> categories = Populator.getCategories(false);
        for (int i=0; i < cText.getCount(); i++){
            String category = categories.get((int)listGrocery.getProducts().getCategoryId());
            if (cText.getItemAtPosition(i).toString().equals(category)){
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
                String categoryText = cText.getSelectedItem().toString();
                float amount = isValidQuantity(quantityTextbox.getText().toString());
        
                if(amount > 0) {
                    ListGrocery newItem = new ListGrocery(listGrocery.getListId(),
                            listGrocery.getProductID(),
                            amount, 
                            uText.getSelectedItem().toString(),
                            listGrocery.getIsPurchased());
                    newItem.setId(listGrocery.getId());
                    Products product = listGrocery.getProducts();
                    product.setProductName(nText.getText().toString());
                    product.setCategoryId(0);
                    newItem.setProducts(product);
                    newItem.setShoppingList(listGrocery.getShoppingList());
                    
                    categoryShift(categoryText);
                    adapter.remove(listGrocery);
                    adapter.add(newItem);
                    updateItemToDatabase(newItem);
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
                adapter.remove(listGrocery);
                deleteFromDatabase(listGrocery);
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

    protected long generateListId() {
        this.shoppingListPOJO = shoppingListDb.createShoppingLIst();
        return this.shoppingListPOJO.getId();
    }

    protected ListGrocery addItemsToDatabase(String name, String category, float quantity, String unit) {
        System.out.println("***** Adding an item to the database. *****");
        Products product = productDb.createProducts(name, category);
        System.out.println("product id should be: " + product.getId());
        ListGrocery groceryItem = listGroceryDb.createListGrocery(shoppingListId, product.getId(), quantity, unit, 0);
        System.out.println("***** Finished adding an item to the database. *****");
        return groceryItem;
    }

    protected void updateItemToDatabase(ListGrocery newItem) {
        System.out.println("***** Updating an item to the database. *****");
        listGroceryDb.updateListGrocery(newItem.getId(), newItem.getListId(), newItem.getProductID(),
                newItem.getUnits(), newItem.getAmount(), newItem.getIsPurchased());
        System.out.println("***** Finished updating an item to the database. *****");
    }

    protected void deleteFromDatabase(ListGrocery listGrocery) {
        System.out.println("***** Deleting an item to the database. *****");
        listGroceryDb.deleteListGroceryById(listGrocery);
        System.out.println("***** Finished deleting an item to the database. *****");
    }

//    protected void updateListToDatabase(String name) {
//        /*
//         * TODO implement so list is named
//         */
//        System.out.println("***** Updating list name to the database. *****");
//        System.out.println(name);
//        System.out.println("***** Finished updating list name to the database. *****");
//    }

    protected void setTitle() {
        /*
         * TODO use database to get the name of the shopping list using shoppingListId
         */
        String title = "Obi Grocery - Create New List " + shoppingListId;
        this.setTitle(title);
    }

    
    /******************************************************************
     * FINISH, save anything else in db, go to confirmation page
     ******************************************************************/
    
    public void finishGroceryList(View view) {
        /*
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
        */

        new AlertDialog.Builder(this)
        .setIcon(android.R.drawable.ic_dialog_info)
        .setTitle("Confirmation")
        .setMessage("List "+shoppingListId+" saved to database.")
        .setNeutralButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        })
        .show();
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
        new AlertDialog.Builder(this)
        .setIcon(android.R.drawable.ic_dialog_alert)
        .setTitle("Cancel Confirmation")
        .setMessage("Are you sure you want to cancel? You will lose all changes.")
        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                shoppingListDb.deleteShoppingList(shoppingListPOJO);
                finish();
            }
        })
        .setNegativeButton("No", null)
        .show();
    }
}

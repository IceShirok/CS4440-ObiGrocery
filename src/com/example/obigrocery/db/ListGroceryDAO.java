package com.example.obigrocery.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.obigrocery.sqlmodel.*;

public class ListGroceryDAO {
    public static final String TAG = "ListGroceryDAO";
    private Context context;

    // Database fields
    private SQLiteDatabase database;
    private DBTools dbTools;
    private String[] allColumns = { DBTools.COLUMN_LISTGROCERY_LISTGROCERYID,
            DBTools.COLUMN_LISTGROCERY_LISTID,
            DBTools.COLUMN_LISTGROCERY_PRODUCTID,
            DBTools.COLUMN_LISTGROCERY_UNITS,
            DBTools.COLUMN_LISTGROCERY_AMOUNT,
            DBTools.COLUMN_LISTGROCERY_ISPURCHASED };

    public ListGroceryDAO(Context context) {
        this.context = context;
        dbTools = new DBTools(context);
        // open the database
        try {
            open();
        } catch (SQLException e) {
            Log.e(TAG, "SQLException on openning database " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void open() throws SQLException {
        database = dbTools.getWritableDatabase();
    }

    public void close() {
        dbTools.close();
    }

    public ListGrocery createListGrocery(long listId, long productId,
            float amount, String units, int isPurchased) {

        ContentValues values = new ContentValues();
        values.put(DBTools.COLUMN_LISTGROCERY_LISTID, listId);
        values.put(DBTools.COLUMN_LISTGROCERY_PRODUCTID, productId);
        values.put(DBTools.COLUMN_LISTGROCERY_AMOUNT, amount);
        values.put(DBTools.COLUMN_LISTGROCERY_UNITS, units);
        values.put(DBTools.COLUMN_LISTGROCERY_ISPURCHASED, isPurchased);

        long insertId = database
                .insertOrThrow(DBTools.TABLE_LISTGROCERY, null, values);

        Cursor cursor = database.query(DBTools.TABLE_LISTGROCERY, allColumns,
                DBTools.COLUMN_LISTGROCERY_LISTGROCERYID + " = " + insertId,
                null, null, null, null);
        cursor.moveToFirst();
        ListGrocery newListGrocery = cursorToListGrocery(cursor);
        cursor.close();

        return newListGrocery;
    }

    public void deleteListGroceryById(ListGrocery listGrocery) {
        long id = listGrocery.getId();
        database.delete(DBTools.TABLE_LISTGROCERY,
                DBTools.COLUMN_LISTGROCERY_LISTGROCERYID + " = " + id, null);
    }

    public List<ListGrocery> getListGeocerybyListId(long listId) {
        List<ListGrocery> listListGrocery = new ArrayList<ListGrocery>();

        Cursor cursor = database.query(DBTools.TABLE_LISTGROCERY, allColumns,
                DBTools.COLUMN_LISTGROCERY_LISTID + " = ?",
                new String[] { String.valueOf(listId) }, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            ListGrocery listGrocery = cursorToListGrocery(cursor);
            listListGrocery.add(listGrocery);
            cursor.moveToNext();
        }
        cursor.close();
        return listListGrocery;
    }

    public List<ListGrocery> getListGeocerybyProductId(long productId) {
        List<ListGrocery> listListGrocery = new ArrayList<ListGrocery>();

        Cursor cursor = database.query(DBTools.TABLE_LISTGROCERY, allColumns,
                DBTools.COLUMN_LISTGROCERY_PRODUCTID + " = ?",
                new String[] { String.valueOf(productId) }, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            ListGrocery listGrocery = cursorToListGrocery(cursor);
            listListGrocery.add(listGrocery);
            cursor.moveToNext();
        }
        cursor.close();
        return listListGrocery;
    }

    public void deleteListGeocerybyListId(long listId) {
        List<ListGrocery> listedProducts = getListGeocerybyListId(listId);

        if (listedProducts != null && !listedProducts.isEmpty()) {
            for (ListGrocery l : listedProducts) {
                deleteListGroceryById(l);
                // long productId = l.getProductID();
                // database.delete(DBTools.TABLE_LISTGROCERY,
                // DBTools.COLUMN_LISTGROCERY_PRODUCTID
                // + " = "+ productId , null);
            }
        }
    }

    public void deleteListGeocerybyProductId(long productId) {
        List<ListGrocery> listedProducts = getListGeocerybyProductId(productId);

        if (listedProducts != null && !listedProducts.isEmpty()) {
            for (ListGrocery l : listedProducts) {
                deleteListGroceryById(l);
            }
        }
    }

    // for grouping groceries by category for showing up in list
    public List<ListGrocery> getListGroceryByCategory(String categoryName) {
        List<ListGrocery> listGroceries = new ArrayList<ListGrocery>();

        ProductsDAO pdao = new ProductsDAO(context);
        List<Products> products = pdao.getProductsByCategory(categoryName);
        if (products != null && !products.isEmpty()) {
            for (Products p : products) {
                List<ListGrocery> listGrocery = getListGeocerybyProductId(p
                        .getId());
                if (listGrocery != null && !listGrocery.isEmpty()) {
                    for (ListGrocery l : listGrocery) {
                        listGroceries.add(l);
                    }
                }
            }
        }
        return listGroceries;
    }

    // updates a ListGrocery
    public boolean updateListGrocery(long listGroceryId, long listId,
            long productId, String units, float amount, int isPurchased) {
        ContentValues args = new ContentValues();
        args.put(DBTools.COLUMN_LISTGROCERY_LISTID, listId);
        args.put(DBTools.COLUMN_LISTGROCERY_PRODUCTID, productId);
        args.put(DBTools.COLUMN_LISTGROCERY_UNITS, units);
        args.put(DBTools.COLUMN_LISTGROCERY_AMOUNT, amount);
        args.put(DBTools.COLUMN_LISTGROCERY_ISPURCHASED, isPurchased);
        return database.update(DBTools.TABLE_LISTGROCERY, args,
                DBTools.COLUMN_LISTGROCERY_LISTGROCERYID + "=" + listGroceryId,
                null) > 0;
    }

    protected ListGrocery cursorToListGrocery(Cursor cursor) {
        ListGrocery listGrocery = new ListGrocery();

        listGrocery.setId(cursor.getLong(0));
        listGrocery.setListId(cursor.getLong(1));
        listGrocery.setProductID(cursor.getLong(2));
        listGrocery.setUnits(cursor.getString(3));
        listGrocery.setAmount(cursor.getFloat(4));
        listGrocery.setIsPurchased(cursor.getInt(5));

        // FK1- get the ShoppingList by id
        long listId = listGrocery.getListId();
        ShoppingListDAO sdao = new ShoppingListDAO(context);
        ShoppingList shoppingList = sdao.getShoppingListById(listId);
        if (shoppingList != null)
            listGrocery.setShoppingList(shoppingList);

        // FK2- get the Products by id
        long productId = listGrocery.getProductID();
        ProductsDAO pdao = new ProductsDAO(context);
        Products products = pdao.getProductsById(productId);
        if (products != null)
            listGrocery.setProducts(products);

        return listGrocery;
    }

}

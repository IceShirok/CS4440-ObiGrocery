package com.example.obigrocery.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.obigrocery.sqlmodel.Products;

public class ProductsDAO {
    public static final String TAG = "ProductsDAO";
    private Context context;

    // Database fields
    private SQLiteDatabase database;
    private DBTools dbTools;
    private String[] allColumns = { DBTools.COLUMN_PRODUCTS_PRODUCTID,
            DBTools.COLUMN_PRODUCTS_PRODUCTNAME,
            DBTools.COLUMN_PRODUCTS_CATEGORYNAME };

    public ProductsDAO(Context context) {
        this.context = context;
        dbTools = new DBTools(context);

        try {
            open();
        } catch (SQLException e) {
            Log.e(TAG, "SQLException on opening database " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void open() throws SQLException {
        database = dbTools.getWritableDatabase();
    }

    public void close() {
        dbTools.close();
    }

    public Products createProducts(String productName, String categoryName) {
        ContentValues values = new ContentValues();
        values.put(DBTools.COLUMN_PRODUCTS_PRODUCTNAME, productName);
        values.put(DBTools.COLUMN_PRODUCTS_CATEGORYNAME, categoryName);

        long insertId = database.insert(DBTools.TABLE_PRODUCTS, null, values);

        Cursor cursor = database.query(DBTools.TABLE_PRODUCTS, allColumns,
                DBTools.COLUMN_PRODUCTS_PRODUCTID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Products newProducts = cursorToProducts(cursor);
        cursor.close();

        return newProducts;
    }

    public void deleteProduct(Products product) {
        long id = product.getId();
        System.out.println("Product deleted with id: " + id);

        // delete all listGrocery related to this product
        ListGroceryDAO listGroceryDAO = new ListGroceryDAO(context);
        listGroceryDAO.deleteListGeocerybyProductId(id);

        database.delete(DBTools.TABLE_PRODUCTS,
                DBTools.COLUMN_PRODUCTS_PRODUCTID + " = " + id, null);
    }

    public Products getProductsById(long id) {
        Cursor cursor = database.query(DBTools.TABLE_PRODUCTS, allColumns,
                DBTools.COLUMN_PRODUCTS_PRODUCTID + " = ?",
                new String[] { String.valueOf(id) }, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }

        Products products = cursorToProducts(cursor);
        return products;
    }

    public List<Products> getProductsByCategory(String categoryName) {
        List<Products> products = new ArrayList<Products>();

        Cursor cursor = database
                .query(DBTools.TABLE_PRODUCTS, allColumns,
                        DBTools.COLUMN_PRODUCTS_CATEGORYNAME + " = ?",
                        new String[] { String.valueOf(categoryName) }, null,
                        null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Products product = cursorToProducts(cursor);
            products.add(product);
            cursor.moveToNext();
        }
        cursor.close();
        return products;
    }

    // updates a product
    public boolean updateProduct(long productId, String productName,
            String categoryName) {
        ContentValues args = new ContentValues();
        args.put(DBTools.COLUMN_PRODUCTS_PRODUCTNAME, productName);
        args.put(DBTools.COLUMN_PRODUCTS_CATEGORYNAME, categoryName);
        return database.update(DBTools.TABLE_PRODUCTS, args,
                DBTools.COLUMN_PRODUCTS_PRODUCTID + "=" + productId, null) > 0;
    }

    protected Products cursorToProducts(Cursor cursor) {
        Products products = new Products();
        products.setId(cursor.getLong(0));
        products.setProductName(cursor.getString(1));
        products.setCategory(cursor.getString(2));

        return products;
    }

    public List<Products> getAllGroceries() {
        List<Products> productList = new ArrayList<Products>();

        Cursor cursor = database.query(DBTools.TABLE_PRODUCTS, allColumns,
                null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Products product = cursorToProducts(cursor);
            productList.add(product);
            cursor.moveToNext();
        }
        cursor.close();
        return productList;
    }

}

package com.example.obigrocery.db;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.obigrocery.sqlmodel.*;

public class ShoppingListDAO {
	public static final String TAG = "ShoppingListDAO";
	private Context context;
	
	// Database fields
	private SQLiteDatabase database;
	private DBTools dbTools;
	private String[] allColumns = {DBTools.COLUMN_SHOPPINGLIST_LISTID,
			DBTools.COLUMN_SHOPPINGLIST_DATETIME};
	
	public ShoppingListDAO(Context context){
		this.context = context;
		dbTools = new DBTools(context);
		
		try {
			open();
		} catch (SQLException e){
			Log.e(TAG, "SQLException on opening database " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void open() throws SQLException {
		database = dbTools.getWritableDatabase();
	}
	
	public void close(){
		dbTools.close();
	}
	
	public ShoppingList createShoppingLIst() {
		ContentValues values = new ContentValues();
		Timestamp ts = new Timestamp(System.currentTimeMillis());
		values.put(DBTools.COLUMN_SHOPPINGLIST_DATETIME, ts.toString());
			 
		long insertId = database.insert(DBTools.TABLE_SHOPPINGLIST, null, values);
			 
		Cursor cursor = database.query(DBTools.TABLE_SHOPPINGLIST, allColumns,
				DBTools.COLUMN_SHOPPINGLIST_LISTID + " = " + insertId, null, null,
				null, null);
		cursor.moveToFirst();
		ShoppingList newShoppingList = cursorToShoppingList(cursor);
		cursor.close();
			 
		return newShoppingList;
	}
	
	public void deleteShoppingList(ShoppingList shoppingList) {
		 long id = shoppingList.getId();		 
		 System.out.println("Shopping list deleted with id: " + id);
	 
		 //delete all listGrocery related to this shoppingList
		 ListGroceryDAO listGroceryDAO = new ListGroceryDAO(context);
		 listGroceryDAO.deleteListGeocerybyListId(id);
		 
		 database.delete(DBTools.TABLE_SHOPPINGLIST, DBTools.COLUMN_SHOPPINGLIST_LISTID
				 + " = " + id, null);
	}
	

	public List<ShoppingList> getAllShoppingLists(){
	  	 List<ShoppingList> shoppingLists = new ArrayList<ShoppingList>();
	  	 
		 Cursor cursor = database.query(DBTools.TABLE_SHOPPINGLIST,
				 allColumns, null, null, null, null, null);
		 
		 cursor.moveToFirst();
		 while (!cursor.isAfterLast()) {
			 ShoppingList shoppingList = cursorToShoppingList(cursor);
			 shoppingLists.add(shoppingList);
			 cursor.moveToNext();
		 }
		 // close the cursor
		 cursor.close();
		 return shoppingLists;
	}	
	 	 
	  
	public ShoppingList getShoppingListById(long id) {
		 Cursor cursor = database.query(DBTools.TABLE_SHOPPINGLIST, allColumns,
		 DBTools.COLUMN_SHOPPINGLIST_LISTID + " = ?",
		 new String[] { String.valueOf(id) }, null, null, null);
		 if (cursor != null) {
			 cursor.moveToFirst();
		 }
		 
		 ShoppingList shoppingList = cursorToShoppingList(cursor);
		 return shoppingList;
	 }	
	 
	 
	protected ShoppingList cursorToShoppingList(Cursor cursor) {
		ShoppingList shoppingList = new ShoppingList();
		try {
    		shoppingList.setId(cursor.getLong(0));
    		shoppingList.setDateTime(cursor.getString(1));
		} catch(Exception e) {
		    shoppingList = null;
		    System.out.println("issues with adding stuff :P");
		}
		return shoppingList;
	}

}

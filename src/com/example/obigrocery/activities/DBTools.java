package com.example.obigrocery.activities;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

//DBTools dbTools = new DBTools(this);
//dbTools.insertContact(queryValuesMap);
//ArrayList<HashMap<String, String>> contactList =  dbTools.getAllContacts();

public class DBTools  extends SQLiteOpenHelper {

	// Context : provides access to application-specific resources and classes
	
	public DBTools(Context applicationcontext) {		
		// Call use the database or to create it		
		super(applicationcontext, "groceryapp.db", null, 1);
     
	}
	
	// onCreate is called the first time the database is created
	@Override
	public void onCreate(SQLiteDatabase database) {
		// create four tables the first time database is created
		String query_ShoppingList = "CREATE TABLE shoppingList(list_id INTEGER PRIMARY KEY AUTOINCREMENT , dateTime TEXT NOT NULL)";
		String query_Category = "CREATE TABLE categoryList(category_id INTEGER PRIMARY KEY AUTOINCREMENT , categoryName TEXT NOT NULL)";
		String query_Product = "CREATE TABLE products(product_id INTEGER PRIMARY KEY AUTOINCREMENT , productName TEXT UNIQUE NOT NULL, categoryId INTEGER NOT NULL, FOREIGN KEY(categoryId) REFERENCES categoryList(category_id))";
		String query_listGrocery = "CREATE TABLE list_Grocery (list_id INTEGER NOT NULL, product_id INTEGER NOT NULL, amount REAL NOT NULL, units TEXT NOT NULL, isPurchased INTEGER NOT NULL, PRIMARY KEY(list_id, product_id), FOREIGN KEY(list_id) REFERENCES shoppingList(list_id), FOREIGN KEY(product_id) REFERENCES products(product_id))";
			
		database.execSQL(query_ShoppingList);	
		database.execSQL(query_Category);
		database.execSQL(query_Product);
		database.execSQL(query_listGrocery);
	}
	
	//onUpgrade called if the database version is increased in the application code
	//allows to update an existing database schema or to drop the existing database and recreate it via onCreate() 
	@Override
	public void onUpgrade(SQLiteDatabase database, int version_old, int current_version) {
		String query_ShoppingList = "DROP TABLE IF EXISTS shoppingList";
		String query_Category = "DROP TABLE IF EXISTS categoryList";
		String query_Product = "DROP TABLE IF EXISTS products";
		String query_listGrocery = "DROP TABLE IF EXISTS list_Grocery";
		
		
		// Executes the query provided as long as the query isn't a select
		// or if the query doesn't return any data		
		database.execSQL(query_ShoppingList);
		database.execSQL(query_Category);
		database.execSQL(query_Product);
		database.execSQL(query_listGrocery);
		
		onCreate(database);
	}

	
	public void insertItem(HashMap<String, String> queryValues){
		// Open db for reading and writing		
		SQLiteDatabase database = this.getWritableDatabase();
		
		
		// Stores key value pairs (the column name and the data) 
		// for categoryList table
		// check if same category exist 
		//if (!IsDataAlreadyInDB(database, "categoryList", "categoryName", queryValues.get("categoryName"))){
		ContentValues catValues = new ContentValues();		
		catValues.put("categoryName", queryValues.get("categoryName"));
		
		// Inserts the data in the form of ContentValues into categoryList table
		database.insert("categoryList", null, catValues);
		//}
		//get category_id
		
		
		// for products table
		// check if same product exist 
		// if (!IsDataAlreadyInDB(database, "products", "productName", queryValues.get("productName"))){			
		ContentValues productValues = new ContentValues();		
		productValues.put("productName", queryValues.get("productName"));
		productValues.put("categoryId", queryValues.get("productName"));
		
		//set category_id
		
		// Inserts the data in the form of ContentValues into products table
		database.insert("products", null, productValues);
		//}
		//get product_id
		
		
		// for list_Grocery table		
		ContentValues listValues = new ContentValues();		
		listValues.put("amount", queryValues.get("amount"));
		listValues.put("units", queryValues.get("units"));
		//list_id
		//set product_id	
		
		// Inserts the data in the form of ContentValues into list_Grocery table			
		database.insert("list_Grocery", null, listValues);
		
		
		// Release the reference to the SQLiteDatabase object		
		database.close();
	}

	/*
	public void insertShoppingList(HashMap<String, String> queryValues) {
		// Open a database for reading and writing		
		SQLiteDatabase database = this.getWritableDatabase();
		
		// Stores key value pairs being the column name and the data		
		ContentValues values = new ContentValues();
		
		values.put("Date_Time", queryValues.get("Date_Time"));
		
		// Inserts the data in the form of ContentValues into the
		// table name provided		
		database.insert("shopping_list", null, values);
		
		// Release the reference to the SQLiteDatabase object		
		database.close();
	}

	
		
	public void insertContact(HashMap<String, String> queryValues) {
		
		// Open a database for reading and writing
		
		SQLiteDatabase database = this.getWritableDatabase();
		
		// Stores key value pairs being the column name and the data
		// ContentValues data type is needed because the database
		// requires its data type to be passed
		
		ContentValues values = new ContentValues();
		
		values.put("firstName", queryValues.get("firstName"));
		values.put("lastName", queryValues.get("lastName"));
		values.put("phoneNumber", queryValues.get("phoneNumber"));
		values.put("emailAddress", queryValues.get("emailAddress"));
		values.put("homeAddress", queryValues.get("homeAddress"));
		
		// Inserts the data in the form of ContentValues into the
		// table name provided
		
		database.insert("contacts", null, values);
		
		// Release the reference to the SQLiteDatabase object
		
		database.close();
	}
	
	public int updateContact(HashMap<String, String> queryValues) {
		
		// Open a database for reading and writing
		
		SQLiteDatabase database = this.getWritableDatabase();	
		
		// Stores key value pairs being the column name and the data
		
	    ContentValues values = new ContentValues();
	    
	    values.put("firstName", queryValues.get("firstName"));
	    values.put("lastName", queryValues.get("lastName"));
		values.put("phoneNumber", queryValues.get("phoneNumber"));
		values.put("emailAddress", queryValues.get("emailAddress"));
		values.put("homeAddress", queryValues.get("homeAddress"));
	    
		// update(TableName, ContentValueForTable, WhereClause, ArgumentForWhereClause)
		
	    return database.update("contacts", values, "contactId" + " = ?", new String[] { queryValues.get("contactId") });
	}
	
	// Used to delete a contact with the matching contactId
	
	public void deleteContact(String id) {

		// Open a database for reading and writing
		
		SQLiteDatabase database = this.getWritableDatabase();
		
		String deleteQuery = "DELETE FROM  contacts where contactId='"+ id +"'";
		
		// Executes the query provided as long as the query isn't a select
		// or if the query doesn't return any data
		
		database.execSQL(deleteQuery);
	}
	
	public ArrayList<HashMap<String, String>> getAllContacts() {
		
		// ArrayList that contains every row in the database
		// and each row key / value stored in a HashMap
		
		ArrayList<HashMap<String, String>> contactArrayList;
		
		contactArrayList = new ArrayList<HashMap<String, String>>();
		
		String selectQuery = "SELECT  * FROM contacts ORDER BY lastName";
		
		// Open a database for reading and writing
		
	    SQLiteDatabase database = this.getWritableDatabase();
	    
	    // Cursor provides read and write access for the 
	    // data returned from a database query
	    
	    // rawQuery executes the query and returns the result as a Cursor
	    
	    Cursor cursor = database.rawQuery(selectQuery, null);	
	    
	    // Move to the first row
	    
	    if (cursor.moveToFirst()) {
	        do {
	        	HashMap<String, String> contactMap = new HashMap<String, String>();
	        	
	        	// Store the key / value pairs in a HashMap
	        	// Access the Cursor data by index that is in the same order
	        	// as used when creating the table
	        	
	        	contactMap.put("contactId", cursor.getString(0));
	        	contactMap.put("firstName", cursor.getString(1));
	        	contactMap.put("lastName", cursor.getString(2));
	        	contactMap.put("phoneNumber", cursor.getString(3));
	        	contactMap.put("emailAddress", cursor.getString(4));
	        	contactMap.put("homeAddress", cursor.getString(5));
	        	
	        	contactArrayList.add(contactMap);
	        } while (cursor.moveToNext()); // Move Cursor to the next row
	    }
	 
	    // return contact list
	    return contactArrayList;
	}
	
	public HashMap<String, String> getContactInfo(String id) {
		HashMap<String, String> contactMap = new HashMap<String, String>();
		
		// Open a database for reading only
		
		SQLiteDatabase database = this.getReadableDatabase();
		
		String selectQuery = "SELECT * FROM contacts where contactId='"+id+"'";
		
		// rawQuery executes the query and returns the result as a Cursor
		
		Cursor cursor = database.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
	        do {
					
	        	contactMap.put("firstName", cursor.getString(1));
	        	contactMap.put("lastName", cursor.getString(2));
	        	contactMap.put("phoneNumber", cursor.getString(3));
	        	contactMap.put("emailAddress", cursor.getString(4));
	        	contactMap.put("homeAddress", cursor.getString(5));
				   
	        } while (cursor.moveToNext());
	    }				    
	return contactMap;
	}

	*/	
	
	public static boolean IsDataAlreadyInDB(SQLiteDatabase database, String TableName,
	        String dbfield, String fieldValue) {
		
	    String Query = "Select * from " + TableName + " where " + dbfield + " = " + fieldValue;
	    Cursor cursor = database.rawQuery(Query, null);
	        if(cursor.getCount() <= 0){
	            return false;
	        }
	    return true;
	}
}

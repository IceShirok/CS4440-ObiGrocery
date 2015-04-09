package com.example.obigrocery.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

//DBTools dbTools = new DBTools(this);
//dbTools.insertContact(queryValuesMap);
//ArrayList<HashMap<String, String>> contactList =  dbTools.getAllContacts();

public class DBTools  extends SQLiteOpenHelper {

	public static final String TAG = "DBTools";
	
	//columns of the shoppingList table
	public static final String TABLE_SHOPPINGLIST = "shoppingList";
	public static final String COLUMN_SHOPPINGLIST_LISTID = "_id";
	public static final String COLUMN_SHOPPINGLIST_DATETIME = "dateTime";
	
	//columns of the products table
	public static final String TABLE_PRODUCTS = "products";
	public static final String COLUMN_PRODUCTS_PRODUCTID = "_id";
	public static final String COLUMN_PRODUCTS_PRODUCTNAME = "productName";
	public static final String COLUMN_PRODUCTS_CATEGORYNAME = "categoryName";
	
	//columns of the list_Grocery table
	public static final String TABLE_LISTGROCERY = "list_Grocery";
	public static final String COLUMN_LISTGROCERY_LISTGROCERYID= "_id";
	public static final String COLUMN_LISTGROCERY_LISTID = "listId";
	public static final String COLUMN_LISTGROCERY_PRODUCTID = "productId";
	public static final String COLUMN_LISTGROCERY_AMOUNT = "amount";
	public static final String COLUMN_LISTGROCERY_UNITS = "units";
	public static final String COLUMN_LISTGROCERY_ISPURCHASED = "isPurchased";
	
	
	public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "groceryapp.db";
    
    //SQL statement for creating shoppingList table
    private static final String SQL_CREATE_TABLE_SHOPPINGLIST = "CREATE TABLE " + TABLE_SHOPPINGLIST + "("
    		+ COLUMN_SHOPPINGLIST_LISTID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
    		+ COLUMN_SHOPPINGLIST_DATETIME + " TEXT NOT NULL);";
    
    //SQL statement for creating products table
    private static final String SQL_CREATE_TABLE_PRODUCTS = "CREATE TABLE " + TABLE_PRODUCTS + "("
    		+ COLUMN_PRODUCTS_PRODUCTID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
    		+ COLUMN_PRODUCTS_PRODUCTNAME + " TEXT UNIQUE NOT NULL, "
    		+ COLUMN_PRODUCTS_CATEGORYNAME + " TEXT NOT NULL);";
    
    
    //SQL statement for creating list_Grocery table
    private static final String SQL_CREATE_TABLE_LISTGROCERY = "CREATE TABLE " + TABLE_LISTGROCERY + "("
    		+ COLUMN_LISTGROCERY_LISTGROCERYID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
    		+ COLUMN_LISTGROCERY_LISTID + " INTEGER NOT NULL, "
    		+ COLUMN_LISTGROCERY_PRODUCTID + " INTEGER NOT NULL, "
    		+ COLUMN_LISTGROCERY_AMOUNT + " REAL NOT NULL, "
    		+ COLUMN_LISTGROCERY_UNITS + " TEXT NOT NULL, "
    		+ COLUMN_LISTGROCERY_ISPURCHASED + " INTEGER NOT NULL, "  
    		+ "FOREIGN KEY(" + COLUMN_LISTGROCERY_LISTID + ") REFERENCES " + TABLE_SHOPPINGLIST 
    		+ "(" + COLUMN_SHOPPINGLIST_LISTID +"), "
    		+ "FOREIGN KEY(" + COLUMN_LISTGROCERY_PRODUCTID + ") REFERENCES " + TABLE_PRODUCTS
    		+ "(" + COLUMN_PRODUCTS_PRODUCTID + "));";
    		
	public DBTools(Context applicationContext) {		
		// Call use the database or to create it		
		super(applicationContext, DATABASE_NAME , null, DATABASE_VERSION);     
	}
	
	// onCreate is called the first time the database is created
	@Override
	public void onCreate(SQLiteDatabase database) {
		// create four tables the first time database is created
		database.execSQL(SQL_CREATE_TABLE_SHOPPINGLIST);	
		database.execSQL(SQL_CREATE_TABLE_PRODUCTS);
		database.execSQL(SQL_CREATE_TABLE_LISTGROCERY);
	}
	
	//onUpgrade called if the database version is increased in the application code
	//allows to update an existing database schema or to drop the existing database and recreate it via onCreate() 
	@Override
	public void onUpgrade(SQLiteDatabase database, int version_old, int current_version) {
		
		Log.w(TAG, "Upgrading the database from version " + version_old + " to " + current_version);
	
		// Executes the query provided 		
		database.execSQL("DROP TABLE IF EXISTS " + TABLE_SHOPPINGLIST);
		database.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
		database.execSQL("DROP TABLE IF EXISTS " + TABLE_LISTGROCERY);
		
		onCreate(database);
	}
	
	public DBTools(Context context, String name, CursorFactory factory, int version){
		super(context, DATABASE_NAME , factory, DATABASE_VERSION);
	}

///////////////////////////////////////////////////////////////////////////////
	/*
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
		//get category_id
		long newRowId_category;
		newRowId_category = database.insert("categoryList", null, catValues);
		//}
		
		
		
		// for products table
		// check if same product exist 
		// if (!IsDataAlreadyInDB(database, "products", "productName", queryValues.get("productName"))){			
		ContentValues productValues = new ContentValues();		
		productValues.put("productName", queryValues.get("productName"));
		productValues.put("categoryId", newRowId_category);
		
		//set category_id
		
		// Inserts the data in the form of ContentValues into products table
		//get product_id
		long newRowId_product;
		newRowId_product = database.insert("products", null, productValues);
		//}
		
		
		
		// for list_Grocery table		
		ContentValues listValues = new ContentValues();	
		//listValues.put("list_id", list_ID);
		listValues.put("product_id", newRowId_product);
		listValues.put("amount", queryValues.get("amount"));
		listValues.put("units", queryValues.get("units"));
			
		
		// Inserts the data in the form of ContentValues into list_Grocery table			
		database.insert("list_Grocery", null, listValues);
		
		
		// Release the reference to the SQLiteDatabase object		
		database.close();
	}

	
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
		
	    String Query = "SELECT * FROM " + TableName + " WHERE " + dbfield + " = " + fieldValue;
	    Cursor cursor = database.rawQuery(Query, null);
	        if(cursor.getCount() <= 0){
	            return false;
	        }
	    return true;
	}
}

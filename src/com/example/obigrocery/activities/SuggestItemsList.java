package com.example.obigrocery.activities;

import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import android.os.AsyncTask;
import android.widget.ListView;
import android.widget.TextView;

import com.example.obigrocery.POJO.ItemPOJO;
import com.example.obigrocery.adapters.ItemListAdapterSelect;

public class SuggestItemsList extends ChooseItemsList {  

    protected void setTitle() {
        /*
         * TODO use database to get the name of the shopping list using shoppingListId
         */
        String title = "Obi Grocery - Suggest Items " + shoppingListId;
        this.setTitle(title);
    }

    protected void setInstructions() {
        TextView instructionText = (TextView) findViewById(R.id.instructionText);
        instructionText.setText("The app has some items to suggest."
                + " Select the items you want to import to the list.");
    }

    protected void populateList() {
        adapter = new ItemListAdapterSelect(this, shoppingListId);
        /*
         * TODO use database to populate
         * Use shoppingListId to retrieve
         * Want to have unique/distinct items here from all history, or part of it
         * 
         * Unlike the choose option, this java file should process what sort of
         * items to suggest
         */

        itemsView = (ListView) findViewById(R.id.itemView);
        new PopulateListTask().execute();
    }
    
    private class PopulateListTask extends AsyncTask<Void, Void, List<ItemPOJO>> {
        @Override
        protected List<ItemPOJO> doInBackground(Void... params) {
            Connection connection;
            List<ItemPOJO> items = new ArrayList<>();
            try {
                connection = DatabaseConnector.getConnection();
                Statement stmt = connection.createStatement();
                stmt.executeUpdate("DROP TABLE IF EXISTS food_table");
                stmt.executeUpdate("CREATE TABLE food_table (food integer)");
                stmt.executeUpdate("INSERT INTO food_table VALUES (1)");
                stmt.executeUpdate("INSERT INTO food_table VALUES (2)");
                stmt.executeUpdate("INSERT INTO food_table VALUES (3)");
                ResultSet rs = stmt.executeQuery("SELECT food FROM food_table");
                while (rs.next()) {
                    System.out.println("Read from DB: " + rs.getInt("food"));
                    items.add(new ItemPOJO("Bread " + rs.getInt("food"), "oz", 1, "Baked Goods"));
                    items.add(new ItemPOJO("Meat " + rs.getInt("food"), "oz", 1, "Meats"));
                    items.add(new ItemPOJO("Dairy " + rs.getInt("food"), "oz", 1, "Dairy"));
                    items.add(new ItemPOJO("Vegetables " + rs.getInt("food"), "oz", 1, "Dairy"));
                }
                stmt.executeUpdate("DROP TABLE IF EXISTS food_table");
            } catch (URISyntaxException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            return items;
        }

        @Override
        protected void onPostExecute(List<ItemPOJO> result) {
            for(ItemPOJO item : result) {
                adapter.add(item);
            }
            itemsView.setAdapter(adapter);
        }

        @Override
        protected void onPreExecute() {}

        @Override
        protected void onProgressUpdate(Void... values) {}
    }

}

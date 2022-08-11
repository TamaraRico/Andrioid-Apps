package com.example.starbuzz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class TopLevelActivity extends AppCompatActivity {

    private SQLiteDatabase db;
    private Cursor favoriteDrinkCursor, favoriteFoodCursor, favoriteStoreCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_level);
        setupOptionsListView();
        setupFavoritesDrinksListView();
        setupFavoritesFoodListView();
        setupFavoritesStoresListView();
    }

    private void setupOptionsListView(){
        //Create an OnItemClickListener
        AdapterView.OnItemClickListener itemClickListener=
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> listView,
                                            View v, int position, long id) {
                        if(position == 0){
                            Intent intent = new Intent(TopLevelActivity.this,
                                    DrinkCategoryActivity.class);
                            startActivity(intent);
                        }
                        else  if(position == 1){
                            Intent intent = new Intent(TopLevelActivity.this,
                                    FoodCategoryActivity.class);
                            startActivity(intent);
                        }
                        else {
                            Intent intent = new Intent(TopLevelActivity.this,
                                    StoreCategoryActivity.class);
                            startActivity(intent);
                        }
                    }
                };
        ListView listView = (ListView) findViewById(R.id.list_options);
        listView.setOnItemClickListener(itemClickListener);
    }

    private void setupFavoritesDrinksListView(){
        ListView listFavorites = (ListView) findViewById(R.id.list_favorites);
        try{
            SQLiteOpenHelper starbuzzDatabaseHelper = new StarbuzzDatabaseHelper(this);
            db = starbuzzDatabaseHelper.getReadableDatabase();
            favoriteDrinkCursor = db.query("DRINK",
                    new String[] {"_id", "NAME"},
                    "FAVORITE = 1",
                    null, null, null, null);
            CursorAdapter favoriteAdapter = new SimpleCursorAdapter(TopLevelActivity.this,
                    android.R.layout.simple_list_item_1,
                    favoriteDrinkCursor,
                    new String[] {"NAME"},
                    new int[] {android.R.id.text1}, 0);
            listFavorites.setAdapter(favoriteAdapter);
        } catch(SQLiteException e){
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }

        listFavorites.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> listView, View v, int position, long id){
                Intent intent = new Intent(TopLevelActivity.this, DrinkActivity.class);
                intent.putExtra(DrinkActivity.EXTRA_DRINKID, (int)id);
                startActivity(intent);
            }
        });
    }

    private void setupFavoritesFoodListView(){
        ListView listFavorites = (ListView) findViewById(R.id.list_favorites);
        try{
            SQLiteOpenHelper starbuzzDatabaseHelper = new StarbuzzDatabaseHelper(this);
            db = starbuzzDatabaseHelper.getReadableDatabase();
            favoriteFoodCursor = db.query("FOOD",
                    new String[] {"_id", "NAME"},
                    "FAVORITE = 1",
                    null, null, null, null);
            CursorAdapter favoriteAdapter = new SimpleCursorAdapter(TopLevelActivity.this,
                    android.R.layout.simple_list_item_1,
                    favoriteFoodCursor,
                    new String[] {"NAME"},
                    new int[] {android.R.id.text1}, 0);
            listFavorites.setAdapter(favoriteAdapter);
        } catch(SQLiteException e){
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }

        listFavorites.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> listView, View v, int position, long id){
                Intent intent = new Intent(TopLevelActivity.this, FoodActivity.class);
                intent.putExtra(FoodActivity.EXTRA_FOODID, (int)id);
                startActivity(intent);
            }
        });
    }

    private void setupFavoritesStoresListView(){
        ListView listFavorites = (ListView) findViewById(R.id.list_favorites);
        try{
            SQLiteOpenHelper starbuzzDatabaseHelper = new StarbuzzDatabaseHelper(this);
            db = starbuzzDatabaseHelper.getReadableDatabase();
            favoriteStoreCursor = db.query("STORE",
                    new String[] {"_id", "NAME"},
                    "FAVORITE = 1",
                    null, null, null, null);
            CursorAdapter favoriteAdapter = new SimpleCursorAdapter(TopLevelActivity.this,
                    android.R.layout.simple_list_item_1,
                    favoriteStoreCursor,
                    new String[] {"NAME"},
                    new int[] {android.R.id.text1}, 0);
            listFavorites.setAdapter(favoriteAdapter);
        } catch(SQLiteException e){
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }

        listFavorites.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> listView, View v, int position, long id){
                Intent intent = new Intent(TopLevelActivity.this, StoreActivity.class);
                intent.putExtra(StoreActivity.EXTRA_STOREID, (int)id);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onRestart() {
        super.onRestart();
        Cursor newDrinkCursor = db.query("DRINK",
                new String[]{"_id", "NAME"},
                "FAVORITE = 1",
                null, null, null, null);
        ListView listFavoritesDrinks = (ListView) findViewById(R.id.list_favorites);
        CursorAdapter adapterDrink = (CursorAdapter) listFavoritesDrinks.getAdapter();
        adapterDrink.changeCursor(newDrinkCursor);
        favoriteDrinkCursor = newDrinkCursor;

        Cursor newFoodCursor = db.query("FOOD",
                new String[]{"_id", "NAME"},
                "FAVORITE = 1",
                null, null, null, null);
        ListView listFavoriteFood = (ListView) findViewById(R.id.list_favorites);
        CursorAdapter adapter = (CursorAdapter) listFavoriteFood.getAdapter();
        adapter.changeCursor(newFoodCursor);
        favoriteFoodCursor = newFoodCursor;

        Cursor newStoreCursor = db.query("STORE",
                new String[]{"_id", "NAME"},
                "FAVORITE = 1",
                null, null, null, null);
        ListView listFavoriteStore = (ListView) findViewById(R.id.list_favorites);
        CursorAdapter adapterStore = (CursorAdapter) listFavoriteStore.getAdapter();
        adapter.changeCursor(newStoreCursor);
        favoriteStoreCursor = newStoreCursor;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        favoriteDrinkCursor.close();
        favoriteFoodCursor.close();
        favoriteStoreCursor.close();
        db.close();
    }
}
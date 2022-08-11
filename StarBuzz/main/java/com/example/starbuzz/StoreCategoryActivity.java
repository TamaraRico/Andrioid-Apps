package com.example.starbuzz;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class StoreCategoryActivity extends AppCompatActivity {

    private SQLiteDatabase db;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_category);

        ListView listStores = (ListView) findViewById(R.id.list_stores);
        SQLiteOpenHelper starbuzzDatabase = new StarbuzzDatabaseHelper(this);

        try{
            db = starbuzzDatabase.getReadableDatabase();
            cursor = db.query("STORE",
                    new String[] {"_id", "NAME"},
                    null, null, null, null, null);
            SimpleCursorAdapter listAdapter = new SimpleCursorAdapter(this,
                    android.R.layout.simple_list_item_1,
                    cursor,
                    new String[] {"NAME"},
                    new int[] {android.R.id.text1}, 0);
            listStores.setAdapter(listAdapter);
        } catch(SQLiteException e){
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }


        AdapterView.OnItemClickListener itemClickListener =
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> listStores, View itView, int position, long id) {
                        //pass the drink the user clicks on to DrinkActivity
                        Intent intent = new Intent(StoreCategoryActivity.this,
                                StoreActivity.class);
                        intent.putExtra(StoreActivity.EXTRA_STOREID, (int)id);
                        startActivity(intent);
                    }
                };
        listStores.setOnItemClickListener(itemClickListener);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        cursor.close();
        db.close();
    }
}

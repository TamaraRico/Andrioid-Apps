package com.example.starbuzz;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class FoodActivity  extends AppCompatActivity {

    public static final String EXTRA_FOODID = "foodID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        int foodId = (Integer) getIntent().getExtras().get(EXTRA_FOODID);

        SQLiteOpenHelper starbuzzDatabaseHelper = new StarbuzzDatabaseHelper(this);

        try {
            SQLiteDatabase db = starbuzzDatabaseHelper.getReadableDatabase();
            Cursor cursor = db.query("DRINK", new String []{"NAME", "DESCRIPTION", "IMAGE_RESOURCE_ID", "FAVORITE"},
                    "_id = ?",
                    new String [] {Integer.toString(foodId)},
                    null, null, null);
            if(cursor.moveToFirst()) {
                String nameText = cursor.getString(0);
                String descriptionText = cursor.getString(1);
                int photoId = cursor.getInt(2);
                boolean isFavorite = (cursor.getInt(3) == 1);

                CheckBox favorite = (CheckBox) findViewById(R.id.favorite);
                favorite.setChecked(isFavorite);
                TextView name = (TextView) findViewById(R.id.description);
                name.setText(nameText);
                TextView description = (TextView) findViewById(R.id.description);
                description.setText(descriptionText);
                ImageView photo = (ImageView) findViewById(R.id.photo);
                photo.setImageResource(photoId);
                photo.setContentDescription(nameText);
            }
            cursor.close();
            db.close();
        }catch(SQLiteException e) {
            Toast toast = Toast.makeText(this,
                    "Database unavailable",
                    Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void onFavoriteClicked(View view){
        int foodId = (Integer) getIntent().getExtras().get(EXTRA_FOODID);

        CheckBox favorite = (CheckBox) findViewById(R.id.favorite);
        ContentValues foodValues = new ContentValues();
        foodValues.put("FAVORITE", favorite.isChecked());

        SQLiteOpenHelper starbuzzDatabaseHelper = new StarbuzzDatabaseHelper(this);
        try{
            SQLiteDatabase db = starbuzzDatabaseHelper.getWritableDatabase();
            db.update("FOOD", foodValues,
                    "_id = ?",
                    new String[] {Integer.toString(foodId)});
            db.close();
        } catch (SQLiteException e){
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    private class UpdateFoodTask extends AsyncTask<Integer, Void, Boolean> {
        private ContentValues foodValues;

        protected void onPreExecute(){
            CheckBox favorite = (CheckBox) findViewById(R.id.favorite);
            foodValues = new ContentValues();
            foodValues.put("FAVORITE", favorite.isChecked());
        }

        protected Boolean doInBackground(Integer... food){
            int foodId = food[0];
            SQLiteOpenHelper starbuzzDatabaseHelper =
                    new StarbuzzDatabaseHelper(FoodActivity.this);
            try{
                SQLiteDatabase db = starbuzzDatabaseHelper.getWritableDatabase();
                db.update("FOOD", foodValues,
                        "_id = ?", new String[] {Integer.toString(foodId)});
                db.close();
                return true;
            } catch (SQLiteException e){
                return false;
            }
        }

        protected void onPostExecute(Boolean success){
            if(!success){
                Toast toast = Toast.makeText(FoodActivity.this,
                        "Database unavailable", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }
}

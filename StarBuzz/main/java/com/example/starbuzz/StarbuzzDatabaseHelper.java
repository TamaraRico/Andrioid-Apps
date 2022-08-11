package com.example.starbuzz;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;

class StarbuzzDatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "starbuzz";
    private static final int DB_VERSION = 2;

    StarbuzzDatabaseHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        updateMyDatabase(db, 0, DB_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        updateMyDatabase(db, 0, DB_VERSION);
    }

    private static void  insertDrink(SQLiteDatabase db, String name,
                                     String description, int resourceId){
        ContentValues drinkValues = new ContentValues();
        drinkValues.put("NAME", name);
        drinkValues.put("DESCRIPTION", description);
        drinkValues.put("IMAGE_RESOURCE_ID", resourceId);
        db.insert("DRINK", null, drinkValues);
    }

    private static void  insertFood(SQLiteDatabase db, String name,
                                     String description, int resourceId){
        ContentValues foodValues = new ContentValues();
        foodValues.put("NAME", name);
        foodValues.put("DESCRIPTION", description);
        foodValues.put("IMAGE_RESOURCE_ID", resourceId);
        db.insert("FOOD", null, foodValues);
    }

    private static void  insertStore(SQLiteDatabase db, String name,
                                    String description, int resourceId){
        ContentValues storeValues = new ContentValues();
        storeValues.put("NAME", name);
        storeValues.put("DESCRIPTION", description);
        storeValues.put("IMAGE_RESOURCE_ID", resourceId);
        db.insert("STORE", null, storeValues);
    }

    private void updateMyDatabase(SQLiteDatabase db, int oldVersion, int newVersion){
        if(oldVersion < 1){
            db.execSQL("CREATE TABLE DRINK (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "NAME TEXT, "
                    + "DESCRIPTION TEXT,  "
                    + "IMAGE_RESOURCE_ID INTEGER);");
            insertDrink(db, "Latte", "Espresso and steamed milk", R.drawable.latte);
            insertDrink(db, "Capuccino", "Espresso, hot milk and steamed-milk foam", R.drawable.capuccino);
            insertDrink(db, "Filter", "Our best drip coffee", R.drawable.filter);

            db.execSQL("CREATE TABLE FOOD (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "NAME TEXT, "
                    + "DESCRIPTION TEXT,  "
                    + "IMAGE_RESOURCE_ID INTEGER);");
            insertFood(db, "Crispy Grilled Cheese on Sourdough", "A blend of white Cheddar and mozzarella cheeses on sourdough bread, topped with a Parmesan butter spread", R.drawable.grilled);
            insertFood(db, "Chicken & Bacon on Brioche", "Herbed, slow-cooked, white meat chicken, double-smoked bacon, maple mustard and cheese piled high on toasted apple brioche", R.drawable.chicken);
            insertFood(db, "Tomato & Mozzarella on Focaccia", "Roasted tomatoes, mozzarella, spinach and basil pesto layered on toasted focaccia bread", R.drawable.focaccia);

            db.execSQL("CREATE TABLE STORE (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "NAME TEXT, "
                    + "DESCRIPTION TEXT,  "
                    + "IMAGE_RESOURCE_ID INTEGER);");
            insertStore(db, "Tijuana", "Paseos De Los Heroes No 9902, Zona Rio, Tijuana, 22010", R.drawable.tij);
            insertStore(db, "Ensenada", "Av. Ryerson & Calle Octava, Zona Centro, 22800 Ensenada, B.C.", R.drawable.ens);
            insertStore(db, "Mexicali", "Av. Monclova 422, Ex-Ejido Coahuila, 21360 Mexicali, B.C.", R.drawable.mexl);
        }
        if(oldVersion < 2) {
            db.execSQL("ALTER TABLE DRINK ADD COLUMN FAVORITE NUMERIC;");
            db.execSQL("ALTER TABLE FOOD ADD COLUMN FAVORITE NUMERIC;");
            db.execSQL("ALTER TABLE STORE ADD COLUMN FAVORITE NUMERIC;");
        }
    }
}

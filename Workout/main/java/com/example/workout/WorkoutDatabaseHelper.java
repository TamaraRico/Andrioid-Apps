package com.example.workout;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class WorkoutDatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "WORKOUT";
    private static final int DB_VERSION = 1;

    WorkoutDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE WORKOUT (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "NAME TEXT, "
                + "DESCRIPTION TEXT);");
        insertWorkout(db, "The limb loosener",
                "5 Handstand push-ups\n10 1-legged squats\n15 Pull-ups");
        insertWorkout(db, "Core agony",
                "100 pull-ups\n100 push-ups\n100 sit-ups\n100 squats");
        insertWorkout(db, "The wimp special",
                "5 pull-ups\n10 push-ups\n15 squats");
        insertWorkout(db, "Strength and Length",
                "500 meter run\n21 x 1.5 pood kettleball swing\n21 x pull-ups");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private static void insertWorkout(SQLiteDatabase db, String name,
                                      String description){
        ContentValues workoutValues = new ContentValues();
        workoutValues.put("NAME", name);
        workoutValues.put("DESCRIPTION", description);
        db.insert("WORKOUT", null, workoutValues);
    }
}

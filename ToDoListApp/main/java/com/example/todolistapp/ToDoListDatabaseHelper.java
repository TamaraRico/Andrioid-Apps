package com.example.todolistapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ToDoListDatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_TITLE = "toDoTasks";
    private static final int DB_VERSION = 1;

    ToDoListDatabaseHelper(Context context){
        super(context, DB_TITLE, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        updateMyDataBase(db, 0, DB_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        updateMyDataBase(db, oldVersion, newVersion);
    }

    private static void insertTask(SQLiteDatabase db,
                                      String title){
        ContentValues taskValues = new ContentValues();
        taskValues.put("TASK", title);
        taskValues.put("STATUS", 0);
        db.insert("TASKS", null, taskValues);
    }

    private void updateMyDataBase(SQLiteDatabase db, int oldVersion, int newVersion){
        if (oldVersion < 1){
            db.execSQL("CREATE TABLE TASKS ("
                    + "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "TASK TEXT, "
                    + "STATUS INTEGER, "
                    + "IMAGE_ID INTEGER);");

            insertTask(db, "cook dinner");
            insertTask(db, "Walk the dog");
        }
    }
}

package com.example.workout;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cursoradapter.widget.SimpleCursorAdapter;
import androidx.fragment.app.ListFragment;

public class WorkoutListFragment extends ListFragment {
    private static final String EXTRA_WORKOUT_ID = "workoutId";
    private SQLiteDatabase db;
    private Cursor cursor;

    static interface Listener{
        void itemClicked(long id);
    };

    private Listener listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = getView();

        SQLiteOpenHelper workoutDatabaseHelper = new WorkoutDatabaseHelper(getContext());
        try{
            db = workoutDatabaseHelper.getReadableDatabase();
            Cursor cursor = db.query("WORKOUTS",
                    new String[]{"_id", "TITLE"},
                    null, null, null, null, null);
            SimpleCursorAdapter listAdapter = new SimpleCursorAdapter(getContext(),
                    android.R.layout.simple_list_item_1,
                    cursor,
                    new String[]{"TITLE"},
                    new int[]{android.R.id.text1},
                    0);
            setListAdapter(listAdapter);
        } catch (SQLiteException e){
            Toast toast = Toast.makeText(getContext(), "Database unavaible", Toast.LENGTH_SHORT);
            toast.show();
        }
        // Inflate the layout for this fragment
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        this.listener = (Listener) context;
    }

    @Override
    public void onListItemClick(ListView listView, View itemView, int position, long id){
        if (listener != null){
            listener.itemClicked(id);
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        cursor.close();
        db.close();
    }
}

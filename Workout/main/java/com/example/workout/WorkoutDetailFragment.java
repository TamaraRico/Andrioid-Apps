package com.example.workout;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import org.w3c.dom.Text;

public class WorkoutDetailFragment extends Fragment {
    private long workoutId;

    @Override
   public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        if(savedInstanceState == null){
            StopwatchFragment stopwatch = new StopwatchFragment();
            FragmentTransaction ft = getChildFragmentManager().beginTransaction();
            ft.add(R.id.stopwatch_container, stopwatch);
            ft.addToBackStack(null);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.commit();
        } else{
            workoutId = savedInstanceState.getLong("workoutId");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_workout_detail, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        View view = getView();
        if (view != null) {
            //Create a cursor
            SQLiteOpenHelper workoutDatabaseHelper = new WorkoutDatabaseHelper(getContext());
            try {
                SQLiteDatabase db = workoutDatabaseHelper.getReadableDatabase();
                Cursor cursor = db.query("WORKOUT",
                        new String[]{"NAME", "DESCRIPTION"},
                        "_id = ?",
                        new String[]{Integer.toString((int) workoutId)},
                        null, null, null);

                //Move to the first record in the Cursor
                if (cursor.moveToFirst()) {
                    //Get the workout details from the cursor
                    String titleText = cursor.getString(0);
                    String descriptionText = cursor.getString(1);

                    //Populate the workout title
                    TextView title = (TextView) view.findViewById(R.id.textTitle);
                    title.setText(titleText);

                    //Populate the workout description
                    TextView description = (TextView) view.findViewById(R.id.textDescription);
                    description.setText(descriptionText);
                }
                cursor.close();
                db.close();
            } catch (SQLiteException e) {
                Toast toast = Toast.makeText(getContext(), "Database unavaible", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        savedInstanceState.putLong("workoutId", workoutId);
    }

    public void setWorkout(long id){ this.workoutId = id; }
}

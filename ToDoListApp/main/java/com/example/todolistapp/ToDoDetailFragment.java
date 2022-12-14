package com.example.todolistapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class ToDoDetailFragment extends Fragment {
    private static final int RESULT_OK = -1;
//    private Uri image;
    private long taskId;
    private static final String EXTRA_TASKID = "taskId";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            taskId = savedInstanceState.getLong("taskId");
//            image = Uri.parse(savedInstanceState.getString("image"));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_to_do_list_detail, container, false);
    }

    @Override
    public void onStart(){
        super.onStart();
        View view = getView();
        if(view != null){

//            if(image != null) {
//                ImageView imageFromGallery = (ImageView) view.findViewById(R.id.image);
//                imageFromGallery.setImageURI(image);
//            }

            ImageButton gallery = (ImageButton) view.findViewById(R.id.add_image_button);
            gallery.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(i, 3);
                }
            });

            ImageButton share = (ImageButton) view.findViewById(R.id.share_button);
            share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CheckBox taskMessage = (CheckBox) getView().findViewById(R.id.taskDescription);
                    String messageText = taskMessage.getText().toString();
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_TEXT, messageText);
                    String chooseTitle = getString(R.string.chooser);
                    Intent chosenIntent = Intent.createChooser(intent, chooseTitle);
                    startActivity(chosenIntent);
                }
            });

            CheckBox taskChecked = (CheckBox) view.findViewById(R.id.taskDescription);
            taskChecked.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ContentValues taskValues = new ContentValues();
                    taskValues.put("STATUS", taskChecked.isChecked());
                    SQLiteOpenHelper todoListDatabaseHelper = new ToDoListDatabaseHelper(getContext());
                    try{
                        SQLiteDatabase db_ = todoListDatabaseHelper.getWritableDatabase();
                        db_.update("TASKS", taskValues, "_id = ?", new String[]{Long.toString(taskId)});
                        db_.close();
                    } catch(SQLiteException e){
                        Toast toast = Toast.makeText(getContext(), "Database unavailable", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
            });

            SQLiteOpenHelper workoutDatabaseHelper = new ToDoListDatabaseHelper(getContext());
            try {
                SQLiteDatabase db = workoutDatabaseHelper.getReadableDatabase();
                Cursor cursor = db.query("TASKS",
                        new String[]{"TASK", "STATUS"},
                        "_id = ?",
                        new String[]{Integer.toString((int)taskId)},
                        null, null, null);

                if(cursor.moveToFirst()){
                    String descriptionText = cursor.getString(0);
                    int status = cursor.getInt(1);

                    CheckBox task = (CheckBox) view.findViewById(R.id.taskDescription);
                    task.setText(descriptionText);

                    boolean s = status == 0 ? false : true;
                    task.setChecked(s);
                }
                cursor.close();
                db.close();
            } catch (SQLiteException e){
                Toast toast = Toast.makeText(getContext(), "Database unavailable", Toast.LENGTH_SHORT);
                toast.show();
            }

            ImageButton delete = (ImageButton)view.findViewById(R.id.delete_button);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ContentValues taskValues = new ContentValues();
                    SQLiteOpenHelper todoListDatabaseHelper = new ToDoListDatabaseHelper(getContext());
                    try{
                        SQLiteDatabase db = todoListDatabaseHelper.getWritableDatabase();
                        db.delete("TASKS", "_id = ?", new String[] {String.valueOf(taskId)});
                        db.close();
                    } catch(SQLiteException e){
                        Toast toast = Toast.makeText(getContext(), "Database unavailable", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                    Toast toast1 = Toast.makeText(getContext(), "Delete done", Toast.LENGTH_SHORT);
                    toast1.show();
                }
            });
        }
    }

    public void onSaveInstanceState(Bundle savedInstanceState){
        savedInstanceState.putLong("taskId", taskId);
//        savedInstanceState.putString("image", image.toString());
    }

    public void setTask(long id){
        this.taskId = id;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && data != null){
            Uri image = data.getData();
            ImageView imageView = (ImageView) getView().findViewById(R.id.image);
            imageView.setImageURI(image);
        }
    }
}

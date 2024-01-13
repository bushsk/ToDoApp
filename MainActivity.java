package com.example.todo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        updateUI();
    }

    public void createNewNote(View view) {
        Toast.makeText(this, "Saving Note", Toast.LENGTH_SHORT).show();
        saveNote();
    }
    private void saveNote() {
        EditText new_todo_text = (EditText) findViewById(R.id.new_todo_text);
        String noteEntered = new_todo_text.getText().toString();

        TodoDbHelper dbHelper = new TodoDbHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SingleTodoNote.COLUMN_TEXT,noteEntered);

        long newRowId = db.insert(
                SingleTodoNote.TABLE_NAME,null,values);
        new_todo_text.setText("");
        dbHelper.close();

        updateUI();
    }
    private void updateUI() {
        // Retreive all todos

        TodoDbHelper dbHelper = new TodoDbHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = new String[]{
                SingleTodoNote._ID,
                SingleTodoNote.COLUMN_TEXT};

        String order = SingleTodoNote._ID +  " DESC ";

        Cursor cursor = db.query(
                SingleTodoNote.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                order);
        ArrayList<String> todosList = new ArrayList<>();
        while(cursor.moveToNext()){
            String currentTodoString =
                    cursor.getString(cursor.getColumnIndex(SingleTodoNote.COLUMN_TEXT));
            todosList.add(currentTodoString);
        }
        cursor.close();
        dbHelper.close();
        // Count
        TextView count_of_entries = (TextView) findViewById(R.id.count_of_entries);
        count_of_entries.setText("Count : " + String.valueOf(todosList.size()));
        // Display list
        displayTodoList(todosList);
    }

    private void displayTodoList(ArrayList<String> todosList) {
        String[] listData = new String[todosList.size()];
        listData = todosList.toArray(listData);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.activity_listview,listData);
        ListView listView = (ListView) findViewById(R.id.todo_list);
        listView.setAdapter(adapter);
    }

    public void deleteAllEntries(View view) {
        TodoDbHelper dbHelper = new TodoDbHelper(this);

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        int deletedRowsCount = db.delete(SingleTodoNote.TABLE_NAME,null,null);
        dbHelper.close();
        Toast.makeText(this,
                "Deleted " + String.valueOf(deletedRowsCount) + " Rows",
                Toast.LENGTH_SHORT).show();
        updateUI();
    }
}

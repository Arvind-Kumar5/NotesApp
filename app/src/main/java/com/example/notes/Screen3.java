package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

//import java.sql.Date;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Screen3 extends AppCompatActivity {

    int noteid = -1;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen3);

        editText = (EditText) findViewById(R.id.editTextTextPersonName3);
        Intent intent = getIntent();
        noteid = intent.getIntExtra("noteid", -1);

        if(noteid != -1){
            Note note = Screen2.notes.get(noteid);
            String noteContent = note.getContent();
            editText.setText(noteContent);
        }

    }

    public void saveMethod(View view){
        editText = (EditText) findViewById(R.id.editTextTextPersonName3);
        String content = editText.getText().toString();
        Context context = getApplicationContext();
        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("notes", Context.MODE_PRIVATE,null);
        DBHelper dbHelper = new DBHelper(sqLiteDatabase);

        SharedPreferences sharedPreferences = getSharedPreferences("com.example.notes", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");

        String title;
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        String date = dateFormat.format(new Date());

        if(noteid == -1){
            title = "NOTE_" + (Screen2.notes.size() + 1);
            dbHelper.saveNotes(username, title, content, date);
        } else{
            title = "NOTE_" + (noteid + 1);
            dbHelper.updateNote(title, date, content, username);
        }

        Intent intent2 = new Intent(this, Screen2.class);
        startActivity(intent2);
    }
}
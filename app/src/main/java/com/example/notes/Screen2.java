package com.example.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class Screen2 extends AppCompatActivity {

    TextView text;
    public static ArrayList<Note> notes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen2);

        text = (TextView) findViewById(R.id.textView);
        Intent intent = getIntent();

        String usernameKey = "username";

        SharedPreferences sharedPreferences = getSharedPreferences("com.example.notes", Context.MODE_PRIVATE);
        text.setText("Welcome " + sharedPreferences.getString(usernameKey, ""));

        Context context = getApplicationContext();
        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("notes", Context.MODE_PRIVATE,null);
        DBHelper instance = new DBHelper(sqLiteDatabase);

        notes = instance.readNotes(sharedPreferences.getString(usernameKey, ""));

        ArrayList<String> displayNotes = new ArrayList<>();
        for (Note note : notes){
            displayNotes.add(String.format("Title:%s\nDate:%s", note.getTitle(), note.getDate()));
        }

        System.out.println("DISPLAY NOTES IS:" + displayNotes);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, displayNotes);
        ListView listView = (ListView) findViewById(R.id.notesListView);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), Screen3.class);
                intent.putExtra("noteid", position);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();

        menuInflater.inflate(R.menu.menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout:
                Intent intent1 = new Intent(this, MainActivity.class);
                String usernameKey = "username";
                SharedPreferences sharedPreferences = getSharedPreferences("com.example.notes", Context.MODE_PRIVATE);
                sharedPreferences.edit().remove(usernameKey).apply();
                startActivity(intent1);
                return true;

            case R.id.add_note:
                Intent intent2 = new Intent(this, Screen3.class);
                startActivity(intent2);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
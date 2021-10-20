package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    public void clickFunction(View view){
        EditText personText = (EditText) findViewById(R.id.editTextTextPersonName);
        EditText passText = (EditText) findViewById(R.id.editTextTextPersonName2);

        String person = personText.getText().toString();
        String password = passText.getText().toString();

        SharedPreferences sharedPreferences = getSharedPreferences("com.example.notes", Context.MODE_PRIVATE);
        sharedPreferences.edit().putString("username", person).apply();


        goToScreen2(person, password);
    }

    public void goToScreen2(String person, String password){

        Intent intent = new Intent(this, Screen2.class);

        intent.putExtra("person", person);
        intent.putExtra("password", password);

        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String usernameKey = "username";

        SharedPreferences sharedPreferences = getSharedPreferences("com.example.notes", Context.MODE_PRIVATE);

        if(!sharedPreferences.getString(usernameKey,"").equals("")){

            String username = sharedPreferences.getString(usernameKey,"");

            Intent intent = new Intent(this, Screen2.class);
            intent.putExtra("person", username);
        }

        setContentView(R.layout.activity_main);
    }
}
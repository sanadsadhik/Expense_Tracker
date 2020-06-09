package com.example.expensetracker;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText name, password;
    Button login, signup;
    databaseHelper myDB;
    globalClass globalVar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDB=new databaseHelper(this);
        name = findViewById(R.id.uname);
        password = findViewById(R.id.upass);
        login = findViewById(R.id.loginb);
        signup= findViewById(R.id.signup);
        globalVar=(globalClass)getApplicationContext();
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToSignUp();
            }
        });

        login_function();


    }
    private void  moveToSignUp(){
        Intent intent = new Intent(MainActivity.this,sign_up.class);
        startActivity(intent);
    }

    public void login_function(){
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String em=name.getText().toString();
                boolean isValid = myDB.checkLoginData(em,password.getText().toString());
                if(isValid) {
                    globalVar.setUserEmail(em);
                    Intent intent = new Intent(MainActivity.this,home_page.class);
                    startActivity(intent);
                }
                else
                    Toast.makeText(MainActivity.this, "Invald Credentials", Toast.LENGTH_SHORT).show();


            }
        });
    }




}

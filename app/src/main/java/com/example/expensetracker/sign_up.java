package com.example.expensetracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class sign_up extends AppCompatActivity {
    Button b2,b1;
    databaseHelper myDB;
    EditText e1,e2,e3,e4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);
        myDB=new databaseHelper(this);
        b1=findViewById(R.id.okay);
        b2=findViewById(R.id.loginb);
        e1=findViewById(R.id.sname);
        e2=findViewById(R.id.email);
        e3=findViewById(R.id.spassword);
        e4=findViewById(R.id.phnum);

        addData();

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToLogin();
            }
        });
    }
    public void addData(){
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean ifInserted=myDB.insert_data(e1.getText().toString(),e2.getText().toString(),e3.getText().toString(),e4.getText().toString());
                if (ifInserted){
                    Toast.makeText(sign_up.this, "Registration succesful", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(sign_up.this, "Please try again", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void moveToLogin(){
        Intent intent=new Intent(sign_up.this,MainActivity.class);
        startActivity(intent);
    }
}

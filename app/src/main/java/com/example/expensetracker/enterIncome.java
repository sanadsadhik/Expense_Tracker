package com.example.expensetracker;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class enterIncome extends AppCompatActivity {

    Spinner incomeSpinner;
    Button b1;
    EditText et1;
    databaseHelper myDB;
    globalClass globalVar;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_income);
        myDB=new databaseHelper(this);
        globalVar=(globalClass)getApplicationContext();
        String em=globalVar.getUserEmail();
        Cursor cursor = myDB.getUserId(em);
        cursor.moveToFirst();
        id=cursor.getInt(0);
        incomeSpinner=findViewById(R.id.spinner);
        ArrayAdapter<String> incomeAdapter= new ArrayAdapter<String>(enterIncome.this,android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.incomeCategories));
        incomeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        incomeSpinner.setAdapter(incomeAdapter);
        b1=findViewById(R.id.button);
        et1=findViewById(R.id.et1);
        addTransaction(id);

    }

    public void addTransaction(final int id){
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int amount=Integer.parseInt(et1.getText().toString());
                String category=incomeSpinner.getSelectedItem().toString();
                boolean ifInserted=myDB.insertIncome(id,amount,category);
                if (ifInserted){
                    Toast.makeText(enterIncome.this, "Transaction recorded", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(enterIncome.this, "Please try again", Toast.LENGTH_SHORT).show();
            }
        });
    }



}

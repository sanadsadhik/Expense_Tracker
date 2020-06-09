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

public class enterExpense extends AppCompatActivity {

    Spinner catSpinner,modeSpinner;
    Button b1;
    EditText et1;
    databaseHelper myDB;
    globalClass globalVar;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_expense);
        myDB=new databaseHelper(this);
        globalVar=(globalClass)getApplicationContext();
        String em=globalVar.getUserEmail();
        Cursor cursor = myDB.getUserId(em);
        cursor.moveToFirst();
        id=cursor.getInt(0);
        catSpinner=findViewById(R.id.catSpinner);
        modeSpinner=findViewById(R.id.modeSpinner);
        ArrayAdapter<String> catAdapter= new ArrayAdapter<String>(enterExpense.this,android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.expenseCategories));
        ArrayAdapter<String> modeAdapter= new ArrayAdapter<String>(enterExpense.this,android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.paymentModes));
        catAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        modeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        catSpinner.setAdapter(catAdapter);
        modeSpinner.setAdapter(modeAdapter);
        b1=findViewById(R.id.button2);
        et1=findViewById(R.id.editText);
        addTransaction(id);
    }

    public void addTransaction(final int id){
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int amount=Integer.parseInt(et1.getText().toString());
                String category=catSpinner.getSelectedItem().toString();
                String mode=modeSpinner.getSelectedItem().toString();
                boolean ifInserted=myDB.insertExpense(id,amount,category,mode);
                if (ifInserted){
                    Toast.makeText(enterExpense.this, "Transaction recorded", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(enterExpense.this, "Please try again", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

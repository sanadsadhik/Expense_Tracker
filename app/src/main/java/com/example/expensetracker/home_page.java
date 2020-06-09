package com.example.expensetracker;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class home_page extends AppCompatActivity {

    TextView tv;
    globalClass globalVar;
    Button b1,b2,b3,b4;
    TextView balance;
    databaseHelper myDB;
    int id,b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page2);
        myDB=new databaseHelper(this);
        globalVar=(globalClass)getApplicationContext();
        String em=globalVar.getUserEmail();
        tv=findViewById(R.id.tv);
        Cursor cursor = myDB.getUserId(em);
        cursor.moveToFirst();
        id=cursor.getInt(0);
        b=myDB.getBalance(id);

        balance=findViewById(R.id.balance);
        balance.setText(""+b);
        b1=findViewById(R.id.b1);
        b2=findViewById(R.id.b2);
        b3=findViewById(R.id.b3);
        b4=findViewById(R.id.b4);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToIncome();
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToExpense();
            }
        });
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToGraph();
            }
        });
        viewAll(id);
    }

    private void moveToIncome(){
        Intent intent = new Intent(home_page.this,enterIncome.class);
        startActivity(intent);
    }
    private void moveToExpense(){
        Intent intent = new Intent(home_page.this,enterExpense.class);
        startActivity(intent);
    }
    private void moveToGraph(){
        Intent intent = new Intent(home_page.this,barGraph.class);
        startActivity(intent);
    }
    public void viewAll(final int id){
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res=myDB.getAllData(id);
                StringBuffer buffer=new StringBuffer();
                if(res.getCount()==0){
                    showMessage("Error","No transaction");
                }
                while (res.moveToNext()){
                    buffer.append("Amount-"+res.getInt(2)+" ");
                    buffer.append("Category-"+res.getString(3)+"\n");
                }
                showMessage("Transactions",buffer.toString());
            }
        });

    }

    public void showMessage(String title,String message){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();

    }

    public void onResume()
    {  // After a pause OR at startup
        super.onResume();
        //Refresh your stuff here
        b=myDB.getBalance(id);
        balance=findViewById(R.id.balance);
        balance.setText(""+b);
    }
}

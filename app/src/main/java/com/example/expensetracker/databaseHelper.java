package com.example.expensetracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.Settings;

import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieEntry;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import androidx.annotation.Nullable;

public class databaseHelper extends SQLiteOpenHelper {

    public static final String db_name="expensetracker.db";
    public static final String table_name1="user";
    public static final String table_name2="transactions";
    public static final String col1="id";
    public static final String col2="name";
    public static final String col3="email";
    public static final String col4="password";
    public static final String col5="phone";
    public static final String tcol1="t_id";
    public static final String tcol2="u_id";
    public static final String tcol3="amount";
    public static final String tcol4="category";
    public static final String tcol5="pay_mode";
    public databaseHelper(@Nullable Context context) {
        super(context, db_name, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE user (id INTEGER PRIMARY KEY AUTOINCREMENT,name TEXT,email TEXT,password TEXT,phone INTEGER)");
        db.execSQL("CREATE TABLE transactions (t_id INTEGER PRIMARY KEY AUTOINCREMENT,u_id INTEGER,amount INTEGER,category TEXT,pay_mode TEXT,created_at DATETIME DEFAULT CURRENT_TIMESTAMP,FOREIGN KEY(u_id) REFERENCES user(id) )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+table_name1);
        db.execSQL("DROP TABLE IF EXISTS "+table_name2);
        onCreate(db);
    }

    public boolean insert_data(String name,String email,String password,String phone){
        if(name.isEmpty() || email.isEmpty() || password.isEmpty() || phone.isEmpty()){
            return false;
        }
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues =new ContentValues();
        contentValues.put(col2,name);
        contentValues.put(col3,email);
        contentValues.put(col4,password);
        contentValues.put(col5,phone);
        long result = db.insert(table_name1, null, contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    public boolean checkLoginData(String email, String password){
        if(email.isEmpty() || password.isEmpty()){
            return false;
        }
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor res = db.query(table_name1,new String[] {col1,col2,col3,col4,col5},col3+"=?",new String[] {email},null,null,null);
        if(res.getCount()==0){
            return false;
        }
        res.moveToFirst();
        String p=res.getString(3);

        if (password.equals(p)){
            res.close();
            return true;
        }
        res.close();
        return false;
    }

    public boolean insertIncome(int id,int amount,String category){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues =new ContentValues();
        contentValues.put(tcol2,id);
        contentValues.put(tcol3,amount);
        contentValues.put(tcol4,category);
        contentValues.put(tcol5,"-");
        long result = db.insert(table_name2, null, contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    public Cursor getUserId(String email){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor res = db.query(table_name1,new String[] {col1},col3+"=?",new String[] {email},null,null,null);
        res.moveToFirst();
        return res;
    }

    public boolean insertExpense(int id,int amount,String category,String mode){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues =new ContentValues();
        amount=amount*(-1);
        contentValues.put(tcol2,id);
        contentValues.put(tcol3,amount);
        contentValues.put(tcol4,category);
        contentValues.put(tcol5,mode);
        long result = db.insert(table_name2, null, contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    public int getBalance(int id){
        int balance =0;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT SUM(amount) FROM transactions WHERE u_id="+id, null);
        if(cursor.moveToFirst()) {
            balance = cursor.getInt(0);
        }
        return balance;
    }

    public Cursor getAllData(int id){
        SQLiteDatabase db=this.getWritableDatabase();
        return db.rawQuery(
                "SELECT * FROM transactions WHERE u_id="+id+" ORDER BY datetime(created_at) DESC", null);
    }

    public ArrayList<PieEntry> getChartEntries(int id) {
        String income_sum_column = "income";
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = new String[]{"SUM(amount)*-1 AS " + income_sum_column, "category"};
        String whereClause = "u_id = ? AND amount<0";
        String[] whereArgs = new String[] {
                ""+id,
        };
        Cursor csr = db.query("transactions", columns, whereClause, whereArgs, "category", null, null);
        ArrayList<PieEntry> rv = new ArrayList<PieEntry>();
        while (csr.moveToNext()) {
            rv.add(new PieEntry(csr.getInt(csr.getColumnIndex(income_sum_column)), csr.getString(csr.getColumnIndex("category"))));
        }
        csr.close();
        return rv;
    }

}

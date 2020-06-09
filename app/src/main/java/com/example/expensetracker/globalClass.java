package com.example.expensetracker;

import android.app.Application;

public class globalClass extends Application {

    private String userId;

    public String getUserEmail(){
        return userId;
    }

    public void setUserEmail(String id){
        userId=id;
    }
}

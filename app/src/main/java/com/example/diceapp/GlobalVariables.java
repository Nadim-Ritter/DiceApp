package com.example.diceapp;

import android.app.Application;
import android.content.SharedPreferences;

public class GlobalVariables extends Application {

    private SharedPreferences resultHistory;

    public SharedPreferences getResultHistory() {
        return resultHistory;
    }

    public void setResultHistory(SharedPreferences resultHistory) {
        this.resultHistory = resultHistory;
    }
}

package com.example.diceapp;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

public class Settings {

    private View view;
    private View popupView;

    public Settings(View view, View popupView) {
        this.view = view;
        this.popupView = popupView;
    }

    public void changeColor(final ActionBar actionBar){
        Switch changeColorSwitch = (Switch) popupView.findViewById(R.id.changeColorSwitch);

        changeColorSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#AA3939")));
                } else {
                    actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3F51B5")));
                }
            }
        });

    }

    public void clearHistory(SharedPreferences resultHistory){
        resultHistory.edit().clear().apply();

    }

    public void changePassword(){

    }
}

package com.example.diceapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v4.app.Fragment;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TabFragment1 extends Fragment {

    SharedPreferences resultHistory;
    int currentNumber = 0;
    static String packageName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_fragment1, container, false);

        ConstraintLayout layout = (ConstraintLayout) view.findViewById(R.id.layout);

        //set package name
        packageName = "com.example.diceapp";

        //create dice
        final List<Dice> diceList = new ArrayList<>();

        Dice dice4 = new Dice("dice4", 4);
        Dice dice6 = new Dice("dice6", 6);
        Dice dice8 = new Dice("dice8", 8);
        Dice dice10 = new Dice("dice10", 10);
        Dice dice12 = new Dice("dice12", 12);
        Dice dice20 = new Dice("dice20", 20);

        diceList.add(dice4);
        diceList.add(dice6);
        diceList.add(dice8);
        diceList.add(dice10);
        diceList.add(dice12);
        diceList.add(dice20);

        boolean secondInLine = false;

        //store results in shared pref
        resultHistory = this.getActivity().getSharedPreferences("resultHistory", Context.MODE_PRIVATE);

        //players = ((GlobalVariables) this.getApplication()).getPlayers();
        //((GlobalVariables) this.getApplication()).setCurrQuestionType("trueFalseQuestion");

        for (int i = 0; i < diceList.size(); i++) {

            final int index = i;

            int resID = getResources().getIdentifier(diceList.get(index).getName(), "id", packageName);
            Button button = ((Button) view.findViewById(resID));

            //random function
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Random rd = new Random();
                    int max = diceList.get(index).getDiceType();
                    int min = 1;

                    int randomNumber = rd.nextInt((max - min) + 1) + min;
                    randomNumber = randomNumber + currentNumber;

                    //show result in alert dialog
                    alertDialog(diceList.get(index).getName(), randomNumber);

                    String diceTypeWithTimestamp;

                    //store value
                    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                    if(currentNumber > 0){
                        diceTypeWithTimestamp = diceList.get(index).getName() + "+" + currentNumber + ";" + timestamp;
                    }else if(currentNumber < 0){
                        diceTypeWithTimestamp = diceList.get(index).getName() + currentNumber + ";" + timestamp;
                    }else{
                        diceTypeWithTimestamp = diceList.get(index).getName() + ";" + timestamp;
                    }

                    resultHistory.edit().putInt(diceTypeWithTimestamp, randomNumber).apply();
                }
            });

            //store result in global variable
            ((GlobalVariables) this.getActivity().getApplication()).setResultHistory(resultHistory);

        }

        Button removeButton = (Button) view.findViewById(R.id.removeButton);
        Button addButton = (Button) view.findViewById(R.id.addButton);

        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeNumber(false);
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeNumber(true);
            }
        });

        return view;
    }

    public void changeNumber(boolean operation){
        TextView extraNumberField = (TextView) getView().findViewById(R.id.extraNumberField);

        //get number without operation
        currentNumber = Integer.parseInt(extraNumberField.getText().toString());

        //if operation is true: add number, else: remove number
        if(operation){
            currentNumber++;

        }else{
            currentNumber--;
        }

        if(currentNumber < 0){
            extraNumberField.setText(Integer.toString(currentNumber));
        }else{
            extraNumberField.setText("+" + currentNumber);
        }

    }

    private void alertDialog(String diceName, Integer result) {
        AlertDialog.Builder resultAlert = new AlertDialog.Builder(new ContextThemeWrapper(getView().getContext(), R.style.ResultAlertStyle));

        resultAlert.setMessage(result.toString());

        if(currentNumber > 0){
            resultAlert.setTitle(diceName + "+" + currentNumber);
        }else if(currentNumber < 0){
            resultAlert.setTitle(diceName + currentNumber);
        }else{
            resultAlert.setTitle(diceName);
        }


        AlertDialog alertDialog = resultAlert.create();

        alertDialog.show();
        alertDialog.getWindow().setLayout(400, 300);
    }
}
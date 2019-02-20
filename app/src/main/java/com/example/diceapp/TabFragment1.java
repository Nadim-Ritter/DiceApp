package com.example.diceapp;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TabFragment1 extends Fragment{

    SharedPreferences resultHistory;
    static String packageName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_fragment1, container, false);

        ConstraintLayout layout = (ConstraintLayout) view.findViewById(R.id.layout);
        ConstraintSet set = new ConstraintSet();
        set.clone(layout);

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
        //resultHistory = getSharedPreferences("resultHistory", Context.MODE_PRIVATE);

        for (int i = 0; i < diceList.size(); i++) {
            /*FrameLayout.LayoutParams layoutButton = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);

            //Button Type
            Button button = new Button(getActivity());

            button.setId(i);

            //Layout
            if(secondInLine){
                set.connect(button.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, 100);
                set.connect(button.getId(),ConstraintSet.RIGHT,ConstraintSet.PARENT_ID,ConstraintSet.RIGHT,100);
                set.connect(button.getId(),ConstraintSet.LEFT,ConstraintSet.PARENT_ID,ConstraintSet.LEFT,600);
                set.connect(button.getId(),ConstraintSet.TOP,ConstraintSet.PARENT_ID,ConstraintSet.TOP,100 + (200*(i-1)));

                //layoutButton.setMargins(600, 100 + (200*(i-1)), 100, 100);
                secondInLine = false;
            }else{
                set.connect(button.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, 100);
                set.connect(button.getId(),ConstraintSet.RIGHT,ConstraintSet.PARENT_ID,ConstraintSet.RIGHT,100);
                set.connect(button.getId(),ConstraintSet.LEFT,ConstraintSet.PARENT_ID,ConstraintSet.LEFT,200);
                set.connect(button.getId(),ConstraintSet.TOP,ConstraintSet.PARENT_ID,ConstraintSet.TOP,100 + (200*i));

                //layoutButton.setMargins(200, 100 + (200*i), 100, 100);
                secondInLine = true;
            }

            //Image or Text
            //button.setImageResource(getResources().getIdentifier(diceList.get(i).getName(), "drawable", getPackageName()));
            button.setText(Integer.toString(diceList.get(i).getDiceType()));
            button.setTypeface(Typeface.DEFAULT_BOLD);
            button.setTextSize(TypedValue.COMPLEX_UNIT_SP, 40);

            set.constrainHeight(button.getId(), 300);
            set.constrainWidth(button.getId(), 300);

            set.applyTo(layout);

            layoutButton.width = 300;
            layoutButton.height = 300;

            //button.setLayoutParams(layoutButton);



            //add button to the layout
            layout.addView(button);
            */


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
                    System.out.println(randomNumber);

                    /*TextView resultOutput = (TextView) getView().findViewById(R.id.resultOutput);

                    resultOutput.setText(Integer.toString(randomNumber));*/

                    //store value
                    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                    String diceTypeWithTimestamp = diceList.get(index).getName() + ";" + timestamp;

                    //resultHistory.edit().putInt(diceTypeWithTimestamp, randomNumber).apply();
                }
            });

        }

        return view;
    }
}
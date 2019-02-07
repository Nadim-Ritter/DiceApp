package com.example.diceapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    SharedPreferences resultHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //the layout on which you are working
        FrameLayout layout = (FrameLayout) findViewById(R.id.layout);

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
        resultHistory = getSharedPreferences("resultHistory", Context.MODE_PRIVATE);

        for (int i = 0; i < diceList.size(); i++) {
            FrameLayout.LayoutParams layoutButton = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);

            //Button Type
            Button button = new Button(this);
            //ImageButton button = new ImageButton(this);

            //Layout
            if(secondInLine){
                layoutButton.setMargins(600, 100 + (200*(i-1)), 100, 100);
                secondInLine = false;
            }else{
                layoutButton.setMargins(200, 100 + (200*i), 100, 100);
                secondInLine = true;
            }


            layoutButton.width = 300;
            layoutButton.height = 300;

            button.setLayoutParams(layoutButton);

            //Image or Text
            //button.setImageResource(getResources().getIdentifier(diceList.get(i).getName(), "drawable", getPackageName()));
            button.setText(Integer.toString(diceList.get(i).getDiceType()));
            button.setTypeface(Typeface.DEFAULT_BOLD);
            button.setTextSize(TypedValue.COMPLEX_UNIT_SP, 40);

            //add button to the layout
            layout.addView(button);

            final int index = i;

            //random function
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Random rd = new Random();
                    int max = diceList.get(index).getDiceType();
                    int min = 1;

                    int randomNumber = rd.nextInt((max - min) + 1) + min;
                    TextView resultOutput = (TextView) findViewById(R.id.resultOutput);

                    resultOutput.setText(Integer.toString(randomNumber));

                    //store value
                    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                    String diceTypeWithTimestamp = diceList.get(index).getName() + ";" + timestamp;

                    resultHistory.edit().putInt(diceTypeWithTimestamp, randomNumber).apply();
                }
            });
        }
    }

    public void getAllPrefs(View view) throws ParseException {

        Map<String, ?> allEntries = resultHistory.getAll();
        List<Timestamp> timestampList = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");

        List<Dice> diceWithResult = new ArrayList<>();


        //iterate throw list
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            String timestampTemp[] = entry.getKey().split(";");

            Date parsedDate = dateFormat.parse(timestampTemp[1]);

            Timestamp timestamp = new Timestamp(parsedDate.getTime());
            timestampList.add(timestamp);

            String value = entry.getValue().toString();

            //fill result in object, add to list
            Dice diceResult = new Dice(timestampTemp[0], Integer.parseInt(value), timestamp);
            diceWithResult.add(diceResult);
        }

        //sort list with diceType, result, timestamp
        Collections.sort(diceWithResult, Collections.<Dice>reverseOrder());

        //change to dice history
        Intent intent = new Intent(this, DiceHistory.class);
        intent.putExtra("diceWithResult", new DiceParameter(diceWithResult));
        startActivity(intent);
    }
}

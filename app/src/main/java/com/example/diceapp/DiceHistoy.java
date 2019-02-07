package com.example.diceapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class DiceHistoy extends AppCompatActivity {

    List<Dice> diceWithResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dice_histoy);

        Intent ii = this.getIntent();

        // set dice list
        DiceParameter diceWithResultTemp = (DiceParameter) ii.getSerializableExtra("diceWithResult");
        diceWithResult = diceWithResultTemp.getDice();

        List<String> resultsInString = new ArrayList<>();

        for(int i = 0; i < diceWithResult.size(); i++){
            resultsInString.add(diceWithResult.get(i).getName() + " " + diceWithResult.get(i).getResult() + " " + diceWithResult.get(i).getTimestamp());
        }

        ListView listView = (ListView) findViewById(R.id.listView);
        ArrayAdapter adapter = new ArrayAdapter<String>(this,R.layout.text_view_for_list, resultsInString);

        listView.setAdapter(adapter);






    }
}

package com.example.diceapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class TabFragment2 extends Fragment {

    List<Dice> diceWithResult;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tab_fragment2, container, false);

        loadList(view);

        return view;
    }

    public void loadList(View view){
        //get results
        try {
            diceWithResult = getAllPrefs(view);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        List<String> resultsInString = new ArrayList<>();

        for(int i = 0; i < diceWithResult.size(); i++){
            resultsInString.add(diceWithResult.get(i).getName() + " " + diceWithResult.get(i).getResult() + " " + diceWithResult.get(i).getTimestamp());
        }

        ListView listView = (ListView) view.findViewById(R.id.listView);
        ArrayAdapter adapter = new ArrayAdapter<String>(view.getContext(), R.layout.text_view_for_list, resultsInString);

        listView.setAdapter(adapter);
    }

    public List<Dice> getAllPrefs(View view) throws ParseException {

        //load result history data into list
        Map<String, ?> allEntries = ((GlobalVariables) this.getActivity().getApplication()).getResultHistory().getAll();

        //create date format for timestamp list
        List<Timestamp> timestampList = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");

        List<Dice> diceWithResult = new ArrayList<>();

        //iterate list
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            String timestampTemp[] = entry.getKey().split(";");

            Date parsedDate = null;
            parsedDate = dateFormat.parse(timestampTemp[1]);

            Timestamp timestamp = new Timestamp(parsedDate.getTime());
            timestampList.add(timestamp);

            String value = entry.getValue().toString();

            //fill result in object, add to list
            Dice diceResult = new Dice(timestampTemp[0], Integer.parseInt(value), timestamp);
            diceWithResult.add(diceResult);
        }

        //sort list with diceType, result, timestamp
        Collections.sort(diceWithResult, Collections.<Dice>reverseOrder());

        return diceWithResult;
    }


    //reload list
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            loadList(view);
        }
    }
}
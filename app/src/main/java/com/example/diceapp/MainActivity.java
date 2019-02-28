package com.example.diceapp;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    View view;
    boolean accessGranted;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    private TextView speechOutput;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = findViewById(android.R.id.content);
        setContentView(R.layout.activity_main);
    }

    public void initializeTabLayout() {
        //if input equals one of the passwords
        //tab layout setup
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Dice"));
        tabLayout.addTab(tabLayout.newTab().setText("History"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    public void showSpeechPopup() {
        //set speech input popup
        // inflate the layout of the popup window
        final Security security = new Security();

        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        final View speechLockView = inflater.inflate(R.layout.speech_lock, null);

        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        //int width = 500;
        //int height = 500;
        boolean focusable = false; // lets taps outside the popup also dismiss it
        final PopupWindow speechLockPopupWindow = new PopupWindow(speechLockView, width, height, focusable);


        // show the popup window
        speechLockPopupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        Button speechButton = (Button) speechLockView.findViewById(R.id.speechButton);
        boolean touched = false;

        speechButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                askSpeechInput(speechLockView);
            }
        });


    }

    //execute after oncreate
    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        showSpeechPopup();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_tab_fragment1, menu);
        return true;
    }

    //options menu action
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(accessGranted){
            if (id == R.id.action_settings) {
                openSettings(view);
                return true;
            }

            return super.onOptionsItemSelected(item);
        }

        return false;
    }


    //settings menu
    public void openSettings(View view) {
        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_settings, null);

        //settings class
        Settings settings = new Settings(view, popupView);

        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        //int width = 500;
        //int height = 500;
        boolean focusable = false; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);


        // show the popup window
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        //change Color
        settings.changeColor(getSupportActionBar());

        //clearHistory
        settings.clearHistory(((GlobalVariables) getApplication()).getResultHistory());


        //close settings
        Button cancelButton = (Button) popupView.findViewById(R.id.cancelButton);
        cancelButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });

    }

    public void askSpeechInput(View speechLockView) {
        speechOutput = (TextView) speechLockView.findViewById(R.id.speechOutput);

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                "Hi speak something");

        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            a.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    speechOutput.setText(result.get(0));
                    password = result.get(0);

                    if (password != null) {
                        Security security = new Security();
                        for (String s : security.getPasswords()) {
                            if (password.equals(s)) {
                                accessGranted = true;
                                initializeTabLayout();
                                break;
                            }
                        }
                    }
                }
                break;
            }

        }
    }
}
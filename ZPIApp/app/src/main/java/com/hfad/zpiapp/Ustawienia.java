package com.hfad.zpiapp;

//import android.support.v7.app.ActionBar;
//import android.support.v7.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class Ustawienia extends AppCompatActivity {
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ustawienia);
        preferences = getApplicationContext().getSharedPreferences("APP_SETTINGS",0);
        editor = preferences.edit();
        this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar_3);

        Switch setNotification = (Switch) findViewById(R.id.setPowiadomienia);
        Switch setDarkMode = (Switch) findViewById(R.id.setDarkMode);
        Switch setDzwiek = (Switch) findViewById(R.id.setDzwiek);
        Switch setWibracje = (Switch) findViewById(R.id.setWibracje);

        setDarkMode.setChecked(preferences.getBoolean("darkModeBool",true));
        setNotification.setChecked(preferences.getBoolean("notifyBool",true));
        setDzwiek.setChecked(preferences.getBoolean("soundBool",true));
        setWibracje.setChecked(preferences.getBoolean("vibrationBool",true));


        setNotification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    editor.putBoolean("notifyBool", true);
                }
                else
                {
                    editor.putBoolean("notifyBool", false);

                }
                editor.apply();
            }
        });

        setDarkMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    recreate();
                    editor.putBoolean("darkModeBool", true);

                }
                else
                {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    recreate();
                    editor.putBoolean("darkModeBool", false);


                }
                editor.apply();
            }
        });

        setDzwiek.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    editor.putBoolean("soundBool", true);
                }
                else
                {
                    editor.putBoolean("soundBool", false);

                }
                editor.apply();
            }
        });

        setWibracje.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    editor.putBoolean("vibrationBool", true);
                }
                else
                {
                    editor.putBoolean("vibrationBool", false);

                }
                editor.apply();
            }
        });
    }

    public void comeBackMethod(View view)
    {

        super.onBackPressed();
      /* Intent i = new Intent(this,Glowna.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);*/
    }



}

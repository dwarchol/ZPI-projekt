package com.hfad.zpiapp;

//import android.support.v7.app.ActionBar;
//import android.support.v7.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class Ustawienia extends AppCompatActivity {

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ustawienia);

        this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar_2);

    }

}

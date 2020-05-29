package com.hfad.zpiapp;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;

import androidx.appcompat.app.AppCompatActivity;

//import android.support.v7.app.ActionBar;
//import android.support.v7.app.AppCompatActivity;

public class KontoUzytkownika extends AppCompatActivity {
    Dialog progressDialog;
    Context ctx;
    boolean museum = false;

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konto_uzytkownika);
        Intent intent = getIntent();
        int postep=intent.getIntExtra("prog",0);

        this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar_2);
        SeekBar sb = (SeekBar) findViewById(R.id.seekBar);
        System.out.println(postep);
        sb.setMax(47);
        sb.setProgress(postep);

        ctx = this;
    }

    public void comeBackMethod(View view)
    {
        super.onBackPressed();
    }
}

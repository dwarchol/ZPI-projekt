package com.hfad.zpiapp;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Glowna extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glowna);

        this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar);

        View view = getSupportActionBar().getCustomView();

    }

    public void settingsMethod(View view)
    {
        final Intent settingsIntent=new Intent(this,Ustawienia.class);
        startActivity(settingsIntent);
    }

    public void userMethod(View view)
    {
        final Intent userIntent=new Intent(this,KontoUzytkownika.class);
        startActivity(userIntent);
    }
}

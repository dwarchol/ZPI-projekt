package com.hfad.zpiapp;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Glowna extends AppCompatActivity {
    Dialog coordinatesDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glowna);

        this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar);

        coordinatesDialog = new Dialog(this);
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

    public void coordinatesMethod(View view)
    {
       /* coordinatesDialog.setContentView(R.layout.custom_popup_coordinates);
        coordinatesDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Button closeDialog = (Button) coordinatesDialog.findViewById(R.id.closeCoordinates);
        closeDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                coordinatesDialog.dismiss();
            }
        });
        coordinatesDialog.show();*/
        coordinatesDialog.setContentView(R.layout.popup_zrob_zdj);
        coordinatesDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        coordinatesDialog.show();
    }

}

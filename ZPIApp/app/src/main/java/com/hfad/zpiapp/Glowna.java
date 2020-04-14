package com.hfad.zpiapp;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
//import android.support.v7.app.ActionBar;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Glowna extends AppCompatActivity {
    Dialog coordinatesDialog;

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glowna);

        this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar);

        coordinatesDialog = new Dialog(this);
        coordinatesDialog.setCancelable(true);
        coordinatesDialog.setCanceledOnTouchOutside(false);
       /* int [] id= new int[]{R.drawable.skytower3,R.drawable.skytower4,R.drawable.skytower5};
                R.drawable.skytower6,R.drawable.skytower7,R.drawable.skytower8,R.drawable.skytower9,
                R.drawable.skytower10,R.drawable.skytower11,R.drawable.skytower12,R.drawable.skytower13,R.drawable.skytower14,R.drawable.skytower15,R.drawable.skytower16};
        for (int i=0;i<id.length;i++) {

            Bitmap icon = BitmapFactory.decodeResource(getResources(),id[i]);
            MLLandmark ml = new MLLandmark(icon,this);
            String sb = ml.labeler(i+3);
            // Toast.makeText(getApplicationContext(),"yupi",Toast.LENGTH_LONG);
            Toast.makeText(getApplicationContext(), sb, Toast.LENGTH_LONG).show();
        }*/
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
        coordinatesDialog.setContentView(R.layout.custom_popup_coordinates);
        coordinatesDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Button closeDialog = (Button) coordinatesDialog.findViewById(R.id.closeCoordinates);
        closeDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                coordinatesDialog.dismiss();
            }
        });
        coordinatesDialog.show();

    }

    public void onBackPressed()
    {
        if(coordinatesDialog.isShowing())
        {
            coordinatesDialog.dismiss();
            return;
        }
        super.onBackPressed();
    }
}

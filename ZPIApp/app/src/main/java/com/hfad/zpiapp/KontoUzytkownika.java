package com.hfad.zpiapp;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.provider.MediaStore;
//import android.support.v7.app.ActionBar;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.firebase.ml.vision.common.FirebaseVisionImage;

import androidx.appcompat.app.AppCompatActivity;

import static android.app.Activity.RESULT_OK;

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
        int size=intent.getIntExtra("size",0);
        this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar_2);
        SeekBar sb = (SeekBar) findViewById(R.id.seekBar);
        System.out.println(postep);
        sb.setMax(size);
        sb.setProgress(postep);
        progressDialog = new Dialog(this);
        progressDialog.setCancelable(true);
        progressDialog.setCanceledOnTouchOutside(false);

        ctx = this;
    }

    public void comeBackMethod(View view)
    {
        super.onBackPressed();
    }
}

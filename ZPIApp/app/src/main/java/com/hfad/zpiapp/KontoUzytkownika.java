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
import android.widget.TextView;

import com.google.firebase.ml.vision.common.FirebaseVisionImage;

import androidx.appcompat.app.AppCompatActivity;

import static android.app.Activity.RESULT_OK;

public class KontoUzytkownika extends AppCompatActivity {
    Dialog progressDialog;
    static final int REQUEST_IMAGE_CAPTURE = 1; ////////////////////////////////////////////////////////////////do pobierania obrazu
    Bitmap myPhoto; ///////////////////////////////////////////////////////////////////////////////////////////trzymacz obrazu
    Context ctx;
    Dialog badAnswerDialog;
    Dialog congratulationsDialog;
    boolean museum = false;

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konto_uzytkownika);

        this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar_2);

        progressDialog = new Dialog(this);
        progressDialog.setCancelable(true);
        progressDialog.setCanceledOnTouchOutside(false);

        badAnswerDialog = new Dialog(this);
        congratulationsDialog = new Dialog(this);

        ctx = this;
    }

    public void progressMethod(View view)
    {
            takePhoto();
    }

    private void takePhoto()
    {
        progressDialog.setContentView(R.layout.popup_zrob_zdj);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView closeDialog = (TextView) progressDialog.findViewById(R.id.closeZrobZdjecie);
        final Button takePhoto = (Button) progressDialog.findViewById(R.id.zrobZdjecieButton);
        closeDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.dismiss();
            }
        });
        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textOnButton = takePhoto.getText().toString();
                if(textOnButton.equals("OK"))
                {
                    dispatchTakePictureIntent();
                }
                else if(textOnButton.equals("Wyślij"))
                {
                    TextRecognition tR = new TextRecognition();
                    tR.detectTextFromImage(ctx,myPhoto);
                    Log.println(Log.ASSERT,"cokolwiek", "naprawdę cokolwiek");
                    String textFromImage = tR.getTextFromImage();
                    Log.println(Log.ASSERT,"cokolwiek", textFromImage);
                    museum = MuzeumWspolczesneRecognition.museum(textFromImage);
                    progressDialog.dismiss();
                    if(museum)
                    {
                        congratulationsDialog.setContentView(R.layout.popup_gratulacje);
                        congratulationsDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        congratulationsDialog.show();
                    }
                    else {
                        badAnswerDialog.setContentView(R.layout.popup_zla_odpowiedz);
                        badAnswerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        badAnswerDialog.show();
                    }
                    ///////////////////////////////////////////////////////////////////////////////////////////////////////analizuj
                }
            }
        });
        progressDialog.show();
    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////no pobiera zdjatko
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            myPhoto = imageBitmap;
            //FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(imageBitmap);
            final Button takePhoto = (Button) progressDialog.findViewById(R.id.zrobZdjecieButton);
            takePhoto.setText("Wyślij");
            ImageView photo = (ImageView) progressDialog.findViewById(R.id.miejsceNaZdj);
            photo.setImageBitmap(imageBitmap);
            photo.setVisibility(View.VISIBLE);


        }

    }
}

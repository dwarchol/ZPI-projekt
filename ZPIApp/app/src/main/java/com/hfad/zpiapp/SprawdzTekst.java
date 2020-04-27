package com.hfad.zpiapp;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class SprawdzTekst extends AsyncTask<Void,Integer,Void> {
    Context ctx;
    Dialog loadingDialog;
    ProgressBar circle;
    Bitmap image;
    boolean museum = false;
    Dialog badAnswerDialog;
    Dialog congratulationsDialog;
    KontoUzytkownika ku;
    String textFromImage;
    public SprawdzTekst(Context c, Bitmap img)
    {
        this.ctx=c;
        this.image=img;
        loadingDialog= new Dialog(ctx);
        loadingDialog.setContentView(R.layout.popup_sprawdzanie);
        loadingDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        circle=loadingDialog.findViewById(R.id.loadingCircle);

        badAnswerDialog = new Dialog(ctx);
        congratulationsDialog = new Dialog(ctx);
    }
    @Override
    protected void onPreExecute() {
        loadingDialog.show();
    }
    @Override
    protected Void doInBackground(Void... voids) {
        publishProgress(0);
        CountDownLatch cdl = new CountDownLatch(1);
        publishProgress(10);
        TextRecognition tR = new TextRecognition(ctx,image,cdl);
        publishProgress(30);
        Executor executor = Executors.newFixedThreadPool(1);
        publishProgress(35);
        executor.execute(tR);
        publishProgress(50);
        try {
            cdl.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        publishProgress(70);
        Log.println(Log.ASSERT,"cokolwiek", "naprawdę cokolwiek");
        textFromImage= tR.getTextFromImage();
        publishProgress(100);
        return null;
    }
    @Override
    protected void onProgressUpdate(Integer... progress)
    {
        circle.setProgress(progress[0]);
    }


    @Override
    protected void onPostExecute(Void red)
    {
        loadingDialog.dismiss();
        museum = MuzeumWspolczesneRecognition.museum(textFromImage);

        if(museum)
        {
            congratulationsDialog.setContentView(R.layout.popup_gratulacje);
            congratulationsDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            congratulationsDialog.show();
            Toast.makeText(ctx,textFromImage,Toast.LENGTH_LONG).show();
        }
        else {
            badAnswerDialog.setContentView(R.layout.popup_zla_odpowiedz);
            badAnswerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            badAnswerDialog.show();
            Toast.makeText(ctx,textFromImage,Toast.LENGTH_LONG).show();
        }
    }
}

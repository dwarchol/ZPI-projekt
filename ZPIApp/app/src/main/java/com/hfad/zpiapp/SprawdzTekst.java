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
    boolean czyPoprawna = false;
    Dialog badAnswerDialog;
    Dialog congratulationsDialog;
    Dialog curiosityDialog;
    KontoUzytkownika ku;
    String textFromImage;
    int indexZagadki;
    ZagadkaMLTekst mojaZagadka;

    public SprawdzTekst(Context c, Bitmap img, int index,ZagadkaMLTekst zagadkaMLTekst, Dialog cD, Dialog curD, Dialog bAD)
    {
        this.ctx=c;
        this.image=img;
        this.indexZagadki = index;
        loadingDialog= new Dialog(ctx);
        loadingDialog.setContentView(R.layout.popup_sprawdzanie);
        loadingDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        circle=loadingDialog.findViewById(R.id.loadingCircle);
        mojaZagadka = zagadkaMLTekst;

        badAnswerDialog = bAD;
        if(zagadkaMLTekst==null)
        {
            Log.println(Log.ASSERT, "nulle", "mulle");
        }
        congratulationsDialog = cD;
        curiosityDialog = curD;
    }
    @Override
    protected void onPreExecute() {
        loadingDialog.show();
    }
    @Override
    protected Void doInBackground(Void... voids) {
        publishProgress(0);
        CountDownLatch cdl = new CountDownLatch(1);
        publishProgress(30);
        TextRecognition tR = new TextRecognition(ctx,image,cdl);
        publishProgress(60);
        Executor executor = Executors.newFixedThreadPool(1);
        publishProgress(70);
        executor.execute(tR);
        publishProgress(80);
        try {
            cdl.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        publishProgress(90);
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
        Log.println(Log.ASSERT, "Ending", "Ending");
        if(indexZagadki == 82) {
            czyPoprawna = MuzeumWspolczesneRecognition.museum(textFromImage);
        }
        else
        {
            czyPoprawna = SolnyRecognition.solny(textFromImage);
        }

        Log.println(Log.ASSERT, "TuZmienie", "TuZmienie");
        if(czyPoprawna)
        {
            //////////////////////////////////////////////////////////////////////////////////////////////aktualizacja bazy danych
            mojaZagadka.showCongratulations(congratulationsDialog,curiosityDialog);
            /////////////////////////////////////////////////////////////////////////////////////////////pokazanie kolejnego punktu na mapie
        }
        else {
            mojaZagadka.showFailed(badAnswerDialog);
        }
    }
}

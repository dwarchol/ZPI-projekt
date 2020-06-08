package com.hfad.zpiapp;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ProgressBar;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class SprawdzZdjecie extends AsyncTask<Void, Integer, Void> {
    Context ctx;
    Dialog loadingDialog;
    ProgressBar circle;
    Bitmap image;
    boolean czyPoprawna = false;
    Dialog badAnswerDialog;
    Dialog congratulationsDialog;
    Dialog curiosityDialog;
    KontoUzytkownika ku;
    String labelFromImage;
    int indexZagadki;
    ZagadkaMLObiekty mojaZagadka;
    int nastepna;
    public SprawdzZdjecie(Context c, Bitmap img, int index, ZagadkaMLObiekty zagadkaMLObiekty, Dialog cD, Dialog curD, Dialog bAD, int nastepna)
    {
        this.ctx=c;
        this.image=img;
        this.indexZagadki = index;
        loadingDialog= new Dialog(ctx);
        loadingDialog.setContentView(R.layout.popup_sprawdzanie);
        loadingDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        circle=loadingDialog.findViewById(R.id.loadingCircle);
        mojaZagadka = zagadkaMLObiekty;
        this.nastepna = nastepna;
        badAnswerDialog = bAD;
        congratulationsDialog = cD;
        curiosityDialog = curD;

    }
    @Override
    protected void onPreExecute() {
       System.out.println(mojaZagadka.poprawnaOdpowiedz);
        loadingDialog.show();
    }
    @Override
    protected Void doInBackground(Void... voids) {
        publishProgress(0);
        final CountDownLatch cdl = new CountDownLatch(1);
        publishProgress(30);

        MLLandmark ml = new MLLandmark(image, ctx, cdl);
        publishProgress(50);

        Executor executor = Executors.newFixedThreadPool(1);
        executor.execute(ml);
        try {
            cdl.await();
            publishProgress(75);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        publishProgress(89);
        labelFromImage=ml.sb.toString();
        publishProgress(100);
        return null;

    }
    @Override
    protected void onProgressUpdate(Integer... progress)
    {
        circle.setProgress(progress[0]);
    }
    @Override
    protected void onPostExecute(Void res) {
        loadingDialog.dismiss();
        czyPoprawna=mojaZagadka.sprawdz(labelFromImage);

        if(czyPoprawna)
        {
            ((Glowna) ctx).user.setRozwiazana(indexZagadki, nastepna);
            ((Glowna) ctx).popUpSemafor=false;

            mojaZagadka.showCongratulations(congratulationsDialog,curiosityDialog,ctx);
        }
        else {
            ((Glowna) ctx).popUpSemafor=false;
            mojaZagadka.showFailed(badAnswerDialog,ctx);
        }
    }

}

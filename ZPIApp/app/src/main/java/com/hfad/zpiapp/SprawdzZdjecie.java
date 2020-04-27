package com.hfad.zpiapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.widget.PopupMenu;
import android.widget.Toast;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class SprawdzZdjecie extends AsyncTask<Void, Void, Void> {
    Context ctx;
    Bitmap image;
    KontoUzytkownika ku;
    String s;
    public SprawdzZdjecie(Context c)
    {
        this.ctx=c;
    }

    @Override
    protected Void doInBackground(Void... voids) {

        final CountDownLatch cdl = new CountDownLatch(1);
        Bitmap icon = BitmapFactory.decodeResource(ctx.getResources(), R.drawable.skytower1);
        MLLandmark ml = new MLLandmark(icon, ctx,  3,cdl);
        Executor executor = Executors.newFixedThreadPool(1);
        System.out.println("execute");
        executor.execute(ml);
        try {
            cdl.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        s=ml.sb.toString();
        return null;

    }

    @Override
    protected void onPostExecute(Void res) {
      Dialog  congratulationsDialog = new Dialog(ctx);
        congratulationsDialog.setContentView(R.layout.popup_gratulacje);
        congratulationsDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        congratulationsDialog.show();
        Toast.makeText(ctx,s,Toast.LENGTH_LONG).show();
    }

}

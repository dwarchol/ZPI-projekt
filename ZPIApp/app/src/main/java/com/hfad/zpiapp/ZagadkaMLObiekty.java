package com.hfad.zpiapp;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;


public class ZagadkaMLObiekty extends Zagadka{
    private String trescPytania;
    public String poprawnaOdpowiedz;
    Context ctx;
    Glowna ac;
    Bitmap myPhoto;
    ZagadkaMLObiekty zagadka;
    public ZagadkaMLObiekty(){

    }

    public ZagadkaMLObiekty(int index, String trescPytania, String poprawnaOdpowiedz, double wspolrzednaLat, double wspolrzednaLng, int typ, String nazwa, int poprzednia, String ciekawostka)
    {
        this.index = index;
        this.typ=typ;
        this.trescPytania = trescPytania;
        this.poprawnaOdpowiedz=poprawnaOdpowiedz;
        this.wspolrzednaLat = wspolrzednaLat;
        this.wspolrzednaLng = wspolrzednaLng;
        this.nazwa=nazwa;
        this.poprzednia=poprzednia;
        this.ciekawostka = ciekawostka;
    }


    public String getTrescPytania(){return trescPytania;}

    @Override
    public boolean sprawdz(String odp) {
        String[] toCheck = odp.split(" ");
        Toast.makeText(ctx,odp,Toast.LENGTH_LONG).show();
        for (String s: toCheck)
        {
            if(poprawnaOdpowiedz.contains(s))
                return true;
        }
        return false;

    }
   /* @Override
    public PopupWindow showPopUp(LayoutInflater inflater) {

        PopupWindow pw=null;
        pw = new PopupWindow(inflater.inflate(R.layout.popup_zrob_zdj, null, false), ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        return pw;
    }*/

    public void showPopUp(final Dialog d, final Dialog bAD, final Dialog cD, final Dialog curD)
    {
        zagadka=this;

        d.setCanceledOnTouchOutside(false);
        d.setCancelable(true);
        d.setContentView(R.layout.popup_zrob_zdj);
        d.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
       // Button closeDialog = (Button) d.findViewById(R.id.zrobZdjecieButton);
        ((TextView)d.findViewById(R.id.zrobZdjecie_title)).setText(getTrescPytania());
        final Button takePhoto = (Button) d.findViewById(R.id.zrobZdjecieButton);
        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textOnButton = takePhoto.getText().toString();
                if(textOnButton.equals("OK"))
                {
                    ((Glowna)ctx).dispatchTakePictureIntent();

                }
                else if(textOnButton.equals("Wyślij"))
                {
                    myPhoto = ((Glowna)ctx).myPhoto;
                    SprawdzZdjecie sz = new SprawdzZdjecie(ctx,myPhoto,index,zagadka,cD,curD,bAD);
                    d.dismiss();
                    sz.execute();
                }
            }
        });

        d.show();
    }

    public void setContext(Context c)
    {
        this.ctx = c;
    }
}

package com.hfad.zpiapp;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

public class ZagadkaMLTekst extends Zagadka{
    private String trescPytania;

    public ZagadkaMLTekst(){

    }

    public ZagadkaMLTekst(int index, String trescPytania, double wspolrzednaLat, double wspolrzednaLng, int typ, String nazwa, int poprzednia)
    {
        this.index = index;
        this.typ=typ;
        this.trescPytania = trescPytania;
        this.wspolrzednaLat = wspolrzednaLat;
        this.wspolrzednaLng = wspolrzednaLng;
        this.nazwa=nazwa;
        this.poprzednia=poprzednia;
    }


    public String getTrescPytania(){return trescPytania;}

    @Override
    public boolean sprawdz(String odp) {
        return false;
    }
    /*@Override
    public PopupWindow showPopUp(LayoutInflater inflater) {

        PopupWindow pw=null;
        pw = new PopupWindow(inflater.inflate(R.layout.popup_zrob_zdj, null, false), ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        return pw;
    }*/

    public void showPopUp(final Dialog d, final Dialog bAD, final Dialog cD)
    {
        d.setCanceledOnTouchOutside(false);
        d.setCancelable(true);
        d.setContentView(R.layout.popup_zrob_zdj);
        d.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Button closeDialog = (Button) d.findViewById(R.id.zrobZdjecieButton);
        ((TextView)d.findViewById(R.id.zrobZdjecie_title)).setText(getTrescPytania());
        closeDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                d.dismiss();
            }
        });
        d.show();
    }
}

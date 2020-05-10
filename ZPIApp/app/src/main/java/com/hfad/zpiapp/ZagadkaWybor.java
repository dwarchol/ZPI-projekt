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

import java.util.ArrayList;

public class ZagadkaWybor extends Zagadka{
    private String trescPytania;
    private String zdjecie;
    private String poprawnaOdpowiedz;
    private String odpowiedzA;
    private String odpowiedzB;
    private String odpowiedzC;
    private String odpowiedzD;
    public ZagadkaWybor(){

    }

    public ZagadkaWybor(int index, String trescPytania, String zdjecie, String poprawnaOdpowiedz, double wspolrzednaLat, double wspolrzednaLng, int typ, String nazwa, int poprzednia, String odpowiedzA, String odpowiedzB, String odpowiedzC, String odpowiedzD)
    {
        this.index = index;
        this.typ=typ;
        this.trescPytania = trescPytania;
        this.zdjecie = zdjecie;
        this.poprawnaOdpowiedz = poprawnaOdpowiedz;
        this.wspolrzednaLat = wspolrzednaLat;
        this.wspolrzednaLng = wspolrzednaLng;
        this.nazwa=nazwa;
        this.poprzednia=poprzednia;
        this.odpowiedzA = odpowiedzA;
        this.odpowiedzB = odpowiedzB;
        this.odpowiedzC = odpowiedzC;
        this.odpowiedzD = odpowiedzD;
    }

    public String getTrescPytania(){ return trescPytania; }
    public String getZdjecie(){ return zdjecie; }
    public String getPoprawnaOdpowiedz() { return poprawnaOdpowiedz; }
    public String getOdpowiedzA(){return odpowiedzA;}
    public String getOdpowiedzB(){return odpowiedzB;}
    public String getOdpowiedzC(){return odpowiedzC;}
    public String getOdpowiedzD(){return odpowiedzD;}

    @Override
    public boolean sprawdz(String str) {
//        if(poprawnaOdpowiedz.get(0).equals(udzielonaOdpowiedz))
//        {
//            rozwiazana = true;
//        }
        return false;
    }
    /*@Override
    public PopupWindow showPopUp(LayoutInflater inflater) {

        PopupWindow pw=null;
        pw = new PopupWindow(inflater.inflate(R.layout.popup_gratulacje, null, false), ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        return pw;
    }*/

    public void showPopUp(final Dialog d)
    {
        d.setCanceledOnTouchOutside(false);
        d.setCancelable(true);
        d.setContentView(R.layout.custom_popup_coordinates);
        d.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Button closeDialog = (Button) d.findViewById(R.id.closeCoordinates);
       // ((TextView)d.findViewById(R.id.pytanie_title)).setText(getTrescPytania());
        closeDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                d.dismiss();
            }
        });
        d.show();
    }
}

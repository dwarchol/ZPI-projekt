package com.hfad.zpiapp;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.List;

public abstract class Zagadka {
    protected int index;
    protected double wspolrzednaLat;
    protected double wspolrzednaLng;
    protected int typ;      //1 - pytanie, 2- wybor, 3- mlObiekty 4- mlTekst 5-dotarcieNaMiejsce
    protected String nazwa;
    protected int poprzednia;
    protected String ciekawostka;
    Context ctx;

    public abstract boolean sprawdz(String odp);

    public int getIndex() {return index;}

    public String getNazwa()
    {
        return nazwa;
    }

    public double getTyp() { return typ; }

    public double getWspolrzednaLat()
    {
        return wspolrzednaLat;
    }

    public double getWspolrzednaLng(){ return wspolrzednaLng; }

    public int getPoprzednia() { return poprzednia; }

    public String getCiekawostka() {return  ciekawostka;}

    public boolean czyNaMiejscu(String str)
    {
        String[] wsp = str.split(",");
        double lat = Double.parseDouble(wsp[0]);
        double lng = Double.parseDouble(wsp[1]);

        double distance = Math.sqrt((wspolrzednaLat-lat)*(wspolrzednaLat-lat) + (wspolrzednaLng-lng)*(wspolrzednaLng-lng));
        if(distance<0.001){
            return true;
        }
        else{
            return false;
        }
    }

   // abstract public PopupWindow showPopUp(LayoutInflater inflater);
   abstract public void showPopUp(Dialog d, final Dialog bAD, final Dialog cD, final Dialog curD);

    public void showCongratulations(final Dialog cD, final Dialog curD)
    {
        ((Glowna)ctx).sound.correctSound();
        final Button showCuriosity = (Button) cD.findViewById(R.id.closeGratulacje);
        showCuriosity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cD.dismiss();
                showCuriosity(curD);
            }
        });
        cD.show();
    }

    public void showFailed(final Dialog bAD)
    {
        ((Glowna)ctx).sound.wrongSound();
        Button showPopupAgain = (Button) bAD.findViewById(R.id.closeZlaOdpowiedz);
        showPopupAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bAD.dismiss();
               // showPopUp(d, bAD, cD);
            }
        });
        bAD.show();
    }

    public void showCuriosity(final Dialog curD)
    {
        final Button closeCuriosity = (Button)curD.findViewById(R.id.closeCoiekawostka);
        TextView curiosityText = (TextView)curD.findViewById(R.id.ciekawostka_text);
        curiosityText.setText(ciekawostka);
        closeCuriosity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                curD.dismiss();
            }
        });
        curD.show();
    }

    public void setContext(Context c)
    {
        this.ctx = c;
    }
}

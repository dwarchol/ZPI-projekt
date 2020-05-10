package com.hfad.zpiapp;

import android.view.LayoutInflater;
import android.widget.PopupWindow;

import java.util.List;

public abstract class Zagadka {
    protected int index;
    protected double wspolrzednaLat;
    protected double wspolrzednaLng;
    protected int typ;      //1 - pytanie, 2- wybor, 3- mlObiekty 4- mlTekst 5-dotarcieNaMiejsce
    protected String nazwa;
    protected int poprzednia;

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

    public boolean czyNaMiejscu(String str)
    {
        String[] wsp = str.split(",");
        double lat = Double.parseDouble(wsp[0]);
        double lng = Double.parseDouble(wsp[1]);

        double distance = Math.sqrt((wspolrzednaLat-lat)*(wspolrzednaLat-lat) - (wspolrzednaLng-lng)*(wspolrzednaLng-lng));
        if(distance<0.001){
            return true;
        }
        else{
            return false;
        }
    }

    abstract public PopupWindow showPopUp(LayoutInflater inflater);

}

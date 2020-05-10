package com.hfad.zpiapp;

import java.util.ArrayList;

import androidx.annotation.NonNull;

public class ZagadkaPytanie extends Zagadka{
    private String trescPytania;
    private String zdjecie;
    private String poprawnaOdpowiedz;
    public ZagadkaPytanie(){

    }

    public ZagadkaPytanie(int index, String trescPytania, String zdjecie, String poprawnaOdpowiedz, double wspolrzednaLat, double wspolrzednaLng, int typ, String nazwa, int poprzednia)
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
    }

    public String getTrescPytania(){ return trescPytania; }
    public String getZdjecie(){ return zdjecie; }
    public String getPoprawnaOdpowiedz() { return poprawnaOdpowiedz; }

    public boolean sprawdz(String Odp)
    {
        String[] odpowiedzi = poprawnaOdpowiedz.split(",");
        Odp = Odp.toUpperCase();

        for(int i=0;i<odpowiedzi.length; i++){
            if(Odp.equals(odpowiedzi[i]))
            {
                return true;
            }
        }

        return false;
    }


    @NonNull
    @Override
    public String toString() {
        return getIndex() + " " + getPoprzednia() + " " + getNazwa() + " " + getWspolrzednaLat() + " " + getWspolrzednaLng() + " " + getTrescPytania() + " " + getPoprawnaOdpowiedz() + " " + getZdjecie() + " " + getTyp();
    }
}

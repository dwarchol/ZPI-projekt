package com.hfad.zpiapp;

import java.util.ArrayList;

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

    public boolean sprawdz(String Odp)
    {
        if(poprawnaOdpowiedz.equals(Odp))
        {
                return true;
        }
        return false;
    }

    public String getPytanie()
    {
        return trescPytania;
    }

//    public String getZdjecie()
//    {
//        return zdjecie;
//    }

}

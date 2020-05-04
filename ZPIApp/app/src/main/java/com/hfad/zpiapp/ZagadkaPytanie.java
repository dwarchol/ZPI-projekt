package com.hfad.zpiapp;

import java.util.ArrayList;

public class ZagadkaPytanie extends Zagadka{
    private String trescPytania;
   // private String zdjecie;
    private String poprawnaOdpowiedz;
    public ZagadkaPytanie(){

    }

    public ZagadkaPytanie(int index, String pyt,/* String zdj,*/ String poprOdp, double wspolrzedna_lat, double wspolrzedna_lng, int typ)
    {
        this.index = index;
        this.typ=typ;
        trescPytania = pyt;
      //  zdjecie = zdj;
        poprawnaOdpowiedz = poprOdp;
        wspolrzednaLat = wspolrzedna_lat;
        wspolrzednaLng = wspolrzedna_lng;
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

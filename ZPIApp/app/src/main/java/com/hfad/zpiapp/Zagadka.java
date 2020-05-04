package com.hfad.zpiapp;

import java.util.List;

public abstract class Zagadka {
    public int index;
    protected double wspolrzednaLat;
    protected double wspolrzednaLng;
    protected int typ;      //1 - pytanie, 2- wybor, 3- mlObiekty 4- mlTekst

    public abstract boolean sprawdz(String odp);

    public double getTyp() { return typ; }

    public double getWspolrzednaLat()
    {
        return wspolrzednaLat;
    }

    public double getWspolrzednaLng()
    {
        return wspolrzednaLng;
    }

}

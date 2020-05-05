package com.hfad.zpiapp;

public class ZagadkaMLObiekty extends Zagadka{
    private String trescPytania;

    public ZagadkaMLObiekty(){

    }

    public ZagadkaMLObiekty(int index, String trescPytania, double wspolrzednaLat, double wspolrzednaLng, int typ, String nazwa, int poprzednia)
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
}

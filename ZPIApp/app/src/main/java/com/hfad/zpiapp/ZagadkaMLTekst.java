package com.hfad.zpiapp;

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
}

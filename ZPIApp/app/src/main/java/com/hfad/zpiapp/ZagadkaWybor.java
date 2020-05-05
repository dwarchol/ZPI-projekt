package com.hfad.zpiapp;

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

}

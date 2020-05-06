package com.hfad.zpiapp;

public class ZagadkaStartowa extends Zagadka{
    public ZagadkaStartowa(){

    }


    public ZagadkaStartowa(int index, double wspolrzednaLat, double wspolrzednaLng, int typ, String nazwa, int poprzednia)
    {
        this.index = index;
        this.wspolrzednaLat = wspolrzednaLat;
        this.wspolrzednaLng = wspolrzednaLng;
        this.typ = typ;
        this.nazwa = nazwa;
        this.poprzednia = poprzednia;
    }

    @Override
    public boolean sprawdz(String str) {
        String[] wsp = str.split(",");
        double lat = Double.parseDouble(wsp[0]);
        double lng = Double.parseDouble(wsp[1]);

        double distance = Math.sqrt((wspolrzednaLat-lat)*(wspolrzednaLat-lat) - (wspolrzednaLng-lng)*(wspolrzednaLng-lng));
        if(distance<0.01){
            return true;
        }
        else{
            return false;
        }

    }
}

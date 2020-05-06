package com.hfad.zpiapp;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

public class ZagadkaDotarcieNaMiejsce extends Zagadka{

    public ZagadkaDotarcieNaMiejsce(){

    }

    public ZagadkaDotarcieNaMiejsce(int index, double wspolrzednaLat, double wspolrzednaLng, int typ, String nazwa, int poprzednia)
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

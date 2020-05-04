package com.hfad.zpiapp;

public class ZagadkaDotarcieNaMiejsce extends Zagadka{
    private String zagadka;
    private String poprawnaOdpowiedz;
    public ZagadkaDotarcieNaMiejsce(String z, double x, double y)
    {
        zagadka= z;
        poprawnaOdpowiedz = null;
        wspolrzednaLat = x;
        wspolrzednaLng = y;
    }
    public String getZagadka()
    {
        return zagadka;
    }

    @Override
    public boolean sprawdz(String str) {
        //////////////////////////////////////////////////tu sprawdza czy zagadzają się współrzędne
    return false;
    }
}

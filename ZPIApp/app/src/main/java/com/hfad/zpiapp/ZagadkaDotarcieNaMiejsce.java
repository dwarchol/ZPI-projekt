package com.hfad.zpiapp;

public class ZagadkaDotarcieNaMiejsce extends Zagadka{
    private String zagadka;

    public ZagadkaDotarcieNaMiejsce(String z, double x, double y)
    {
        zagadka= z;
        poprawnaOdpowiedz = null;
        rozwiazana = false;
        wspolrzednaX = x;
        wspolrzednaY = y;
    }
    public String getZagadka()
    {
        return zagadka;
    }

    @Override
    public void sprawdz() {
        //////////////////////////////////////////////////tu sprawdza czy zagadzają się współrzędne
    }
}

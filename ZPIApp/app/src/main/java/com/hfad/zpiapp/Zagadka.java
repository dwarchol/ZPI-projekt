package com.hfad.zpiapp;

import java.util.List;

public abstract class Zagadka {
    protected List<String> poprawnaOdpowiedz;
    protected String udzielonaOdpowiedz;
    protected Boolean rozwiazana;
    protected double wspolrzednaX;
    protected double wspolrzednaY;

    public abstract void sprawdz();
    public Boolean czyRozwiazane()
    {
        return rozwiazana;
    }
    public void setUdzielonaOdpowiedz(String uO)
    {
        udzielonaOdpowiedz = uO.toUpperCase();
    };

    public double getWspolrzednaX()
    {
        return wspolrzednaX;
    }

    public double getWspolrzednaY()
    {
        return wspolrzednaY;
    }
}

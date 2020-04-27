package com.hfad.zpiapp;

import java.util.List;

public abstract class Zagadka {
    protected List<String> poprawnaOdpowiedz;
    protected String udzielonaOdpowiedz;
    protected Boolean rozwiazana;

    public abstract void sprawdz();
    public Boolean czyRozwiazane()
    {
        return rozwiazana;
    }
    public void setUdzielonaOdpowiedz(String uO)
    {
        udzielonaOdpowiedz = uO;
    };
}

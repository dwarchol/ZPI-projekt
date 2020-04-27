package com.hfad.zpiapp;

import java.util.ArrayList;

public class ZagadkaWybor extends Zagadka{
    private String pytanie;
    private String zdjecie;
    private String opcja1;
    private String opcja2;
    private String opcja3;
    private String opcja4;

    public ZagadkaWybor(String p, String z, String pO, double x, double y, String o1, String o2, String o3, String o4)
    {
        pytanie = p;
        zdjecie = z;
        poprawnaOdpowiedz = new ArrayList<String>();
        poprawnaOdpowiedz.add(pO);
        rozwiazana = false;
        wspolrzednaX = x;
        wspolrzednaY = y;
        opcja1 = o1;
        opcja2 = o2;
        opcja3 = o3;
        opcja4 = o4;
    }

    @Override
    public void sprawdz() {
        if(poprawnaOdpowiedz.get(0).equals(udzielonaOdpowiedz))
        {
            rozwiazana = true;
        }
    }

    public String getOpcja1()
    {
        return opcja1;
    }

    public String getOpcja2()
    {
        return opcja2;
    }

    public String getOpcja3()
    {
        return opcja3;
    }

    public String getOpcja4()
    {
        return opcja4;
    }

    public String getPytanie()
    {
        return pytanie;
    }

    public String getZdjecie()
    {
        return zdjecie;
    }
}

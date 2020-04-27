package com.hfad.zpiapp;

import java.util.ArrayList;

public class ZagadkaPytanie extends Zagadka{
    private String pytanie;
    private String zdjecie;

    public ZagadkaPytanie(String p, String z, ArrayList<String> pO, double x, double y)
    {
        pytanie = p;
        zdjecie = z;
        poprawnaOdpowiedz = pO;
        rozwiazana = false;
        wspolrzednaX = x;
        wspolrzednaY = y;
    }

    public void sprawdz()
    {
        for(int i = 0; i < poprawnaOdpowiedz.size(); i++)
        {
            if(poprawnaOdpowiedz.get(i).equals(udzielonaOdpowiedz))
            {
                rozwiazana = true;
            }
        }
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

package com.hfad.zpiapp;

import java.util.ArrayList;

public class ZagadkaPytanie extends Zagadka{
    private String pytanie;
    private String zdjecie;

    public ZagadkaPytanie(String p, String z, String pO)
    {
        pytanie = p;
        zdjecie = z;
        poprawnaOdpowiedz = new ArrayList<String>();
        rozwiazana = false;
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

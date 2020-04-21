package com.hfad.zpiapp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

public class Uzytkownik {
    String login;
    String password;
   public ArrayList<Integer> odznaki;
    ArrayList<ArrayList<Integer>> zagadki;



    public Uzytkownik()
    {
        login="";
        password="";
    }
    public Uzytkownik(String l, String p)
    {
        login=l;
        password=p;
        odznaki=new ArrayList<Integer>();
        zagadki= new ArrayList<ArrayList<Integer>>();
        ArrayList a1 = new ArrayList<Integer>();
        a1.add(0);
        a1.add(0);
        ArrayList a2 = new ArrayList<Integer>();
        a2.add(0);
        a2.add(0);
        zagadki.add(a1);
        zagadki.add(a2);
        for(int i=0;i<20;i++)
        {
            odznaki.add(0);
        }

    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public ArrayList<Integer> getOdznaki() {
        return odznaki;
    }

    public void setOdznaki(ArrayList<Integer> odznaki) {
        this.odznaki = odznaki;
    }

    public ArrayList<ArrayList<Integer>> getZagadki() {
        return zagadki;
    }

    public void setZagadki(ArrayList<ArrayList<Integer>> zagadki) {
        this.zagadki = zagadki;
    }
}

package com.hfad.zpiapp;

import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;

public class Uzytkownik implements Serializable {
    String login;
    String password;

   public ArrayList<Integer> zagadkiRozwiazane;
   public ArrayList<Integer> zagadkiAktualne;


    public Uzytkownik()
    {
        login="";
        password="";
    }
    public Uzytkownik(String l, String p, ArrayList<Integer> zagadkiW,  ArrayList<Integer> zagadkiR)
    {
        this.login=l;
        this.password=p;

        this.zagadkiRozwiazane= new ArrayList<>(zagadkiR);
        this.zagadkiAktualne= new ArrayList<>(zagadkiW);

    }
    public Uzytkownik(String l, String p)
    {
        login=l;
        password=p;

        zagadkiRozwiazane = new ArrayList<>(11);
        zagadkiAktualne = new ArrayList<>();
        for(int i=10;i<101;i=i+10){
            zagadkiAktualne.add(i);
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

    public void setRozwiazana(int i, int nastepna)
    {
        Log.println(Log.ASSERT, "Reasuming", "chuj");
        if(zagadkiRozwiazane==null)
        {
            zagadkiRozwiazane=new ArrayList<>(11);
        }
        zagadkiRozwiazane.add(i);
        zagadkiAktualne.remove(Integer.valueOf(i));
        if(nastepna != -1){
            zagadkiAktualne.add(nastepna);
        }
        uaktualnijWBazie();
    }


    public void uaktualnijWBazie()
    {
       FirebaseDB fbdb = new FirebaseDB(this);
       fbdb.updateUser();
    }

    public boolean jestWAktywnych(int i){
        for(Integer zagadka: zagadkiAktualne){
            if(zagadka.equals(new Integer(i))){
                return true;
            }
        }
        return false;
    }

}

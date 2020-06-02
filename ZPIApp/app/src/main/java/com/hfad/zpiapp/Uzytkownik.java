package com.hfad.zpiapp;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class Uzytkownik implements Serializable {
    String login;
    String password;
    String wspolrzedne;
    int pierwszyRaz=0;


   public ArrayList<Integer> zagadkiRozwiazane;
   public ArrayList<Integer> zagadkiAktualne;

    public ArrayList<Integer> wspolrzedneDoDostania;
    public ArrayList<Integer> kolejneMiejsca;

    public Uzytkownik()
    {
        login="";
        password="";
        wspolrzedne =" _ _ ° _ _ . _ _ _ \" N _ _ ° _ _ . _ _ _ \" E";
        Integer [] wsp = {5,7,1,2,1,2,3,1,4,9,8,7,6,5};
        wspolrzedneDoDostania= new ArrayList<Integer>(Arrays.asList(wsp));
        Integer [] kolejny ={1,1,6,6,1,1,4,4,1,3,1,2,1,1};
        kolejneMiejsca = new ArrayList<Integer>(Arrays.asList(kolejny));
        pierwszyRaz = 0;
        zagadkiRozwiazane = new ArrayList<>();
        zagadkiAktualne = new ArrayList<>();
    }
    public Uzytkownik(String l, String p, ArrayList<Integer> zagadkiW,  ArrayList<Integer> zagadkiR, String wspolrzedne, ArrayList<Integer> wsp, ArrayList<Integer> kol, Boolean pierwszyRaz)
    {
        this.login=l;
        this.password=p;

        this.zagadkiRozwiazane= new ArrayList<>(zagadkiR);
        this.zagadkiAktualne= new ArrayList<>(zagadkiW);
        this.wspolrzedne = wspolrzedne;
        this.wspolrzedneDoDostania = wsp;
        this.kolejneMiejsca = kol;
        this.pierwszyRaz = 0;

    }
    public Uzytkownik(String l, String p)
    {
        login=l;
        password=p;

        zagadkiRozwiazane = new ArrayList<>(11);
        zagadkiAktualne = new ArrayList<>();
        wspolrzedne =" _ _ ° _ _ . _ _ _ \" N _ _ ° _ _ . _ _ _ \" E";
        Integer [] wsp = {5,7,1,2,1,2,3,1,4,9,8,7,6,5};
        wspolrzedneDoDostania= new ArrayList<Integer>(Arrays.asList(wsp));
        Integer [] kolejny ={1,1,6,6,1,1,4,4,1,3,1,2,1,1};
        kolejneMiejsca = new ArrayList<Integer>(Arrays.asList(kolejny));
        for(int i=10;i<101;i=i+10){
            zagadkiAktualne.add(i);
        }
        pierwszyRaz = 0;
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

    public String getWspolrzedne() { return wspolrzedne;}

    public void setWspolrzedne(String  wsp) {this.wspolrzedne = wsp;}

    public void otworzone(){
        pierwszyRaz=1;
    }

    public void setRozwiazana(int i, int nastepna)
    {
        if(zagadkiRozwiazane==null)
        {
            zagadkiRozwiazane=new ArrayList<>(11);
        }

            zagadkiRozwiazane.add(i);
            zagadkiAktualne.remove(Integer.valueOf(i));
            if (nastepna != -1) {
                zagadkiAktualne.add(nastepna);
            } else {
                int miejsce = kolejneMiejsca.get(0);
                uzupelnijNapisOCyfre(wspolrzedneDoDostania.get(miejsce - 1), miejsce);
                wspolrzedneDoDostania.remove(miejsce - 1);
                kolejneMiejsca.remove(0);
                if (!(i == 53 || i == 62 || i == 82 || i == 12)) {
                    miejsce = kolejneMiejsca.get(0);
                    uzupelnijNapisOCyfre(wspolrzedneDoDostania.get(miejsce - 1), miejsce);
                    wspolrzedneDoDostania.remove(miejsce - 1);
                    kolejneMiejsca.remove(0);
                }
            }
            if(zagadkiRozwiazane.size() == 47)
            {
                /////////////////////////////////////////////////////////////////////////////////////////sama koncowka
               // zagadkiAktualne.add(1000);
            }

        uaktualnijWBazie();
    }



    public void uaktualnijWBazie()
    {
       FirebaseDB fbdb = new FirebaseDB(this);
       fbdb.updateUser();
    }

    public void uzupelnijNapisOCyfre(Integer cyfra, Integer miejsce)
    {
        StringBuilder sB = new StringBuilder("");
        Log.println(Log.ASSERT, "cyfra", cyfra.toString());
        Log.println(Log.ASSERT, "miejsce", miejsce.toString());
        for(int i = 0; i < wspolrzedne.length(); i ++)
        {

            if(wspolrzedne.charAt(i)=='_')
            {
                miejsce--;
                if(miejsce == 0)
                {
                    sB.append(wspolrzedne.substring(0,i));
                    sB.append(cyfra);
                    sB.append(wspolrzedne.substring(i+1));
                }
            }
        }
        wspolrzedne = sB.toString();
    }

    public boolean jestWAktywnych(int i){
        for(Integer zagadka: zagadkiAktualne){
            if(zagadka.equals(new Integer(i))){
                return true;
            }
        }
        return false;
    }

    public boolean jestWRozwiazanych(int i){
        for(Integer zagadka: zagadkiRozwiazane){
            if(zagadka.equals(new Integer(i))){
                return true;
            }
        }
        return false;
    }

}

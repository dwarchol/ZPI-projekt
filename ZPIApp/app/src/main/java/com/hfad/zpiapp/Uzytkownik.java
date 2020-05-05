package com.hfad.zpiapp;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import androidx.annotation.NonNull;

public class Uzytkownik implements Serializable {
    String login;
    String password;
   public ArrayList<Integer> odznaki;
   public ArrayList<ArrayList<Integer>> zagadki;



    public Uzytkownik()
    {
        login="";
        password="";
    }
    public Uzytkownik(String l, String p, ArrayList<Integer> od, ArrayList<Integer> zag)
    {
        this.login=l;
        this.password=p;
        this.odznaki=new ArrayList<>(od);
        this.zagadki=new ArrayList(zag);

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

    public void uaktualnijWBazie()
    {
       FirebaseDB fbdb = new FirebaseDB(this);
       fbdb.updateUser();
    }

}

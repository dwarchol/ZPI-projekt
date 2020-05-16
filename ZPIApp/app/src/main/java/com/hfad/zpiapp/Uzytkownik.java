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

   public ArrayList<Integer> zagadki;



    public Uzytkownik()
    {
        login="";
        password="";
    }
    public Uzytkownik(String l, String p, ArrayList<Integer> zagadki)
    {
        this.login=l;
        this.password=p;

        this.zagadki=new ArrayList(zagadki);

    }
    public Uzytkownik(String l, String p)
    {
        login=l;
        password=p;

        zagadki= new ArrayList<Integer>();
        for(int i=0;i<44;i++)
        {
            zagadki.add(0);
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


    public ArrayList<Integer> getZagadki() {
        return zagadki;
    }

    public void setZagadki(ArrayList<Integer> zagadki) {
        this.zagadki = zagadki;
    }

    public void setRozwiązana(int i)
    {
        zagadki.set(i,1);
        uaktualnijWBazie();
    }


    public void uaktualnijWBazie()
    {
       FirebaseDB fbdb = new FirebaseDB(this);
       fbdb.updateUser();
    }

}

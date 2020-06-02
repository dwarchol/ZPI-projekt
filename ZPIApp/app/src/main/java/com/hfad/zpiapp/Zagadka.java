package com.hfad.zpiapp;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public abstract class Zagadka {
    protected int index;
    protected double wspolrzednaLat;
    protected double wspolrzednaLng;
    protected int typ;      //1 - pytanie, 2- wybor, 3- mlObiekty 4- mlTekst 5-dotarcieNaMiejsce
    protected String nazwa;
    protected int poprzednia;
    protected String ciekawostka;
    protected int nastepna;
    Context ctx;


    public abstract boolean sprawdz(String odp);

    public int getIndex() {return index;}

    public String getNazwa()
    {
        return nazwa;
    }

    public double getTyp() { return typ; }

    public double getWspolrzednaLat()
    {
        return wspolrzednaLat;
    }

    public double getWspolrzednaLng(){ return wspolrzednaLng; }

    public int getPoprzednia() { return poprzednia; }

    public String getCiekawostka() {return  ciekawostka;}

    public int getNastepna() {return  nastepna;}

    public boolean czyNaMiejscu(String str)
    {
        String[] wsp = str.split(",");
        double lat = Double.parseDouble(wsp[0]);
        double lng = Double.parseDouble(wsp[1]);

        double distance = Math.sqrt((wspolrzednaLat-lat)*(wspolrzednaLat-lat) + (wspolrzednaLng-lng)*(wspolrzednaLng-lng));
        if(distance<0.001){
            return true;
        }
        else{
            return false;
        }
    }

   // abstract public PopupWindow showPopUp(LayoutInflater inflater);
   abstract public void showPopUp(Dialog d, final Dialog bAD, final Dialog cD, final Dialog curD);

    public void showCiekawostka(Dialog d, final Dialog curD){
        ((Glowna)ctx).popUpSemafor=false;
        showCuriosity(curD,ctx);
    }



    public void showCongratulations(final Dialog cD, final Dialog curD, final Context ctx)
    {
        if(ctx!=null)
        {
            ((Glowna) ctx).sound.correctSound();
        }
        else
        {
          //  System.out.println("Nulllik");
        }
        final Button showCuriosity = (Button) cD.findViewById(R.id.closeGratulacje);
        showCuriosity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cD.dismiss();
                showCuriosity(curD,ctx);
            }
        });
        cD.show();

    }

    public void showFailed(final Dialog bAD, Context ctx)
    {

        ((Glowna)ctx).sound.wrongSound();
        Button showPopupAgain = (Button) bAD.findViewById(R.id.closeZlaOdpowiedz);
        showPopupAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bAD.dismiss();
                // showPopUp(d, bAD, cD);
            }
        });
        bAD.show();
    }

    public void showCuriosity(final Dialog curD, final Context ctx)
    {
        final Button closeCuriosity = (Button)curD.findViewById(R.id.closeCoiekawostka);
        TextView curiosityText = (TextView)curD.findViewById(R.id.ciekawostka_text);
        curiosityText.setText(ciekawostka);
        ((Glowna)ctx).mMap.clear();
        ((Glowna)ctx).drawMapsStartowe();
        closeCuriosity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                curD.dismiss();
              //  ((Glowna)ctx).user.uaktualnijWBazie();


            }
        });
        curD.show();
    }

    public void setContext(Context c)
    {
        this.ctx = c;
    }
}

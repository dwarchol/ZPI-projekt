package com.hfad.zpiapp;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

public class ZagadkaStartowa extends Zagadka{
    public ZagadkaStartowa(){

    }


    public ZagadkaStartowa(int index, double wspolrzednaLat, double wspolrzednaLng, int typ, String nazwa, int poprzednia)
    {
        this.index = index;
        this.wspolrzednaLat = wspolrzednaLat;
        this.wspolrzednaLng = wspolrzednaLng;
        this.typ = typ;
        this.nazwa = nazwa;
        this.poprzednia = poprzednia;
    }

    @Override
    public boolean sprawdz(String str) {
        String[] wsp = str.split(",");
        double lat = Double.parseDouble(wsp[0]);
        double lng = Double.parseDouble(wsp[1]);

        double distance = Math.sqrt((wspolrzednaLat-lat)*(wspolrzednaLat-lat) - (wspolrzednaLng-lng)*(wspolrzednaLng-lng));
        if(distance<0.01){
            return true;
        }
        else{
            return false;
        }

    }
   /* @Override
    public PopupWindow showPopUp(LayoutInflater inflater) {

        PopupWindow pw=null;
        pw = new PopupWindow(inflater.inflate(R.layout.popup_gratulacje, null, false), ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        return pw;
    }*/

   public void showPopUp(final Dialog d, final Dialog bAD, final Dialog cD)
   {
       d.setCanceledOnTouchOutside(false);
       d.setCancelable(true);
       d.setContentView(R.layout.custom_popup_coordinates);
       d.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
       Button closeDialog = (Button) d.findViewById(R.id.closeCoordinates);
       closeDialog.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               d.dismiss();
           }
       });
       d.show();
   }
}
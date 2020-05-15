package com.hfad.zpiapp;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

public class ZagadkaDotarcieNaMiejsce extends Zagadka{

    private String trescPytania;
    private String poprawnaOdpowiedz;

    public ZagadkaDotarcieNaMiejsce(){

    }

    public ZagadkaDotarcieNaMiejsce(int index, String poprawnaOdpowiedz, double wspolrzednaLat, double wspolrzednaLng, int typ, String nazwa, int poprzednia, String trescPytania)
    {
        this.index = index;
        this.wspolrzednaLat = wspolrzednaLat;
        this.wspolrzednaLng = wspolrzednaLng;
        this.poprawnaOdpowiedz = poprawnaOdpowiedz;
        this.typ = typ;
        this.nazwa = nazwa;
        this.poprzednia = poprzednia;
        this.trescPytania = trescPytania;
    }

    public String getTrescPytania(){return trescPytania;}

    @Override
    public boolean sprawdz(String str) {
        String[] wsp = str.split(",");
        double lat = Double.parseDouble(wsp[0]);
        double lng = Double.parseDouble(wsp[1]);

        String[] wspCelu = poprawnaOdpowiedz.split(",");
        double latCelu = Double.parseDouble(wspCelu[0]);
        double lngCelu = Double.parseDouble(wspCelu[1]);

        double distance = Math.sqrt((latCelu-lat)*(latCelu-lat) + (lngCelu-lng)*(lngCelu-lng));
        if(distance<0.0008){
            return true;
        }
        else{
            return false;
        }

    }

    /*@Override
    public PopupWindow showPopUp(LayoutInflater inflater) {

        PopupWindow pw=null;
        pw = new PopupWindow(inflater.inflate(R.layout.popup_idz_do, null, false), ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        return pw;
    }*/

    public void showPopUp(final Dialog d, final Dialog bAD, final Dialog cD)
    {
        d.setCanceledOnTouchOutside(false);
        d.setCancelable(true);
        d.setContentView(R.layout.popup_idz_do);
        d.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Button closeDialog = (Button) d.findViewById(R.id.closeIdzDo);
        ((TextView)d.findViewById(R.id.idzDo_title)).setText(getTrescPytania());
        closeDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                d.dismiss();
            }
        });
        d.show();
    }


}

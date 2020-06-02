package com.hfad.zpiapp;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ZagadkaStartowa extends Zagadka{

    public ZagadkaStartowa(){

    }


    public ZagadkaStartowa(int index, double wspolrzednaLat, double wspolrzednaLng, int typ, String nazwa, int poprzednia, String ciekawostka, int nastepna)
    {
        this.index = index;
        this.wspolrzednaLat = wspolrzednaLat;
        this.wspolrzednaLng = wspolrzednaLng;
        this.typ = typ;
        this.nazwa = nazwa;
        this.poprzednia = poprzednia;
        this.ciekawostka = ciekawostka;
        this.nastepna = nastepna;
    }


    @Override
    public boolean sprawdz(String str) {
        String[] wsp = str.split(",");
        double lat = Double.parseDouble(wsp[0]);
        double lng = Double.parseDouble(wsp[1]);

        double distance = Math.sqrt((wspolrzednaLat-lat)*(wspolrzednaLat-lat) - (wspolrzednaLng-lng)*(wspolrzednaLng-lng));
        if(distance<0.01){
            ((Glowna) ctx).popUpSemafor=false;
            return true;
        }
        else{
            ((Glowna) ctx).popUpSemafor=false;
            return false;
        }

    }
   /* @Override
    public PopupWindow showPopUp(LayoutInflater inflater) {

        PopupWindow pw=null;
        pw = new PopupWindow(inflater.inflate(R.layout.popup_gratulacje, null, false), ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        return pw;
    }*/

   public void showPopUp(final Dialog d, final Dialog bAD, final Dialog cD, final Dialog curD)
   {
       d.setCanceledOnTouchOutside(false);
       d.setCancelable(true);
       d.setContentView(R.layout.popup_idz_do);
       d.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
       ((TextView)d.findViewById(R.id.idzDo_title)).setText(getCiekawostka());
       Button closeDialog = (Button) d.findViewById(R.id.closeIdzDo);
       closeDialog.setText("OK");
       closeDialog.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               ((Glowna)ctx).popUpSemafor=false;
               ((Glowna) ctx).showNext(nastepna);
               ((Glowna) ctx).user.setRozwiazana(index,nastepna);
               ((Glowna)ctx).mMap.clear();
               ((Glowna)ctx).drawMapsStartowe();
               d.dismiss();
           }
       });
       d.setOnDismissListener(new DialogInterface.OnDismissListener() {
           @Override
           public void onDismiss(DialogInterface dialog) {
               ((Glowna)ctx).popUpSemafor=false;
           }
       });
       if(!((Glowna)ctx).isFinishing())
       {
           d.show();
       }
   }
    @Override
    public boolean czyNaMiejscu(String str) {
        String[] wsp = str.split(",");
        double lat = Double.parseDouble(wsp[0]);
        double lng = Double.parseDouble(wsp[1]);

        double distance = Math.sqrt((wspolrzednaLat - lat) * (wspolrzednaLat - lat) + (wspolrzednaLng - lng) * (wspolrzednaLng - lng));
        if (distance < 0.0015) {
            return true;
        } else {
            return false;
        }
    }
}

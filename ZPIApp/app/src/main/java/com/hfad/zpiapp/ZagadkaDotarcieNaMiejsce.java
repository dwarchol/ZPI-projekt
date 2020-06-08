package com.hfad.zpiapp;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ZagadkaDotarcieNaMiejsce extends Zagadka{

    private String trescPytania;
    private String poprawnaOdpowiedz;
    Context ctx;

    public ZagadkaDotarcieNaMiejsce(){

    }

    public ZagadkaDotarcieNaMiejsce(int index, String poprawnaOdpowiedz, double wspolrzednaLat, double wspolrzednaLng, int typ, String nazwa, int poprzednia, String trescPytania, String ciekawostka,int nastepna)
    {
        this.index = index;
        this.wspolrzednaLat = wspolrzednaLat;
        this.wspolrzednaLng = wspolrzednaLng;
        this.poprawnaOdpowiedz = poprawnaOdpowiedz;
        this.typ = typ;
        this.nazwa = nazwa;
        this.poprzednia = poprzednia;
        this.trescPytania = trescPytania;
        this.ciekawostka = ciekawostka;
        this.nastepna = nastepna;
    }

    public String getTrescPytania(){return trescPytania;}
    public String getPoprawnaOdpowiedz(){return poprawnaOdpowiedz;}

    @Override
    public boolean sprawdz(String str) {
        String[] wsp = str.split(",");
        wsp[0] = wsp[0].substring(0,9);
        wsp[1] = wsp[1].substring(0,9);
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

    public void showPopUp(final Dialog d, final Dialog bAD, final Dialog cD, final Dialog curD)
    {
        d.setCanceledOnTouchOutside(false);
        d.setCancelable(true);
        d.setContentView(R.layout.popup_idz_do);
        d.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        d.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {

                ((Glowna)ctx).popUpSemafor=false;
            }
        });
        Button juzJestDialog = (Button) d.findViewById(R.id.closeIdzDo);
        ((TextView)d.findViewById(R.id.idzDo_title)).setText(getTrescPytania());

        TextView close = (TextView) d.findViewById(R.id.closeIdzDoGora);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                d.dismiss();
            }
        });

        juzJestDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //d.dismiss();
                String mojaOdp = ((Glowna)ctx).obecneWspolrzedne;
                if(mojaOdp==null)
                {
                    Log.println(Log.ASSERT, "null", "null");
                }
                boolean czyPoprawnaOdp = sprawdz(mojaOdp);
                d.dismiss();
                if(czyPoprawnaOdp)
                {
                    ((Glowna) ctx).user.setRozwiazana(index, nastepna);
                    ((Glowna) ctx).popUpSemafor = false;
                    if(index==999)
                    {
                        ((Glowna) ctx).pokazKoniec();
                    }
                    else {
                        showCongratulations(cD, curD, ctx);
                    }
                }
                else
                {
                    ((Glowna) ctx).popUpSemafor=false;
                    showFailed(bAD,ctx);
                }
            }
        });
        if(!((Glowna)ctx).isFinishing())
        {
            d.show();
        }
    }

    public void setContext(Context c)
    {
        this.ctx = c;
    }
}

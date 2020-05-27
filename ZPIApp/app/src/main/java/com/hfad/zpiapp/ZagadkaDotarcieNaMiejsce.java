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
        Log.println(Log.ASSERT, "liczonko", str);
        String[] wsp = str.split(",");
        wsp[0] = wsp[0].substring(0,9);
        wsp[1] = wsp[1].substring(0,9);
        Log.println(Log.ASSERT, "liczonko", wsp[0]);
        double lat = Double.parseDouble(wsp[0]);
        double lng = Double.parseDouble(wsp[1]);
        Log.println(Log.ASSERT, "liczonko", Double.toString(lat));

        if (poprawnaOdpowiedz == null) {

            Log.println(Log.ASSERT, "liczonko", "ugh");
        }
        Log.println(Log.ASSERT, "liczonko", poprawnaOdpowiedz);
        String[] wspCelu = poprawnaOdpowiedz.split(",");
        double latCelu = Double.parseDouble(wspCelu[0]);
        double lngCelu = Double.parseDouble(wspCelu[1]);

        Log.println(Log.ASSERT, "liczonko", "liczonko");
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
                    //////////////////////////////////////////////////////////////////////////////////////////////aktualizacja bazy danych
                    ((Glowna) ctx).user.setRozwiązana(index,nastepna);
                    ((Glowna) ctx).popUpSemafor=false;
                    showCongratulations(cD,curD);
                    /////////////////////////////////////////////////////////////////////////////////////////////pokazanie kolejnego punktu na mapie
                }
                else
                {
                    ((Glowna) ctx).popUpSemafor=false;
                    showFailed(bAD);
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

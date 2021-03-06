package com.hfad.zpiapp;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;

public class ZagadkaPytanie extends Zagadka{
    private String trescPytania;
    private String zdjecie;
    private String poprawnaOdpowiedz;

    public ZagadkaPytanie(){

    }

    public ZagadkaPytanie(int index, String trescPytania, String zdjecie, String poprawnaOdpowiedz, double wspolrzednaLat, double wspolrzednaLng, int typ, String nazwa, int poprzednia, String ciekawostka, int nastepna, String ciekawostkaTytul)
    {
        this.index = index;
        this.typ=typ;
        this.trescPytania = trescPytania;
        this.zdjecie = zdjecie;
        this.poprawnaOdpowiedz = poprawnaOdpowiedz;
        this.wspolrzednaLat = wspolrzednaLat;
        this.wspolrzednaLng = wspolrzednaLng;
        this.nazwa=nazwa;
        this.poprzednia=poprzednia;
        this.ciekawostka = ciekawostka;
        this.nastepna = nastepna;
        this.ciekawostkaTytul = ciekawostkaTytul;
    }

    public String getTrescPytania(){ return trescPytania; }
    public String getZdjecie(){ return zdjecie; }
    public String getPoprawnaOdpowiedz() { return poprawnaOdpowiedz; }

    public boolean sprawdz(String Odp)
    {
        String[] odpowiedzi = poprawnaOdpowiedz.split(",");
        Odp = Odp.toUpperCase();
        for(int i=0;i<odpowiedzi.length; i++){
            if(Odp.equals(odpowiedzi[i]))
            {
                return true;
            }
        }

        return false;
    }


    @NonNull
    @Override
    public String toString() {
        return getIndex() + " " + getPoprzednia() + " " + getNazwa() + " " + getWspolrzednaLat() + " " + getWspolrzednaLng() + " " + getTrescPytania() + " " + getPoprawnaOdpowiedz() + " " + getZdjecie() + " " + getTyp();
    }


    public void showPopUp(final Dialog d, final Dialog bAD, final Dialog cD, final Dialog curD)
    {
        d.setCanceledOnTouchOutside(false);
        d.setCancelable(true);
        d.setContentView(R.layout.popup_pytania);
        d.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        d.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {

                ((Glowna)ctx).popUpSemafor=false;
            }
        });
        Button closeDialog = (Button) d.findViewById(R.id.closePytanie);
        ((TextView)d.findViewById(R.id.pytanie_title)).setText(getTrescPytania());
        final EditText odpowiedz = d.findViewById(R.id.odpowiedz_editText);

        TextView close = (TextView) d.findViewById(R.id.closePytanieGora);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                d.dismiss();
            }
        });

        odpowiedz.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(odpowiedz.getText().toString().isEmpty()){
                    if(hasFocus){
                        odpowiedz.setHint("");
                    }else{
                        odpowiedz.setHint(R.string.userName);
                    }
                }
            }
        });
        ImageView iv=d.findViewById(R.id.photoPytanie);
         if(getIndex()==101 ||getIndex()==92|| getIndex()==22 || getIndex()==33||getIndex()==93 ||getIndex()==53 ||getIndex()==24 ) {
            //http://i.imgur.com/DvpvklR.png
            Picasso.get().load(getZdjecie()).into(iv);//"https://fotopolska.eu/foto/27/27965.jpg","https://polska-org.pl/foto/8458/Epitafia_kartusze_i_pomniki_Wroclaw_8458498.jpg"
            iv.setVisibility(View.VISIBLE);
        }
       // else



        closeDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               String mojaOdp = odpowiedz.getText().toString();
               boolean czyPoprawnaOdp = sprawdz(mojaOdp);
               d.dismiss();
               if(czyPoprawnaOdp)
               {
                   ((Glowna) ctx).user.setRozwiazana(index,nastepna);
                   ((Glowna) ctx).popUpSemafor=false;

                   showCongratulations(cD,curD,ctx);
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
}

package com.hfad.zpiapp;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ZagadkaMLTekst extends Zagadka{
    private String trescPytania;
    Context ctx;
    Glowna ac;
    Bitmap myPhoto;
    ZagadkaMLTekst zagadka;

    public ZagadkaMLTekst(){

    }

    public ZagadkaMLTekst(int index, String trescPytania, double wspolrzednaLat, double wspolrzednaLng, int typ, String nazwa, int poprzednia, String ciekawostka, int nastepna, String ciekawostkaTytul)
    {
        this.index = index;
        this.typ=typ;
        this.trescPytania = trescPytania;
        this.wspolrzednaLat = wspolrzednaLat;
        this.wspolrzednaLng = wspolrzednaLng;
        this.nazwa=nazwa;
        this.poprzednia=poprzednia;
        this.ciekawostka = ciekawostka;
        this.nastepna = nastepna;
        this.ciekawostkaTytul = ciekawostkaTytul;
    }


    public String getTrescPytania(){return trescPytania;}

    @Override
    public boolean sprawdz(String odp) {
        return false;
    }

    public void showPopUp(final Dialog d, final Dialog bAD, final Dialog cD, final Dialog curD)
    {
        zagadka = this;
        d.setCanceledOnTouchOutside(false);
        d.setCancelable(true);
        d.setContentView(R.layout.popup_zrob_zdj);
        d.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        d.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {

                ((Glowna)ctx).popUpSemafor=false;
            }
        });
        final Button takePhoto = (Button) d.findViewById(R.id.zrobZdjecieButton);
        ((TextView)d.findViewById(R.id.zrobZdjecie_title)).setText(getTrescPytania());

        TextView close = (TextView) d.findViewById(R.id.closeZrobZdjecie);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                d.dismiss();
            }
        });

        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textOnButton = takePhoto.getText().toString();
                if(textOnButton.equals("OK"))
                {
                    ((Glowna)ctx).dispatchTakePictureIntent();
                }
                else if(textOnButton.equals("Wyślij"))
                {
                    myPhoto = ((Glowna)ctx).myPhoto;
                    SprawdzTekst st = new SprawdzTekst(ctx,myPhoto,index,zagadka,cD,curD,bAD,nastepna);
                    d.dismiss();
                    st.execute();
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

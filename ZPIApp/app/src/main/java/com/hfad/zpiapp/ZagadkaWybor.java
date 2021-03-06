package com.hfad.zpiapp;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.util.ArrayList;

public class ZagadkaWybor extends Zagadka{
    private String trescPytania;
    private String zdjecie;
    private String poprawnaOdpowiedz;
    private String odpowiedzA;
    private String odpowiedzB;
    private String odpowiedzC;
    private String odpowiedzD;
    ImageView iv;//= d.findViewById(R.id.photoCheckBox);
    public ZagadkaWybor(){

    }
    public ZagadkaWybor(final Dialog d){
        d.setContentView(R.layout.popup_checkbox);
        iv=d.findViewById(R.id.photoCheckBox);
        Picasso.get().load("http://i.imgur.com/DvpvklR.png").into(iv);
    }

    public ZagadkaWybor(int index, String trescPytania, String zdjecie, String poprawnaOdpowiedz, double wspolrzednaLat, double wspolrzednaLng, int typ, String nazwa, int poprzednia, String odpowiedzA, String odpowiedzB, String odpowiedzC, String odpowiedzD, String ciekawostka, int nastepna, String ciekawostkaTytul)
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
        this.odpowiedzA = odpowiedzA;
        this.odpowiedzB = odpowiedzB;
        this.odpowiedzC = odpowiedzC;
        this.odpowiedzD = odpowiedzD;
        this.ciekawostka = ciekawostka;
        this.nastepna = nastepna;
        this.ciekawostkaTytul = ciekawostkaTytul;
    }

    public String getTrescPytania(){ return trescPytania; }
    public String getZdjecie(){ return zdjecie; }
    public String getPoprawnaOdpowiedz() { return poprawnaOdpowiedz; }
    public String getOdpowiedzA(){return odpowiedzA;}
    public String getOdpowiedzB(){return odpowiedzB;}
    public String getOdpowiedzC(){return odpowiedzC;}
    public String getOdpowiedzD(){return odpowiedzD;}

    @Override
    public boolean sprawdz(String odp) {
        odp=odp.toUpperCase();
        Log.i("chosen",getPoprawnaOdpowiedz());
        if(odp.equals(getPoprawnaOdpowiedz())){
            return true;
        }
        else{
            return false;
        }
    }
    public  Drawable LoadImageFromWebOperations(String url,ImageView iv) {
        Picasso.get().load(url).into(iv);
        return iv.getDrawable();
    }

    public void showPopUp(final Dialog d, final Dialog bAD, final Dialog cD, final Dialog curD)
    {
        final boolean[] ifChecked = {false};
        d.setCanceledOnTouchOutside(false);
        d.setCancelable(true);
        d.setContentView(R.layout.popup_checkbox);

        d.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ((TextView)d.findViewById(R.id.pytanie_title)).setText(getTrescPytania());
        d.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {

                ((Glowna)ctx).popUpSemafor=false;
            }
        });

        iv=d.findViewById(R.id.photoCheckBox);
        Picasso.get().load(getZdjecie()).into(iv);

        TextView close = (TextView) d.findViewById(R.id.closeCheckbox);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                d.dismiss();
            }
        });

        Drawable myDrawable = iv.getDrawable();


        final RadioGroup checkbox=(RadioGroup) d.findViewById(R.id.checkbox);
        checkbox.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                checkbox.getCheckedRadioButtonId();
                RadioButton option=
                        (RadioButton) d.findViewById(checkbox.getCheckedRadioButtonId());
                Log.i("chosen",option.getText().toString());

                Log.i("right", String.valueOf(poprawnaOdpowiedz));
                ifChecked[0] =true;
                if(option.getText().equals(poprawnaOdpowiedz)){
                    // Log.i("chosen poprawna",option.getText().toString());
                }
            }
        });
        ArrayList<View> xd=new ArrayList<>();
        xd.add(d.findViewById(R.id.option1));
        xd.add(d.findViewById(R.id.option2));
        xd.add(d.findViewById(R.id.option3));
        xd.add(d.findViewById(R.id.option4));
        checkbox.addChildrenForAccessibility(xd);

        if(getOdpowiedzA().startsWith("http")) {
            ((RadioButton) checkbox.getChildAt(0)).setText("A");//getOdpowiedzA());
            ((RadioButton) checkbox.getChildAt(2)).setText("B");//getOdpowiedzB());
            ((RadioButton) checkbox.getChildAt(4)).setText("C");//getOdpowiedzC());
            ((RadioButton) checkbox.getChildAt(6)).setText("D");//getOdpowiedzD());

            ImageView iv1;
            iv1 =d.findViewById(R.id.photoOdp1);
            Picasso.get().load(getOdpowiedzA()).into(iv1);
            iv1.setVisibility(View.VISIBLE);
            ImageView iv2;
            iv2 =d.findViewById(R.id.photoOdp2);
            Picasso.get().load(getOdpowiedzB()).into(iv2);
            iv2.setVisibility(View.VISIBLE);
            ImageView iv3;
            iv3 =d.findViewById(R.id.photoOdp3);
            Picasso.get().load(getOdpowiedzC()).into(iv3);
            iv3.setVisibility(View.VISIBLE);
            ImageView iv4;
            iv4 =d.findViewById(R.id.photoOdp4);
            Picasso.get().load(getOdpowiedzD()).into(iv4);
            iv4.setVisibility(View.VISIBLE);
        }
        else{
            ((RadioButton) checkbox.getChildAt(0)).setText("A. "+getOdpowiedzA());
            ((RadioButton) checkbox.getChildAt(2)).setText("B. "+getOdpowiedzB());
            ((RadioButton) checkbox.getChildAt(4)).setText("C. "+getOdpowiedzC());
            ((RadioButton) checkbox.getChildAt(6)).setText("D. "+getOdpowiedzD());

            ImageView iv1;
            d.findViewById(R.id.photoOdp1).setVisibility(View.GONE);
            d.findViewById(R.id.photoOdp2).setVisibility(View.GONE);
            d.findViewById(R.id.photoOdp3).setVisibility(View.GONE);
            d.findViewById(R.id.photoOdp4).setVisibility(View.GONE);

        }
        RadioButton option1 =(RadioButton) d.findViewById(R.id.option1);
        RadioButton option2 =(RadioButton) d.findViewById(R.id.option2);
        RadioButton option3 =(RadioButton) d.findViewById(R.id.option3);
        RadioButton option4 =(RadioButton) d.findViewById(R.id.option4);

        Button closeDialog = (Button) d.findViewById(R.id.closeCkeckBox);

        Log.i("photo"," photoCheckbox:Visible");

        closeDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RadioButton option=
                        (RadioButton) d.findViewById(checkbox.getCheckedRadioButtonId());
                String option1="";
                Character o1=' ';
                if(ifChecked[0]) {
                    option1 = ((RadioButton) d.findViewById(checkbox.getCheckedRadioButtonId())).getText().toString();
                    o1=option1.charAt(0);
                }

                String odpow=o1.toString();

                boolean czyPoprawnaOdp = sprawdz(odpow);
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
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
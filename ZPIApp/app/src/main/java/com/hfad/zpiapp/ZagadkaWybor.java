package com.hfad.zpiapp;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.net.URL;
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

    public ZagadkaWybor(int index, String trescPytania, String zdjecie, String poprawnaOdpowiedz, double wspolrzednaLat, double wspolrzednaLng, int typ, String nazwa, int poprzednia, String odpowiedzA, String odpowiedzB, String odpowiedzC, String odpowiedzD)
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

//        if(poprawnaOdpowiedz.get(0).equals(udzielonaOdpowiedz)

//        {
//            rozwiazana = true;
//        }
        // return false;
    }
    /*@Override
    public PopupWindow showPopUp(LayoutInflater inflater) {
        PopupWindow pw=null;
        pw = new PopupWindow(inflater.inflate(R.layout.popup_gratulacje, null, false), ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        return pw;
    }*/
    public  Drawable LoadImageFromWebOperations(String url,ImageView iv) {
        Picasso.get().load("http://i.imgur.com/DvpvklR.png").into(iv);
        return iv.getDrawable();
    }

    public void showPopUp(final Dialog d, final Dialog bAD, final Dialog cD, final Dialog curD)
    {

//        iv.setVisibility(View.VISIBLE);
        //Drawable dra=iv.getDrawable();
        //Picasso.get().load("http://i.imgur.com/DvpvklR.png").into(iv);
       // d.dismiss();
        final boolean[] ifChecked = {false};
        d.setCanceledOnTouchOutside(false);
        d.setCancelable(true);
        //d.setContentView(R.layout.custom_popup_coordinates);
        d.setContentView(R.layout.popup_checkbox);

        d.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ((TextView)d.findViewById(R.id.pytanie_title)).setText(getTrescPytania());

        iv=d.findViewById(R.id.photoCheckBox);
        Picasso.get().load("http://i.imgur.com/DvpvklR.png").into(iv);

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
                  //  iv=d.findViewById(R.id.photoCheckBox);
                //Picasso.get().load("http://i.imgur.com/DvpvklR.png").into(iv);
                /*  Drawable dr=LoadImageFromWebOperations
                         ("https://www.google.pl/url?sa=i&url=https%3A%2F%2Fwww.mojegotowanie.pl%2Fprzepis%2Fpyszne-pancakes&psig=AOvVaw0rbjv3Tb0eHG3omo-Fo1eC&ust=1589743526594000&source=images&cd=vfe&ved=0CAIQjRxqFwoTCOjcqaSOuekCFQAAAAAdAAAAABAE");
        iv.setImageDrawable(dr);*/
            }
        });
        ArrayList<View> xd=new ArrayList<>();
        xd.add(d.findViewById(R.id.option1));
        xd.add(d.findViewById(R.id.option2));
        xd.add(d.findViewById(R.id.option3));
        xd.add(d.findViewById(R.id.option4));
        checkbox.addChildrenForAccessibility(xd);

       // option1.setButtonDrawable(myDrawable);
        if(getOdpowiedzA().startsWith("http")) {
            ((RadioButton) checkbox.getChildAt(0)).setText("A");//getOdpowiedzA());
            ((RadioButton) checkbox.getChildAt(1)).setButtonDrawable(myDrawable);
            ((RadioButton) checkbox.getChildAt(1)).setButtonDrawable(myDrawable);
            ((RadioButton) checkbox.getChildAt(1)).setClickable(false);//.setActivated(false);
            ((RadioButton) checkbox.getChildAt(2)).setText("B");//getOdpowiedzB());
            ((RadioButton) checkbox.getChildAt(3)).setButtonDrawable(myDrawable);
            ((RadioButton) checkbox.getChildAt(3)).setButtonDrawable(myDrawable);
            ((RadioButton) checkbox.getChildAt(3)).setClickable(false);
            ((RadioButton) checkbox.getChildAt(4)).setText("C");//getOdpowiedzC());
            ((RadioButton) checkbox.getChildAt(5)).setButtonDrawable(myDrawable);
            ((RadioButton) checkbox.getChildAt(5)).setButtonDrawable(myDrawable);
            ((RadioButton) checkbox.getChildAt(5)).setClickable(false);
            ((RadioButton) checkbox.getChildAt(6)).setText("D");//getOdpowiedzD());
            ((RadioButton) checkbox.getChildAt(7)).setButtonDrawable(myDrawable);
            ((RadioButton) checkbox.getChildAt(7)).setButtonDrawable(myDrawable);
            ((RadioButton) checkbox.getChildAt(7)).setClickable(false);
        }
        else{
            ((RadioButton) checkbox.getChildAt(0)).setText("A. "+getOdpowiedzA());
            ((RadioButton) checkbox.getChildAt(1)).setText("A. "+getOdpowiedzA());
            ((RadioButton) checkbox.getChildAt(2)).setText("B. "+getOdpowiedzB());
            ((RadioButton) checkbox.getChildAt(3)).setText("B. "+getOdpowiedzB());
            ((RadioButton) checkbox.getChildAt(4)).setText("C. "+getOdpowiedzC());
            ((RadioButton) checkbox.getChildAt(4)).setText("C. "+getOdpowiedzC());
            ((RadioButton) checkbox.getChildAt(5)).setText("D. "+getOdpowiedzD());
            ((RadioButton) checkbox.getChildAt(6)).setText("D. "+getOdpowiedzD());
            d.findViewById(R.id.option11).setVisibility(View.GONE);
            d.findViewById(R.id.option22).setVisibility(View.GONE);
            d.findViewById(R.id.option33).setVisibility(View.GONE);
            d.findViewById(R.id.option44).setVisibility(View.GONE);
        }
        RadioButton option1 =(RadioButton) d.findViewById(R.id.option1);
        RadioButton option2 =(RadioButton) d.findViewById(R.id.option2);
        RadioButton option3 =(RadioButton) d.findViewById(R.id.option3);
        RadioButton option4 =(RadioButton) d.findViewById(R.id.option4);

        Button closeDialog = (Button) d.findViewById(R.id.closeCkeckBox);




        //iv.setImageDrawable(d.findViewById(R.drawable.ic_launcher_foreground));
        //d.findViewById(R.id.photoCheckBox).setVisibility(View.VISIBLE);
        Log.i("photo"," photoCheckbox:Visible");
       /* Drawable dr=LoadImageFromWebOperations("https://www.html.am/images/image-codes/milford_sound_t.jpg");
        iv.setImageDrawable(dr);*/
        //new DownloadImageTask((ImageView) d.findViewById(R.id.photoCheckBox))
          //      .execute("http://java.sogeti.nl/JavaBlog/wp-content/uploads/2009/04/android_icon_256.png");

        // ((TextView)d.findViewById(R.id.pytanie_title)).setText(getTrescPytania());
        closeDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // d.dismiss();
                RadioButton option=
                        (RadioButton) d.findViewById(checkbox.getCheckedRadioButtonId());
                String option1="";
                Character o1=' ';
                if(ifChecked[0]) {
                    option1 = ((RadioButton) d.findViewById(checkbox.getCheckedRadioButtonId())).getText().toString();
                    //Log.i("chosen",option.getText().toString());
                    o1=option1.charAt(0);
                }

                String odpow=o1.toString();

                boolean czyPoprawnaOdp = sprawdz(odpow);
                d.dismiss();
                if(czyPoprawnaOdp)
                {
                    //////////////////////////////////////////////////////////////////////////////////////////////aktualizacja bazy danych
                    showCongratulations(cD,curD);
                    /////////////////////////////////////////////////////////////////////////////////////////////pokazanie kolejnego punktu na mapie
                }
                else
                {
                    showFailed(bAD);
                }
            }
        });
        d.show();
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
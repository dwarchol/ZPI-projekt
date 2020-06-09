package com.hfad.zpiapp;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

//import android.support.v7.app.ActionBar;
//import android.support.v7.app.AppCompatActivity;

public class KontoUzytkownika extends AppCompatActivity {
    Dialog progressDialog;
    Context ctx;
    boolean museum = false;

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konto_uzytkownika);
        Intent intent = getIntent();
        ArrayList<String> rz=intent.getStringArrayListExtra("program");
        ArrayList<String> ciekawostki=intent.getStringArrayListExtra("ciekawostki");
        ArrayList<String> ciekawostkiTyt=intent.getStringArrayListExtra("ciekawostkiTytuly");

        int postep = rz.size();

        this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar_2);
        ImageView imv = (ImageView) findViewById(R.id.logout);
        imv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.putExtra("logout",true);
                setResult(Activity.RESULT_OK, i );
                finish();
                //startActivity(i);
            }
        });
        SeekBar sb = (SeekBar) findViewById(R.id.seekBar);
        System.out.println(postep);
        sb.setMax(48);
        sb.setProgress(postep);

        //sb.setEnabled(false);
        sb.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        sb.setClickable(false);
       ListView lv= findViewById(R.id.ciekawostkiUsera);
        ArrayList<Zagadka> alt =new ArrayList<>();
        String[] ciekawostkiArr=new String[ciekawostki.size()];
        TextView tv = findViewById(R.id.odznazki_textView);
        for(int i=0;i<ciekawostki.size();i++){
            ciekawostkiArr[i]= System.getProperty ("line.separator") +
                    ciekawostkiTyt.get(i)+ System.getProperty ("line.separator")+
                    System.getProperty ("line.separator")+ ciekawostki.get(i) + System.getProperty ("line.separator");
        }
        ArrayAdapter adapter = new ArrayAdapter<String>(this,R.layout.activity_listview,R.id.lvTextView,ciekawostkiArr);
        lv.setAdapter(adapter);

        ctx = this;
    }

    public void comeBackMethod(View view)
    {
        super.onBackPressed();
    }
}

package com.hfad.zpiapp;

import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.RelativeLayout;

public class Startowa extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startowa);

        Button haveAccount = (Button) findViewById(R.id.haveAccount);
        Button register = (Button) findViewById(R.id.register);

        new CountDownTimer(30000, 100) {

            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
            }
        }.start();

        moveButton(haveAccount);
        moveButton(register);
    }

    public void moveButton(final Button button)
    {
        DisplayMetrics dm = this.getResources().getDisplayMetrics();

        final float yDest = -2*dm.heightPixels/5 ;

        final int originalPos[] = new int[2];
        button.getLocationOnScreen( originalPos );

        long timeToAnimate = 2000;

        button.animate().setDuration(timeToAnimate);
        button.animate().y(yDest - originalPos[1]);
        button.animate().translationY(yDest - originalPos[1]);

    }

    public void haveAccountMethod(View view)
    {
        final Intent haveAccountIntent=new Intent(this,Glowna.class);
        startActivity(haveAccountIntent);
    }

    public void registerMethod(View view)
    {
        final Intent registerIntent=new Intent(this,Glowna.class);
        startActivity(registerIntent);
    }

}

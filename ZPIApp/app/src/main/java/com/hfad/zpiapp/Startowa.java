package com.hfad.zpiapp;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.RelativeLayout;

public class Startowa extends AppCompatActivity {

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

    public void moveButton(Button button)
    {
        DisplayMetrics dm = this.getResources().getDisplayMetrics();

        float yDest = -2*dm.heightPixels/5 ;

        int originalPos[] = new int[2];
        button.getLocationOnScreen( originalPos );

        TranslateAnimation animation = new TranslateAnimation( originalPos[0], originalPos[0] , originalPos[1], yDest - originalPos[1]);
        animation.setDuration(1000);
        animation.setFillAfter( true );
        button.startAnimation(animation);
    }
}

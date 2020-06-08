package com.hfad.zpiapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;

public class Odtwarzacz {
    Context ctx;
    MediaPlayer FXPlayer;
    SharedPreferences preferences;
    public Odtwarzacz(Context c)
    {
        this.ctx=c;
        this.preferences =ctx.getSharedPreferences("APP_SETTINGS",0);
    }

    public void spotSound()
    {
        if(preferences.getBoolean("soundBool",true)==true) {
            if (FXPlayer != null) {
                FXPlayer.stop();
                FXPlayer.release();
            }
            FXPlayer = MediaPlayer.create(ctx, R.raw.notify);
            if (FXPlayer != null)
                FXPlayer.start();
        }
    }

    public void correctSound()
    {
        if(preferences.getBoolean("soundBool",true)==true) {
            if (FXPlayer != null) {
                FXPlayer.stop();
                FXPlayer.release();
            }
            FXPlayer = MediaPlayer.create(ctx, R.raw.correct);
            if (FXPlayer != null)
                FXPlayer.start();
        }
    }

    public void wrongSound()
    {
        if(preferences.getBoolean("soundBool",true)==true) {
            if (FXPlayer != null) {
                FXPlayer.stop();
                FXPlayer.release();
            }
            FXPlayer = MediaPlayer.create(ctx, R.raw.negative);
            if (FXPlayer != null)
                FXPlayer.start();
        }
    }

    public void finishSound()
    {
        if(preferences.getBoolean("soundBool",true)==true) {
            if (FXPlayer != null) {
                FXPlayer.stop();
                FXPlayer.release();
            }
            FXPlayer = MediaPlayer.create(ctx, R.raw.fanfare);
            if (FXPlayer != null)
                FXPlayer.start();
        }
    }



}

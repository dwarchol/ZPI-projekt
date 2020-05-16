package com.hfad.zpiapp;

import android.content.Context;
import android.media.MediaPlayer;

public class Odtwarzacz {
    Context ctx;
    MediaPlayer FXPlayer;
    public Odtwarzacz(Context c)
    {
        this.ctx=c;
    }

    public void spotSound()
    {
        if(FXPlayer != null)
        {
            FXPlayer.stop();
            FXPlayer.release();
        }
        FXPlayer = MediaPlayer.create(ctx, R.raw.notify);
        if(FXPlayer != null)
            FXPlayer.start();
    }

    public void correctSound()
    {
        if(FXPlayer != null)
        {
            FXPlayer.stop();
            FXPlayer.release();
        }
        FXPlayer = MediaPlayer.create(ctx, R.raw.correct);
        if(FXPlayer != null)
            FXPlayer.start();
    }

    public void wrongSound()
    {
        if(FXPlayer != null)
        {
            FXPlayer.stop();
            FXPlayer.release();
        }
        FXPlayer = MediaPlayer.create(ctx, R.raw.negative);
        if(FXPlayer != null)
            FXPlayer.start();
    }



}

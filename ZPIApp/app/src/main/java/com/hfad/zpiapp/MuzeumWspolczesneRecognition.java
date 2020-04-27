package com.hfad.zpiapp;

import android.util.Log;

public class MuzeumWspolczesneRecognition {

    public static boolean museum(String line)
    {
        int whichB = 0;
        String lineB = "";
        for(int i = 0; i < line.length(); i++)
        {
            if(line.charAt(i) == 'b' && whichB == 0 && i + 6 < line.length())
            {
                lineB = line.substring(i, i + 6);
                if(lineB.equals("będzie") || lineB.equals("bedzie") || lineB.equals("bpdzie"))
                {
                    whichB = 1;
                }
            }
            else if(line.charAt(i) == 'b' && whichB == 1 && i + 4 < line.length())
            {
                lineB = line.substring(i, i+ 4);
                if(lineB.equals("było") || lineB.equals("bylo") || lineB.equals("byto") || lineB.equals("byio") || lineB.equals("byfo") || lineB.equals("byro"))
                {
                    return true;
                }
            }
        }
        return false;
    }

}

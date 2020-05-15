package com.hfad.zpiapp;

public class SolnyRecognition {

    public static boolean solny(String line)
    {
        for(int i = 0; i < line.length(); i++)
        {
            if(line.charAt(i)=='S' && line.length() - i >=5)
            {
                String rozw = line.substring(i,i+5);
                if(rozw.equals("SOLNY"))
                {
                    return true;
                }
            }
        }
        return false;
    }
}

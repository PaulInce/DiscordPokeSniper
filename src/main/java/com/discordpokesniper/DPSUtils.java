package com.discordpokesniper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class DPSUtils{

    public static double formatCoords(double coords){
        DecimalFormat df = new DecimalFormat("000.00000");
        return Double.parseDouble(df.format(coords));
    }

    public static void log(String logMessage){
        LocalTime time = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT);
        System.out.println("[" + time.format(formatter) + "] " + logMessage);
    }

    public static String getToken(){
        File tokenFile = new File(Main.getCurrentDirectory().getAbsolutePath() + "/token.txt");
        try{
            BufferedReader reader = new BufferedReader(new FileReader(tokenFile));
            return reader.readLine();
        }catch(IOException e){
            e.printStackTrace();
        }
        return null;
    }
}

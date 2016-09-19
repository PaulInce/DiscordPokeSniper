package com.discordpokesniper;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class DSPUtils{

    public static double formatCoords(double coords){
        DecimalFormat df = new DecimalFormat("000.00000");
        return Double.parseDouble(df.format(coords));
    }

    public static void log(String logMessage){
        LocalTime time = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT);
        System.out.println("[" + time.format(formatter) + "] " + logMessage);
    }
}

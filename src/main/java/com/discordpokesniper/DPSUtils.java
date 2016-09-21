package com.discordpokesniper;

import java.io.*;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;

public class DPSUtils{

    public static double formatCoords(double coords){
        DecimalFormat df = new DecimalFormat("000.00000");
        return Double.parseDouble(df.format(coords).replace(',', '.'));
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

    public static ArrayList<String> pokemonSniping = new ArrayList<>();

    public static void loadSnipingPokemon(){
        File file = new File(Main.getCurrentDirectory().getAbsolutePath() + "/DiscordSniperPokemon.txt");
        try{
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String pokemonLine = null;
            while((pokemonLine = reader.readLine()) != null){
                String[] split = pokemonLine.split("\\=");
                String name = split[0];
                String value = split[1];
                if(Boolean.valueOf(value) == false) continue;
                else pokemonSniping.add(name);
            }
        }catch(IOException e){
            e.printStackTrace();
        }

    }
}

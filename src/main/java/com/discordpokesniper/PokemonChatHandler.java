package com.discordpokesniper;

import de.btobastian.javacord.DiscordAPI;
import de.btobastian.javacord.entities.message.Message;
import de.btobastian.javacord.listener.message.MessageCreateListener;

import java.io.*;
import java.util.Timer;
import java.util.TimerTask;

public class PokemonChatHandler implements MessageCreateListener{

    public void onMessageCreate(DiscordAPI discordAPI, Message message){
        String messageContents = message.getContent();
        PokeLocation pokeLocation = PokeLocation.parsePokemonNotificationString(messageContents);
        if(pokeLocation != null) snipePokemon(pokeLocation);
    }

    public void snipePokemon(PokeLocation pokeLocation){
        if(SnipeCache.isCached(pokeLocation)) return;

        //Set the snipe.bat file data
        double latitude = pokeLocation.getLatitude();
        double longitude = pokeLocation.getLongitude();
        String pokemonName = pokeLocation.getPokemonType().toString();
        BufferedWriter writer = null;
        try{
            writer = new BufferedWriter(new FileWriter(new File(Main.getCurrentDirectory().getAbsolutePath() + "\\snipe.vbs")));
            writer.write("Set oShell = CreateObject (\"Wscript.Shell\") \r\n" +
                    "Dim strArgs\r\n" +
                    "strArgs = \"PokeSniper2.exe " + pokemonName + " " + latitude + " " + longitude + " exit\"\r\n" +
                    "oShell.Run strArgs, 0, false");
        }catch(IOException e){
            e.printStackTrace();
        }finally{
            try{
                writer.close();
            }catch(IOException e){
                e.printStackTrace();
            }
        }

        //Snipe
        DPSUtils.log("Sniping " + pokemonName + " @ " + latitude + ", " + longitude + ".");
        try{
            final Process process = Runtime.getRuntime().exec("cscript " + Main.getCurrentDirectory().getAbsolutePath() + "\\snipe.vbs");
            Timer timer = new Timer();
            timer.schedule(new TimerTask(){
                @Override
                public void run(){
                    process.destroyForcibly();
                }
            }, 15000);
        }catch(IOException e){
            e.printStackTrace();
        }
        SnipeCache.addToCache(pokeLocation);
    }
}

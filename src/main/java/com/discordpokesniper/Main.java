package com.discordpokesniper;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import de.btobastian.javacord.Discord;
import de.btobastian.javacord.DiscordAPI;
import de.btobastian.javacord.Javacord;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URISyntaxException;

public class Main{

    private static File currentDirectory = null;


    public static void main(String... args){
        Main main = new Main();
        main.disableLoggers();
        main.setCurrentDirectoryLocation();
        DPSUtils.loadSnipingPokemon();
        Discord.launch();
        DiscordAPI discordAPI = main.connectToDiscord();
        main.registerListeners(discordAPI);
        SnipeCache.startCacheProgram();
        DPSUtils.log("Autosniper Started...");
    }


    public void disableLoggers(){
        Logger logger = (Logger)LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        logger.setLevel(Level.ERROR);
    }

    public DiscordAPI connectToDiscord(){
        String token = DPSUtils.getToken();
        DiscordAPI api = Javacord.getApi(token, false);
        api.connectBlocking();
        DPSUtils.log("Connected to Discord with token: " + token + ".");
        return api;
    }

    public void registerListeners(DiscordAPI discordAPI){
        discordAPI.registerListener(new PokemonChatHandler());

    }

    public void setCurrentDirectoryLocation(){
        try{
            currentDirectory = new File(new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getParentFile().getAbsolutePath());
        }catch(URISyntaxException e){
            e.printStackTrace();
        }
    }


    public static File getCurrentDirectory(){
        return currentDirectory;
    }

}

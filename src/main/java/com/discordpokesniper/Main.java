package com.discordpokesniper;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import de.btobastian.javacord.DiscordAPI;
import de.btobastian.javacord.Javacord;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URISyntaxException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main{

    private static File currentDirectory = null;


    public static void main(String... args){
        Main main = new Main();
        main.disableLoggers();
        main.setCurrentDirectoryLocation();

        DiscordAPI discordAPI = main.connectToDiscord();
        main.registerListeners(discordAPI);
        SnipeCache.startCacheProgram();
        DSPUtils.log("Autosniper Started...");
    }


    public void disableLoggers(){
        Logger logger = (Logger)LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        logger.setLevel(Level.ERROR);
    }

    public DiscordAPI connectToDiscord(){
        //Mine
        DiscordAPI api = Javacord.getApi("MjI2OTk5Nzk1NjM2NTAyNTI4.Cr_xig.JoRe-g65JJqSJGxB-6ptn7GgQJI", false);
        //Someone elses
        //DiscordAPI api = Javacord.getApi("MjE0MDk0NzExOTY5ODA4Mzg0.CqdYpg.BxedtirQj2HU5xs3qZ2FiC0jg0k", false);
        api.connectBlocking();
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

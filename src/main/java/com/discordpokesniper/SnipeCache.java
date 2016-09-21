package com.discordpokesniper;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class SnipeCache{

    public static Map<PokeLocation, Long> sniperCache = new ConcurrentHashMap<>();

    public static void addToCache(PokeLocation pokeLocation){
        sniperCache.put(pokeLocation, System.currentTimeMillis());
    }

    public static boolean isCached(PokeLocation pokeLocation){
        return sniperCache.containsKey(pokeLocation);
    }

    public static void startCacheProgram(){
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask(){
            @Override
            public void run(){
                List<PokeLocation> toRemove = new ArrayList<>();

                for(PokeLocation pokeLocation : sniperCache.keySet()){
                    long timePlaced = sniperCache.get(pokeLocation);
                    long currentTime = System.currentTimeMillis();
                    if((timePlaced + 3_600_000L) < currentTime) toRemove.add(pokeLocation);
                }

                toRemove.forEach(pL -> sniperCache.remove(pL));
            }
        }, 3_600_000L, 60000L);
    }
}

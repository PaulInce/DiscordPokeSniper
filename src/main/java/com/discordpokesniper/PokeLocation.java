package com.discordpokesniper;

import org.apache.commons.lang.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PokeLocation{

    private PokemonType pokemonType;
    private double longitude;
    private double latitude;


    public PokeLocation(PokemonType pokemonType, double longitude, double latitude){
        setPokemonType(pokemonType);
        setLongitude(longitude);
        setLatitude(latitude);
    }


    public void setPokemonType(PokemonType pokemonType){
        this.pokemonType = pokemonType;
    }

    public PokemonType getPokemonType(){
        return this.pokemonType;
    }

    public void setLongitude(double longitude){
        this.longitude = longitude;
    }

    public double getLongitude(){
        return this.longitude;
    }

    public void setLatitude(double latitude){
        this.latitude = latitude;
    }

    public double getLatitude(){
        return this.latitude;
    }


    public static PokeLocation parsePokemonNotificationString(String notificationString){
        if(StringUtils.containsIgnoreCase(notificationString, "DSP")) return null;

        //Find Lat/Long from string
        Pattern pattern = Pattern.compile("(-?(\\d)+(\\.)?(\\d)*)");
        Matcher matcher = pattern.matcher(notificationString);
        String[] locations = new String[2];
        int doublesFound = 0;
        while(matcher.find()){
            locations[doublesFound] = matcher.group(1);
            ++doublesFound;
            if(doublesFound == 2) break;
        }
        if(doublesFound < 2) return null;
        double latitude = Double.parseDouble(locations[0]);
        double longitude = Double.parseDouble(locations[1]);

        //Check if Lat/Long found is valid
        if(latitude > 90 || latitude < -90 || longitude > 180 || longitude < -180) return null;
        if(latitude % 1 == 0 || longitude % 1 == 0) return null;

        //Find if it is 100IV pokemon
        boolean is100IV = StringUtils.containsIgnoreCase(notificationString, "IV: 100") || StringUtils.containsIgnoreCase(notificationString, "iv100") || StringUtils.containsIgnoreCase(notificationString, "100iv") || StringUtils.containsIgnoreCase(notificationString, "100%") || StringUtils.containsIgnoreCase(notificationString, "IV:(100)");
        if(!is100IV) return null;

        //Find which pokemon we're talking about
        PokemonType pokemonType = null;
        for(PokemonType type : PokemonType.values()){
            if(StringUtils.containsIgnoreCase(notificationString, type.toString())){
                pokemonType = type;
                break;
            }
        }

        //Ensure lat / long is of the same length for easy cache storage

        if(pokemonType == null) return null;
        return new PokeLocation(pokemonType, longitude, latitude);
    }

    @Override
    public boolean equals(Object o){
        if(!(o instanceof PokeLocation)) return false;
        PokeLocation pokeLocation = (PokeLocation)o;
        if(pokeLocation.getPokemonType() != getPokemonType()) return false;
        if(pokeLocation.getLongitude() != getLongitude()) return false;
        if(pokeLocation.getLatitude() != getLatitude()) return false;
        return true;
    }

    @Override
    public int hashCode(){
        int latitude = (int)(DSPUtils.formatCoords(getLatitude()) * 100000);
        int longitude = (int)(DSPUtils.formatCoords(getLongitude()) * 100000);
        return latitude + longitude;
    }
}
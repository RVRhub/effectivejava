package com.rybak.effective.java.ch11.item77.enumSingleton;


import java.util.Arrays;

//Более предпочтительный поход
public enum Elvis {
    INSTANCE;
    private String[] favoriteSongs = { "Hound Dog", "Heartbreak Hotel" };

    public void printFavorites() {
        System.out.println(Arrays.toString(favoriteSongs));
    }
}
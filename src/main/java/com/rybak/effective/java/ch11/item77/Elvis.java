package com.rybak.effective.java.ch11.item77;


import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.Arrays;

// Broken singleton - has nontransient object reference field!
public class Elvis implements Serializable {
    public static final Elvis INSTANCE = new Elvis();

    private Elvis() {
    }

    private String[] favoriteSongs = { "Hound Dog", "Heartbreak Hotel" };

    public void printFavorites()
    {
        System.out.println(Arrays.toString(favoriteSongs));
    }

    //readResolve для контроля над экземлярами
    private Object readResolve() throws ObjectStreamException
    {
        //Возвращает только истинный экземляр Elvis и дает возможность сборщику мусора позаботится
        //об Elvis - самозванце
        return INSTANCE;
    }
}
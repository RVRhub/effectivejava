package com.rybak.effective.java.ch6;

import java.util.EnumSet;
import java.util.Set;

/**
 * Используйте EnumSet вместо битовых полей
 */
public class Item32
{
    enum Style {
        BOLD,
        ITALIC,
        UNDERLINE,
        STRIKETHROUGH;
    }

    public void applyStyles(Set<Style> styles)
    {
        System.out.println(styles);
    }

    public static void main(String[] args) {

        Item32 item32 = new Item32();
        item32.applyStyles(EnumSet.of(Style.BOLD, Style.STRIKETHROUGH));
    }

}



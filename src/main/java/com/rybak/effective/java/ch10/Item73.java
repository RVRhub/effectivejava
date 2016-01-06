package com.rybak.effective.java.ch10;

/**
 * Избегайте группировки потоков
 *
 *
 */
public class Item73
{
    public static void main(String[] args) {
        Thread.setDefaultUncaughtExceptionHandler((Thread.UncaughtExceptionHandler) new Exception());
    }

}

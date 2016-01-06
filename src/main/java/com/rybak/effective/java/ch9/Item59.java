package com.rybak.effective.java.ch9;

import java.io.FileNotFoundException;

/**
 * Избегайте ненужных обрабатываемых исключений
 */
public class Item59

{
    public static void main(String[] args) throws TheCheckException {


        //вызов с обрабатоваемым исключением
        try
        {
            new Item59().action();
        }
        catch (TheCheckException e)
        {

        }


        //вызов с использованием метода проверки состояния и необрабатываемого исключения
        if(isActionPermitted(args))
        {
            new Item59().action();
        }
        else
        {
            //Обработать исключительную ситуацию
        }
    }


    private static boolean isActionPermitted(String[] args) {
        return true;
    }

    private static void testCheckException() throws FileNotFoundException
    {
        if(true)
            throw new FileNotFoundException();
    }

    private static void testUnCheckException()
    {
        if(true)
            throw new RuntimeException();
    }


    private void action()  throws TheCheckException{

    }

    private class TheCheckException extends Exception {
    }
}

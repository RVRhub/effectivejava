package com.rybak.effective.java.ch7;

/**
 * Используйте varargs c осторожностью
 */
public class Item42
{
    public static void main(String[] args)
    {

    }

    static int sum(int ... args)
    {
        int sum = 0;
        for(int arg : args)
            sum += arg;

        return sum;
    }

    //неверное использование varargs для передачи одного или более аргументов
    static int min(int ... args)
    {
        if(args.length == 0)
            throw new IllegalArgumentException("Too few arguments");
        int min = args[0];

        for(int i = 1; i < args.length; i++)
            if(args[i] < min)
                min=args[i];

        return min;
    }

    //todo правильный способ использовать varargs для передачи одного или более аргументов
    static int min(int firstArgs, int ... args)
    {
        int min = firstArgs;

        for(int arg : args)
            if(arg < min)
                min=arg;

        return min;
    }
}

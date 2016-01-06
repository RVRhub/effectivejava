package com.rybak.effective.java.ch8;

import java.util.Arrays;
import java.util.Set;

/**
 * Предпочитайте интерфейсы отражения класса
 *
 * Недостатки работы с отражением :
 *   Вы лишаете всех преимуществ проверки типов на этапе компляции
 *   Программный код, необходимый для отражения классов, неуклюж и многословен.
 *   Страдает производительность.
 *
 * Как правило, обычные приложения на стадии выполнения не должны пользоваться опосредованным доступом к объектам.
 *
 * Исключения :
 *   -визуализаторы классов
 *   - инспекторы объектов
 *   - анализаторы программного кода
 *   - интерпретирующие встроеные системы
 *
 * Вы можете без больших затрат использовать многие приемущества механизма отражения, если будете применять
 * его в усеченном виде.
 *
 * Во многих программах механизм отражения можно заменить на супер класс или интерфейс.
 *
 * По возможности механизм отражения использовать для создания экземпляра отсутствующих классов,
 * а для доступа к полученным объектам следует применять интерфейс.
 */
public class Item53
{

    //опосредственное создание экземпляра с доступом через интерфейс
    @SuppressWarnings("unchecked")
    public static void main(String[] args)
    {
        //Преобразует имя класса в экземпляр класса
        Class<?> cl = null;
        try {
            cl = Class.forName(args[0]);
        }
        catch (ClassNotFoundException e)
        {
            System.err.println("Class not found");
            System.exit(1);
        }

        //Создание экземпляра класса
        Set<String> s = null;

        try {
            s = (Set<String>)cl.newInstance();
        }
        catch (IllegalAccessException e)
        {
            System.err.println("Class not accessible");
            System.exit(1);
        }
        catch (InstantiationException e)
        {
            System.err.println("Class not instantiate");
            System.exit(1);
        }

        //Проверяет набор
        s.addAll(Arrays.asList(args).subList(1, args.length -1));
        System.out.println(s);
    }
}

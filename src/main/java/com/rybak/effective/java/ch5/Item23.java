package com.rybak.effective.java.ch5;

import java.util.*;

/**
 * Не используйте необработаные типы в новом коде
 *
 * Необработаный тип (raw type) - без актуальных типовых параметров.
 * Например, необработанным типом, существующим List<E>, будет List,
 * и ведет себя так , словно вся информация об общеном типе оказалась стерта при его декларации .
 */
public class Item23
{

    public static void rawType()
    {
        //не стоит так делать
        Collection stamps = null;
        stamps.add(new Stamp());
        stamps.add(new Coin());

        for(Iterator i = stamps.iterator(); i.hasNext(); )
        {
            Stamp stamp = (Stamp) i.next(); //Throws class cast exception
            //...
        }

    }

    public static void parameterizedType()
    {
        //Коллекция типов с параметрами - безопасно
        Collection<Stamp> stamps = null;
        stamps.add(new Stamp());
      //  stamps.add(new Coin());

        for(Iterator<Stamp> i = stamps.iterator(); i.hasNext(); )
        {
            Stamp stamp = i.next(); //Throws class cast exception
            //...
        }

    }

    //TODO Если вы используете необработанные типа ,
    //TODO то вы теряете все преймущества использования средств обобщеного программирования.

    //Todo Вы теряете в безопасности типов в случаеиспользования необработаного типа,
    //Todo такого как List, но сохраняете ее при испльзовании типа с парамтрами List<Object>.

    public static void main(String[] args)
    {
        //Использует необратимый типа - происходит ошибка при выполнеении!
        List<String> strings = new ArrayList<String>();
        unsafeAdd(strings, new Integer(42));
        String s = strings.get(0); //Compiler-generated cast

    }

    private static void unsafeAdd(List strings, Object o)  //если добавить List на List<Object>
    {
        strings.add(o);
    }

    //Необработаные типа - опасно
    static int numElementsInCommon(Set s1, Set s2)
    {
        int result = 0;
        for(Object o1 : s1)
            if(s2.contains(o1))
            {
                result++;
            }

        return result;
    }

    //Unbounded wildcard type (Несвязаные типа подстановки ) - typesafe and flexible
    static int numElementsInCommonUnboundedWildcardType(Set<?> s1, Set<?> s2)
    {
        int result = 0;
        for(Object o1 : s1)
            if(s2.contains(o1))
            {
                result++;
            }

        return result;
    }
    //Вы можете добавить в коллекцию любой элемент, используя необработанные тип и повредить инварианты типа коллекции,
    //TODO НО вы не можете добавить любой элемент(кроме нолевого) в коллекцию Collection <?>.

    //TODO Поскольку информация об общеном типе стирается при выполнении ,
    //TODO то не разрешается испльзовать оператор instanceof в типах с параметрами
    //TODO , за исключением не связаных типов подстановки.

    static void instanceofExample(Set o)
    {
        if(o instanceof Set) //Raw type
        {
            Set<?> m = (Set<?>) o; //Wildcard type
        }
    }


    public static class Stamp {
    }

    private static class Coin {
    }
}

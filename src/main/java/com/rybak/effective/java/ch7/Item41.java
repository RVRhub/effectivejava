package com.rybak.effective.java.ch7;

import java.math.BigInteger;
import java.util.*;

/**
 * Перезагружая метод соблюдайте осторожность
 */
public class Item41
{
    //Выбор вариант перегрузки осуществляется на стадии компиляции
    //Выбор перегружаемых методов является статичным, тогда как выбор переопределяемых методов - денамический
    public static void main(String[] args) {


        Collection<?>[] collections = {
                new HashSet<String>(),
                new ArrayList<BigInteger>(),
                new HashMap<String, String>().values()
        };

        for (Collection<?> c : collections) {
            System.out.println(CollectionClassifier.classify(c));
        }


        Wine [] wines = { new Wine(), new SparklingWine(), new Champagne()};

        for (Wine wine : wines)
            System.out.println(wine.name());


        //Безопасная, умереная политика предписывает никогда не предоставлять два варианта перезагрузки с одним и
        //темже числом параметров

        Set<Integer> set = new TreeSet<Integer>();
        List<Integer> list = new ArrayList<Integer>();

        for(int i = -3; i < 3; i++)
        {
            set.add(i);
            list.add(i);
        }
        System.out.println(set + "\n" + list);

        for(int i = 0; i < 3; i++)
        {
            set.remove(i);
            list.remove(i);
            list.remove((Integer)i);//удаления по E
        }

        System.out.println(set + "\n" + list);
    }
}

//Ошибка что выведет данная программа
class CollectionClassifier
{
    public static String classify(Set<?> s)
    {
        return "Set";
    }

    public static String classify(List<?> s)
    {
        return "List";
    }

    public static String classify(Collection<?> s)
    {
        return "Unknown Collection";
    }
}

class Wine
{
    String name() {return "wine";}
}

class SparklingWine extends Wine
{
    @Override
    String name() {return "SparklingWine";}
}

class Champagne extends SparklingWine
{
    @Override
    String name() {return "Champagne";}
}

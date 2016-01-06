package com.rybak.effective.java.ch5;

import java.util.*;

/**
 * Подерживайте обобщенные методы
 */
public class Item27
{
    //Обобщенный метод
    public static <E> Set<E> union(Set<? extends E> s1, Set<? extends E> s2)
    {
        Set<E> result = new HashSet<E>(s1);
        result.addAll(s2);
        return result;
    }

    public static void main(String[] args) {
        Set<String> guys = new HashSet<String>(Arrays.asList("Tom", "Dick", "Harry"));
        Set<String> stooges = new HashSet<String>(Arrays.asList("Larry", "Moe", "Curly"));
        Set<String> alfCio = union(guys, stooges);
        System.out.println(alfCio);



//использование обобщеного сингелтона
        String [] strings = {"jute", "hemp", "nylon"};
        UnaryFunction<String> sameString = identityFunction();
        for (String s : strings)
        {
            System.out.println(sameString.apply(s));
        }

        Number [] numbers = {1, 2.0, 3L};
        UnaryFunction<Number> sameNumber = identityFunction();
        for (Number s : numbers)
        {
            System.out.println(sameNumber.apply(s));
        }

    }

    //Создание экзамляра типа с параметрами с помощью конструктора
    Map<String, List<String>> anagrams = new HashMap<String, List<String>>();

    //TODO обобщеный метод статичексой генерацнии
    public static <K, V> HashMap<K, V> newHashMap()
    {
        return new HashMap<K, V>();
    }



    public interface UnaryFunction<T>
    {
        T apply(T arg);
    }

    //шоблон обобщенного статического синглтона
    private static UnaryFunction<Object> IDENTITY_FUNCTION =
            new UnaryFunction<Object>() {
                @Override
                public Object apply(Object arg) {
                    return arg;
                }
            };

    @SuppressWarnings("unchecked")
    public static <T> UnaryFunction<T> identityFunction()
    {
        return (UnaryFunction<T>) IDENTITY_FUNCTION;
    }


    //Исползуйте рекурсивное ограничение типа для выражений взаимной сопоставимости
    //читается как , для каждого типа T, который может быть сопоставлен с самим собой
    //TODO Comparable<? super T> - всегда так !!! Item 28!
    public static <T extends Comparable<? super T>> T max(List<? extends T> list)
    {
        Iterator<? extends T> i = list.iterator();
        T result = i.next();
        while (i.hasNext())
        {
            T t = i.next();
            if(t.compareTo(result) > 0)
                result = t;
        }
        return result;
    }
}

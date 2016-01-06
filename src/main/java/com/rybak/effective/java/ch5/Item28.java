package com.rybak.effective.java.ch5;

import java.util.*;

/**
 * Используйте ограниченные групповые сиволы для увеличения гибкости API .
 */
public class Item28
{
    //смотреть Stack - Item26
//TODO не используйте wildcard тип  в качестве возвращаемого типа.
//TODO Если пользователь класса должен думать о наличии wildcard-типов, то значит что-то не так с API - классом
    public static void main(String[] args) {

        //ограниченные типом групповых сиволов wildcard-mun  Item26 Stack puchALL / pop
        //TODO PECS значит производитель - extends
        //TODO             потребитель  - super
        //TODO producer - extends, consumer - super
        //пример инвариантов
        Stack<Number> numberStack = new Stack<Number>();
        Integer foo[] = {1,2,3,4,5,6,7,8,9,0};
        Iterable<Integer> integerStack = Arrays.asList(foo);
        //producer - extends
        numberStack.puchAll(integerStack);


        Stack<Number> numberStackPop = new Stack<Number>();
        numberStackPop.puch(new Integer(21));
        Collection<Object> objects = new ArrayList<Object>();
        //consumer - super
        numberStackPop.popAll(objects);


        Set<Integer> guys = new HashSet<Integer>(Arrays.asList(1, 2));
        Set<Double> stooges = new HashSet<Double>(Arrays.asList(3.0, 4.1));
        //исключительный параметр
        Set<Number> alfCio = Item27.<Number>union(guys, stooges);
        System.out.println(alfCio);

        //Item 27 public static <T extends Comparable<? super T>> T max(List<? extends T> list)
        //TODO Comparable<? super T> - всегда так !!! и Comparator -  всегда потребители


    }



    //TODO Если параметр для типа появился только один раз в декларации метода, замените его групповым символом.
    public static void swap(List<?> list, int i , int j )
    {
        swapHelper(list, i, j);
    }

    //закрытый вспомогательный метод для зазвата групповых сиволов
    private static <E> void swapHelper(List<E> list, int i , int j )
    {
        list.set(i, list.set(j, list.get(i)));
    }




}



package com.rybak.effective.java.ch7;

import java.util.*;

/**
 * Возвращайте массив нулевой длины, а не null
 *
 * Нет никаких причин для того, чтобы работающий с массивами метод возвращал значение null,
 * а не массив нулевой длины.
 */
public class Item43
{

    private List cheesesInStock = new ArrayList();
    public static final Cheese[] EMPTY_CHEESE_ARRAY = new Cheese[0];

    public static void main(String[] args) {
        Cheese[] cheeses = new Item43().getCheeses();
        if(cheeses != null && Arrays.asList(new Item43().getCheeses()).contains(new Cheese()))
        {
            System.out.println("Cheese");
        }
    }


    /**
     *
     * @return массив содержащий все сыры , имеющие в магазине или null, если сыров для продажи нет
     */
    public Cheese[] getCheeses()
    {
        if(cheesesInStock.size() == 0)
            return null;

        return new Cheese[]{new Cheese()};
    }


    /**
     *
     * @return правильный способ вывести массив из коллекции
     */
    public Cheese[] getCheesesTrue()
    {
        return (Cheese[]) cheesesInStock.toArray(EMPTY_CHEESE_ARRAY);
    }

    /**
     *
     * @return правильный способ возврат копии колеции
     */
    public List<Cheese> getCheesesList()
    {
        if(cheesesInStock.isEmpty())
            return Collections.emptyList();

        return new ArrayList<Cheese>(cheesesInStock);
    }


    private static class Cheese {
    }
}

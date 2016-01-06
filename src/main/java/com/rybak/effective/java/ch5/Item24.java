package com.rybak.effective.java.ch5;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Избегайте предупреждений о непровереном коде
 */
public class Item24
{

    Set<String> strings = new HashSet(); //Компилятор аккуртано предупреждает

    //TODO Избежать надо всех предупреждений насколько это возможно

    //Анотация SuppressWarnings может использоватся с детализацией любого уровня от локальных переменых до классов.
    //TODO Всегда используйте аннотацию SuppressWarnings на как можно меньшем деапозоне


    // Если выполнить компляцию то получим
    // Unchecked cast: 'java.lang.Object[]' to 'T[]'
    int size = 5;
    public <T> T[] toArray(T[] a, T [] elements)
    {
        if(a.length < size)
        {
            return (T[]) Arrays.copyOf(elements, size, a.getClass());
        }
        System.arraycopy(elements, 0, a, 0, size);
        if(a.length > size)
        {
            a[size] = null;
        }
        return a;
    }


    //Adding local variable to reduce scope of SuppressWarnings
    public <T> T[] toArraySuppressWarnings(T[] a, T [] elements)
    {
        if(a.length < size)
        {
            //This cast is correct because the array we're creating
            //is of the same type as the one passed in , which is T[]
            @SuppressWarnings("unchecked")
            T[] result = (T[]) Arrays.copyOf(elements, size, a.getClass());
            return result;
        }
        System.arraycopy(elements, 0, a, 0, size);
        if(a.length > size)
        {
            a[size] = null;
        }
        return a;
    }


    public static void main(String[] args) {

    }
}

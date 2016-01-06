package com.rybak.effective.java.ch6;

import java.util.HashSet;
import java.util.Set;

/**
 * Используйте аннотацию Override последовательно
 *
 * Нужно использовать аннотацию Override для каждой декларации метода, который вы хотите переопределить
 * декларацию суппер класса
 */
public class Item36
{
    public static void main(String[] args) {

        Set<Bigram> s = new HashSet<Bigram>();
        for (int i =0 ; i < 10; i++)
            for (char ch = 'a'; ch <= 'z'; ch++)
                s.add(new Bigram(ch, ch));
        System.out.println(s.size());
    }
}

class Bigram
{
    private final char first;
    private final char second;

    public Bigram(char first, char second)
    {
        this.first = first;
        this.second = second;
    }

//    @Override
//    public boolean equals(Bigram b)
//    {
//        return b.first == first && b.second == second;
//    }

    @Override
    public boolean equals(Object o)
    {
        if(!(o instanceof Bigram))
            return false;

        Bigram b = (Bigram)o;
        return b.first == first && b.second == second;
    }


    @Override
    public int hashCode()
    {
        return 31 * first + second;
    }
}

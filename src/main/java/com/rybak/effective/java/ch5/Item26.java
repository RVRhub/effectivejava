package com.rybak.effective.java.ch5;

import java.util.Arrays;
import java.util.Collection;
import java.util.EmptyStackException;

/**
 * Поддерживайте обобщеные типы
 */
public class Item26
{

    public static void main(String[] args) {
        args = new String[2];
        args[0] = "a";
        args[1] = "b";
        Stack<String> stack = new Stack<String>();
        for(String arg : args)
        {
            stack.puch(arg);
        }

        while (!stack.isEmpty())
        {
            System.out.println(stack.pop().toUpperCase());
        }
    }
}

class Stack<E>
{
    //private E[] elements; //1
    private Object[] elements;
    private int size = 0;
    public static final int DEFUALT_INITIAL_CAPACITY = 16;

    @SuppressWarnings("unchecked")
    public Stack()
    {
        //variant 1 невозможно создать массив из не материальных типов
        //elements = (E[]) new Object[DEFUALT_INITIAL_CAPACITY];
        elements = new Object[DEFUALT_INITIAL_CAPACITY];
    }

    public void puch(E e)
    {
        ensureCapacity();
        elements[size++] = e;
    }

    private void ensureCapacity()
    {
        if(elements.length == size)
        {
            elements = Arrays.copyOf(elements, 2*size +1);
        }
    }

    public E pop()
    {
        if(size == 0)
        {
            throw new EmptyStackException();
        }

        //variant 2
        @SuppressWarnings("unchecked")
        E result = (E) elements[--size];
        elements[size] = null;
        return result;
    }

    //Метод puchAll без использования групповых символов имеет недостатки !
//    public void puchAll(Iterable<E> src)
//    {
//        for(E e : src)
//            puch(e);
//    }

    //Метод popAll без Wildcard-тип - имеет недостатки !
//    public void popAll(Collection<E> dst)
//    {
//        while (!isEmpty())
//            dst.add(pop());
//    }



    // Wildcard-тип для параметра , является  потребителем  E public
    public void popAll(Collection<? super E> dst)
    {
        while (!isEmpty())
            dst.add(pop());
    }


    // Wildcard-тип для параметра , служащего производителем E public
    public void puchAll(Iterable<? extends E> src)
    {
        for(E e : src)
            puch(e);
    }

    public boolean isEmpty()
    {
        return size == 0;
    }

}

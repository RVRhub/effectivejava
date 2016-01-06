package com.rybak.effective.java.ch9;

import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Инициируйте исключения, соответсвующие абстрации
 *
 *
 */
public class Item61
{

    public static void main(String[] args) throws HigherLevelException{

        //Трансляция исключения
        try
        {
            //Использование абстрации нижнего уровня
            //для выполнения наших указаний

            //...
            if (true)
                throw new LowerLevelException();
        }
        catch (LowerLevelException e)
        {
            HigherLevelException higherLevelException = new HigherLevelException();
            higherLevelException.initCause(e); //сцепление исключений
            throw higherLevelException;
        }

        //Сцепление исключений
        try
        {
            //Использование абстрации нижнего уровня
            //для выполнения наших указаний

            //...
            if (true)
                throw new LowerLevelException();
        }
        catch (LowerLevelException e)
        {
            throw new HigherLevelException(e);
        }
    }



    private static class LowerLevelException extends Exception{
    }

    private static class HigherLevelException extends Throwable {
        public HigherLevelException(LowerLevelException e) {

            super(e);
        }

        public HigherLevelException() {

        }


    }


    /**
     * Возвращает элемент, находящийся в указанной позиции
     * в заданном списке.
     *
     * @throws IndexOutOfBoundsException , если индекс находится за пределами диапозона (index < 0 || index >= size())
     */
    public Object get(int index)
    {
        ListIterator i = listIterator(index);
        try {
            return i.next();
        }
        catch (NoSuchElementException e)
        {
            throw new IndexOutOfBoundsException("Index: " + index);
        }
    }

    private ListIterator listIterator(int index) {
        return null;
    }



}

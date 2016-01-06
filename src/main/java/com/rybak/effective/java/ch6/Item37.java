package com.rybak.effective.java.ch6;

/**
 * Используйте маркерные интерфейсы для определения типов
 *
 * Маркерные интерфейсы определяют тип, который реализуется экземлярами маркерного класса, а маркерные аннотации - нет.
 *
 * Маркерные аннотациия - когда это не класс или не интрефейс.
 *
 * Если вы впишете тип маркерной аннотации, цель которой ElemenetType.Type, потратьте время, чтобы определить, действительно
 * ли это должно быть тип аннотации, или же маркерный интрефейс больше подходит
 */
public class Item37 extends A implements Marker
{
    // if this class will not implement Marker, throw exception
    public static void main(String[] args)
    {
        Item37 a = new Item37();
        try {
            a.m1();
        } catch (MyException e) {

            System.out.println(e);
        }
    }
}


interface Marker
{
}

class A
{
    void m1() throws MyException
    {
        if((this instanceof Marker))
        {
            System.out.println("successfull");
        }
        else {
            throw new MyException("Must implement interface Marker ");
        }
    }
}

class MyException extends Exception {
    public MyException(String s){
        super(s);
    }
}
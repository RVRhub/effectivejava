package com.rybak.effective.java.ch9;

/**
 * Используйте исключения лишь в исключительных ситуациях
 *
 * Инициирование и перехват исключений дорого обходятся системе
 */
public class Item57
{
    public static void main(String[] args) {

        A[] range = new A[2];

        //Неправильное использования исключения. Никогда так не делайте.
        try
        {
            int i = 0;
            while (true)
            {
                range[i++].climb();
            }
        }
        catch (ArrayIndexOutOfBoundsException e)
        {

        }

    }
}


class A {
    void climb(){}
}
